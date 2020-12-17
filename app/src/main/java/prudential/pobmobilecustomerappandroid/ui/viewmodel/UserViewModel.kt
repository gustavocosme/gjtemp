package prudential.pobmobilecustomerappandroid.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import prudential.pobmobilecustomerappandroid.extensions.tryResource
import prudential.pobmobilecustomerappandroid.extensions.tryResponse
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.model.User
import prudential.pobmobilecustomerappandroid.repository.UserRepository
import prudential.pobmobilecustomerappandroid.ui.activity.autentication.LoginActivity

/**
 * ViewModel Factory to pass params to according view model
 * @property application Context of activity.
 */
class UserViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    /**
     * Create instance of given view model.
     * @return Instance of ViewModel
     * @throws IllegalArgumentException if view model is unknow
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


/**
 * ViewModel class to handle state of activity and call repository methods.
 * @param application Context of LifecycleOwner.
 */
class UserViewModel(context: Application) : AndroidViewModel(context) {

    val userRepository by lazy { UserRepository(context) }

    /**
     * Check if user exists and return boolean.
     */
    val isUserExists = liveData {
        emit(Resource.Loading())
        val exists = tryResource { userRepository.isUser() }
        emit(exists)
    }

    /**
     * Check if user is logged and touch id verification is enable..
     */
    val isLoggedAndTouchIdEnable = liveData {
        emit(Resource.Loading())
        val isLoggedAndTouchIdEnable = tryResource { userRepository.isLoggedAndTouchIdEnable() }
        emit(isLoggedAndTouchIdEnable)
    }

    /**
     * Check if user is logged.
     */
    val isLogged = liveData {
        emit(Resource.Loading())
        val isLogged = tryResource { userRepository.isLogged() }
        emit(isLogged)
    }

    /**
     * Return user data from repository.
     */
    val user = liveData {
        emit(Resource.Loading())
        val user = tryResource { userRepository.getCurrentUser() }
        emit(user)
    }

    /**
     * Return list of users from repository.
     */
    val users = liveData {
        emit(Resource.Loading())
        val users = tryResponse { userRepository.getUsers() }
        emit(users)
    }

    /**
     * Delete all users from repository.
     */
    fun deleAllUser() = liveData {
        emit(Resource.Loading())
        val deleted = tryResource { userRepository.deleteAll(); }
        emit(deleted)
    }

    /**
     * Fake insertaion using fake data.
     */
    fun insertFake() = viewModelScope.launch {
        val userFaKe = User(0, LoginActivity.isRemember,"07812312312");
        userRepository.insert(userFaKe);
    }

    /**
     * Perform user login.
     * @param user login identification of user..
     * @param cpf CPF document of user.
     * @param isLogin If user is logged or note.
     * @return LiveData with state and user data.
     */
    fun login(user: String, cpf: String, isLogin: Boolean) = liveData {
        emit(Resource.Loading())

        val result = tryResponse { userRepository.login(user, cpf) }

        when (result) {
            is Resource.Success -> {
                result.data.isLogin = true;
                result.data.cpf = cpf;
                if (isLogin) {
                    userRepository.deleteAll();
                    userRepository.insert(result.data)

                }
                emit(result);
            }
            is Resource.Failure -> {
                val failure = Resource.Failure<User>(
                    throwable = result.throwable,
                    statusCode = result.statusCode
                )
                emit(failure)
            }
        }
    }

    /**
     * Perform password forgotten.
     * @param user Username of user.
     * @param firstAccess Check if is user first access.
     * @return Result of operation.
     */
    fun forgotPassword(user: String, firstAccess: Boolean) = liveData {
        emit(Resource.Loading())

        val result = tryResponse { userRepository.forgotPassword(user) }

        when (result) {
            is Resource.Success -> {
                emit(result)
            }
            is Resource.Failure -> {
                val failure = Resource.Failure<User>(
                    throwable = result.throwable,
                    statusCode = result.statusCode
                )
                emit(failure)
            }
        }
    }

    /**
     * Perform new password creation.
     * @param user Username of user.
     * @param password User password.
     * @param firstAccess Check if is user first access.
     */
    fun newPassword(user: String, password: String, date: String, firstAccess: Boolean) =
        liveData {
            emit(Resource.Loading())

            val result = tryResponse { userRepository.newPassword(user, password, date) }

            when (result) {
                is Resource.Success -> {


                    emit(result)
                }
                is Resource.Failure -> {
                    val failure = Resource.Failure<User>(
                        throwable = result.throwable,
                        statusCode = result.statusCode
                    )
                    emit(failure)
                }
            }
        }

    /**
     * Perform logout action.
     */
    val logout = liveData {
        emit(Resource.Loading())
        val result = tryResource { userRepository.onLogout() }
        emit(result)
    }

}
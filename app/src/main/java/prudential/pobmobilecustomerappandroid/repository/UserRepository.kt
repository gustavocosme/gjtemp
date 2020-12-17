package prudential.pobmobilecustomerappandroid.repository

import android.app.Application
import prudential.pobmobilecustomerappandroid.db.AppDBSingleton
import prudential.pobmobilecustomerappandroid.model.User
import prudential.pobmobilecustomerappandroid.network.RetrofitService
import prudential.pobmobilecustomerappandroid.network.api.UserService
import retrofit2.Response
import retrofit2.awaitResponse

/**
 * Repository to manage database and netowork access and returns data relative to [User] object.
 * @constructor  Application Context to be used on database instance creation.
 * @property application Contex to be used on database creation.
 */
class UserRepository(val application: Application) {

    private val userService: UserService = RetrofitService.createService()
    private val configRepository by lazy { ConfigRepository(application) }
    private val userDao by lazy {
        val db = AppDBSingleton.getInstance(application)
        db.userDao()
    }

    /**
     * Check if user is null.
     * @return return true if user is NOT null, else return false.
     */
    suspend fun isUser(): Boolean {
        val user = userDao.getCurrentUser()
        return user != null
    }

    /**
     * Check if user is logged.
     * @return True if is logged, false if not.
     */
    suspend fun isLogged(): Boolean {
        val user = getCurrentUser()
        return user.isLogin
    }

    /**
     * Get list of users from api.
     * @return List of users encapsuled on Response object.
     */
    suspend fun getUsers(): Response<List<User>> {
        val result = userService.getUsers()
        return result.awaitResponse()
    }

    /**
     * Call api service to perform login.
     * @param user Username to be used on api call.
     * @param password Password of given username.
     * @return User encapsuled on Response Object.
     */
    suspend fun login(user: String, password: String): Response<User> {
        val result = userService.login(user, password)
        return result.awaitResponse()
    }

    /**
     * Call api service to perform forgot password acion.
     * @param user Username to perform action.
     * @return User encapsuled on Response Object.
     */
    suspend fun forgotPassword(user: String): Response<User> {
        val result = userService.forgotPassword(user)
        return result.awaitResponse()
    }

    /**
     * Call api service to perform forgot password acion.
     * @param user Username to perform action.
     * @return User encapsuled on Response Object.
     */
    suspend fun newPassword(user: String, password: String, date: String): Response<User> {
        val result = userService.newPassword(user, password, date)
        return result.awaitResponse()
    }

    /**
     * Get current logged user from database.
     * @return User logged.
     */
    suspend fun getCurrentUser(): User = userDao.getCurrentUser()

    /**
     * Insert user to database.
     * @return ID of inserted user.
     */
    suspend fun insert(user: User): Long = userDao.insert(user)

    /**
     * Delete all entries from database.
     */
    suspend fun deleteAll(): Unit = userDao.deleteAll();

    /**
     * Check if user is logged, and touch id is enable to the user.
     * @return true if both expressions is true, false if not.
     */
    suspend fun isLoggedAndTouchIdEnable(): Boolean {
        val user = getCurrentUser()
        val isTouchIdEnable = configRepository.isTouchIdEnable()
        return isTouchIdEnable and user.isLogin
    }

    /**
     * Logout user and remove it from database.
     */
    suspend fun onLogout() {
        deleteAll()
        //configRepository.disableTouchId()
    }
}
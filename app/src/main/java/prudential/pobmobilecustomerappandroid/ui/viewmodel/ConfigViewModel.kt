package prudential.pobmobilecustomerappandroid.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import prudential.pobmobilecustomerappandroid.extensions.tryResource
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.repository.ConfigRepository


/**
 * ViewModel Factory to pass params to according view model
 * @property application Context of activity.
 */
class ConfigViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    /**
     * Create instance of given view model.
     * @return Instance of ViewModel
     * @throws IllegalArgumentException if view model is unknow
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfigViewModel::class.java)) {
            return ConfigViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


/**
 * ViewModel class to handle state of activity and call repository methods.
 * @param application Context of LifecycleOwner.
 */
class ConfigViewModel(application: Application) : AndroidViewModel(application) {

    private val configRepository by lazy { ConfigRepository(application) }

    /**
     * Var to get configs from repository.
     */
    val config = liveData {
        emit(Resource.Loading())
        val result = tryResource { configRepository.getCurrent() }
        emit(result)
    }

    /**
     * Update configuration token.
     * @param isChedked Option checked by user if want or not validate by token.
     * @return viewModelScope to run suspended on activity.
     */
    fun onUpdateConfigUpdateToken(isChedked: Boolean) = viewModelScope.launch {
        configRepository.onUpdateConfigUpdateToken(isChedked)
    }

    /**
     * Update configuration touch id.
     * @param isChedked Option checked by user if want or not validate by touch id.
     * @return viewModelScope to run suspended on activity.
     */
    fun onUpdateConfigUpdateTouch(isChedked: Boolean) = viewModelScope.launch {
        configRepository.onUpdateConfigUpdateTouch(isChedked)
    }

    /**
     * Update configuration touch id button.
     * @param isChedked Option checked by user if want or not validate by touch id.
     * @return viewModelScope to run suspended on activity.
     */
    fun onUpdateConfigUpdateTouchBtn(isChedked: Boolean) = viewModelScope.launch {
         configRepository.onUpdateConfigUpdateTouchBtn(isChedked)
    }

}
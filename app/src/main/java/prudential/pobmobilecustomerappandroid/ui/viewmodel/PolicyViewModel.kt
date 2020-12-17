package prudential.pobmobilecustomerappandroid.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import prudential.pobmobilecustomerappandroid.extensions.tryResource
import prudential.pobmobilecustomerappandroid.model.PolicyBasicModel
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.repository.PolicyRepository


/**
 * ViewModel Factory to pass params to according view model
 * @property application Context of activity.
 */
class PolicyViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    /**
     * Create instance of given view model.
     * @return Instance of ViewModel
     * @throws IllegalArgumentException if view model is unknow
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PolicyViewModel::class.java)) {
            return PolicyViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


/**
 * ViewModel class to handle state of activity and call repository methods.
 * @param application Context of LifecycleOwner.
 */
class PolicyViewModel (application: Application) : AndroidViewModel(application) {

    private val policyRepository by lazy { PolicyRepository(application) }
    private val policySelectedIndexs = MutableLiveData(mutableListOf<Int>())

    /**
     * Get all policies from repository.
     */
    val policies = liveData {
        emit(Resource.Loading())
        val result = tryResource { policyRepository.getPolicies4() }
        emit(result)
    }

    /**
     * Var to get payments from repository by given list of index.
     * @return liveData encapsuled on a resource model with payment list to manage return states.
     */
    val policiesSelected: LiveData<Resource<List<PolicyBasicModel>>> = policySelectedIndexs.switchMap {
        liveData {
            emit(Resource.Loading())
            val result = tryResource { policyRepository.getSelectedPoliciesByIndex(it) }
            emit(result)
        }
    }

    /**
     * Set policy index to get list based on it.
     * @param indexs list of indexs to set.
     */
    fun setPolicySelectedIndex(indexs: MutableList<Int>) {
        policySelectedIndexs.value = indexs
    }

}
package prudential.pobmobilecustomerappandroid.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import prudential.pobmobilecustomerappandroid.extensions.tryResource
import prudential.pobmobilecustomerappandroid.model.PaymentBasicaModel
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.repository.PaymentRepository


/**
 * ViewModel Factory to pass params to according view model
 * @property application Context of activity.
 */
class PaymentViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    /**
     * Create instance of given view model.
     * @return Instance of ViewModel
     * @throws IllegalArgumentException if view model is unknow
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PaymentViewModel::class.java)) {
            return PaymentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

/**
 * ViewModel class to handle state of activity and call repository methods.
 * @param application Context of LifecycleOwner.
 */
class PaymentViewModel(application: Application) : AndroidViewModel(application) {

    private val paymentRepository by lazy { PaymentRepository(application) }

    private val paymentIndex = MutableLiveData(0)


    /**
     * Var to get payments from repository by given index.
     * @return liveData encapsuled on a resource model with payment list to manage return states.
     */
    val payments: LiveData<Resource<List<PaymentBasicaModel>>> = paymentIndex.switchMap {
        liveData {
            emit(Resource.Loading())
            val result = tryResource { paymentRepository.getPaymentsByIndex(it) }
            emit(result)
        }
    }

    /**
     * Set payment index and change MutableLiveData value.
     */
    fun setPaymentIndex(index: Int) {
        paymentIndex.value = index
    }

}
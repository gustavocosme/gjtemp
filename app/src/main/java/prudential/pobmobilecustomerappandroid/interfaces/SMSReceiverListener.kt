package prudential.pobmobilecustomerappandroid.interfaces

/**
 * Interface for a callback to be invoked when receive SMS.
 */
interface SMSReceiverListener {
    /**
     * Called when SMS was received.
     * @param origin Receive author of SMS.
     * @param message Receive message body of SMS.
     */
    fun onReceiveSMS(origin: String, message: String)
}
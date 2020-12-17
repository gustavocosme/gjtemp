package prudential.pobmobilecustomerappandroid.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import prudential.pobmobilecustomerappandroid.interfaces.SMSReceiverListener

/**
 * Broadcast base class to receive SMS with token validation and send back to activity.
 */
class SMSBroadcastReceiver : BroadcastReceiver() {

    private lateinit var smsReceiverListener: SMSReceiverListener

    /**
     * Default method onReceive that intercept android.provider.Telephony.SMS_RECEIVED and handle it.
     * @param context Context from who is calling
     * @param intent Intent to start receive with action
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals("android.provider.Telephony.SMS_RECEIVED")) {
            intent?.extras?.let {
                try {
                    val pdus = it["pdus"] as Array<Any>
                    val msgs = arrayOfNulls<SmsMessage>(pdus.size)
                    for (i in msgs.indices) {
                        val sms = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                        val msgFrom = sms[0].getOriginatingAddress()
                        val msgBody = sms[0].getMessageBody()
                        val msgBodyToken = msgBody.split(":")[1].trim()
                        msgFrom?.let {
                            smsReceiverListener.onReceiveSMS(msgFrom, msgBodyToken)
                        }
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    /**
     * Receive listener to send back token message to who called.
     * @param smsReceiverListener Custom listener to send info back.
     */
    fun setOnSMSReceiverListener(smsReceiverListener: SMSReceiverListener) {
        this.smsReceiverListener = smsReceiverListener
    }

}
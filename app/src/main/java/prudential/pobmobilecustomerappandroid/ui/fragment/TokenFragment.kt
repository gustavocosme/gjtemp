package prudential.pobmobilecustomerappandroid.ui.fragment;

import android.content.*
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import gustavocosme.ui.BaseFragment
import kotlinx.android.synthetic.main.activity_token_validation.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.broadcast.SMSBroadcastReceiver
import prudential.pobmobilecustomerappandroid.extensions.nextToFocus
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.SMSReceiverListener
import prudential.pobmobilecustomerappandroid.model.DataConfirmationUpdate
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.utils.form.FormField
import prudential.pobmobilecustomerappandroid.utils.form.Keyboard

/**
 * TokenFragment subclass BaseFragment to create view
 * and it's actions.
 */
class TokenFragment : BaseFragment(), SMSReceiverListener {

    private lateinit var countDownTimer: CountDownTimer
    private var continueButton: Button? = null
    private var resendSMSButton: Button? = null
    private var tokenText1: TextInputEditText? = null
    private var tokenText2: TextInputEditText? = null
    private var tokenText3: TextInputEditText? = null
    private var tokenText4: TextInputEditText? = null
    private var tokenText5: TextInputEditText? = null
    private var tokenText6: TextInputEditText? = null
    private var invisibleTokenText: TextView? = null
    private var inputLayout1: TextInputLayout? = null
    private var inputLayout2: TextInputLayout? = null
    private var inputLayout3: TextInputLayout? = null
    private var inputLayout4: TextInputLayout? = null
    private var inputLayout5: TextInputLayout? = null
    private var inputLayout6: TextInputLayout? = null
    private var resendMessage: TextView? = null
    private var resendSMSMessageTex: TextView? = null
    private var resendMessageCount: TextView? = null

    /**
     * Init method to set layout to the cachedView variable.
     */
    init {
        setLayout(
            R.layout.fragment_token
        )
    }

    /**
     * Called inside onCreateView.
     * It's called once, if the view was not created yet.
     */
    override fun init() {
        initViews()
        navigationInputToken()
        getTokenFromSMS()
        countDownTimer()

        setContinueButtonEnable(false)
        resendSMS()
        continueButtonClick()
    }

    private fun initViews() {
        continueButton = cachedView?.findViewById(R.id.fragment_token_validation_continue_button)
        tokenText1 = cachedView?.findViewById(R.id.fragment_token_validation_token_1)
        tokenText2 = cachedView?.findViewById(R.id.fragment_token_validation_token_2)
        tokenText3 = cachedView?.findViewById(R.id.fragment_token_validation_token_3)
        tokenText4 = cachedView?.findViewById(R.id.fragment_token_validation_token_4)
        tokenText5 = cachedView?.findViewById(R.id.fragment_token_validation_token_5)
        tokenText6 = cachedView?.findViewById(R.id.fragment_token_validation_token_6)
        inputLayout1 = cachedView?.findViewById(R.id.fragment_token_validation_inputlayout_1)
        inputLayout2 = cachedView?.findViewById(R.id.fragment_token_validation_inputlayout_2)
        inputLayout3 = cachedView?.findViewById(R.id.fragment_token_validation_inputlayout_3)
        inputLayout4 = cachedView?.findViewById(R.id.fragment_token_validation_inputlayout_4)
        inputLayout5 = cachedView?.findViewById(R.id.fragment_token_validation_inputlayout_5)
        inputLayout6 = cachedView?.findViewById(R.id.fragment_token_validation_inputlayout_6)
        invisibleTokenText = cachedView?.findViewById(R.id.fragment_token_invisible_textview)
        resendSMSButton = cachedView?.findViewById(R.id.fragment_token_validation_resend_button)
        resendMessage = cachedView?.findViewById(R.id.fragment_token_validation_resend_msg)
        resendMessageCount = cachedView?.findViewById(R.id.fragment_token_validation_resend_count)
        resendSMSMessageTex = cachedView?.findViewById(R.id.textView16)
    }

    /**
     * Init SMSBroadcastReceiver and listener to SMS action.
     * @return Unit
     */
    private fun getTokenFromSMS() {
        val smsBroadcastReceiver = SMSBroadcastReceiver()
        smsBroadcastReceiver.setOnSMSReceiverListener(this)
        context?.registerReceiver(
            smsBroadcastReceiver,
            IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        )
    }

    /**
     * Used to count down and display the second until finish.
     * After finish count down, show resend button.
     * @return Unit
     */
    private fun countDownTimer() {
        countDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                var time = "0:${millisUntilFinished / 1000} segundos";

                var t = millisUntilFinished / 1000

                if (t < 10) {
                    time = "0:0${millisUntilFinished / 1000} segundos";
                }

                resendMessageCount?.text = time;

            }

            override fun onFinish() {
                showCountDown(false)
            }
        }.start()
    }

    /**
     * Restart count and set it's visibility to VISIBLE.
     * @param view - Comes from inflated layout
     * @return Unit
     */
    fun resendSMS() {
        resendSMSButton?.setOnClickListener {
            countDownTimer()
            showCountDown(true)
            resendSMSMessageTex?.setText("Não recebeu? Fique tranquilo!");
            setContinueButtonEnable(false)
        }
    }

    private fun continueButtonClick() {
        continueButton?.setOnClickListener {
            Tab.INSTANCE.nav(
                UserConfirmationUpdateFragment.newInstance(
                    instance = 0,
                    array = dataConfirmationUpdateArray,
                    isCheck1 = true,
                    isCheck2 = true,
                    isToken = false
                )
            )
        }
    }

    /**
     * Set visibilities to true or false on count down views.
     * @param show - State of visibility
     */
    private fun showCountDown(show: Boolean) {
        resendSMSMessageTex?.setText("Não recebeu? Vamos tentar mais uma vez!");
        resendMessage?.showOrGone(show)
        resendMessageCount?.showOrGone(show)
        resendSMSButton?.showOrGone(!show)
    }

    /**
     * Cancel count down when all fields are fullfiled.
     */
    private fun cancelCountDown() {
        countDownTimer.cancel()
        resendMessageCount?.text = "1:00"
    }

    /**
     * Receive SMS data to perform action and fill all fields.
     * @param origin SMS origin/author.
     * @param message Message body of received SMS.
     */
    override fun onReceiveSMS(origin: String, message: String) {
        if (message.length == 6) {
            tokenText1?.setText(message.substring(0))
            tokenText2?.setText(message.substring(1))
            tokenText3?.setText(message.substring(2))
            tokenText4?.setText(message.substring(3))
            tokenText5?.setText(message.substring(4))
            tokenText6?.setText(message.substring(5))
            cancelCountDown()
        }
    }

    /**
     * Called when activity is resumed and check if has text on clipboard to paste on token fields to validate.
     */
    override fun onResume() {
        super.onResume()
        try {
            val clipboard: ClipboardManager =
                context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var pasteData = ""
            when {
                !clipboard.hasPrimaryClip() and !clipboard.getPrimaryClipDescription()!!
                    .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN) -> {
                }
                else -> {
                    val item: ClipData.Item = clipboard.getPrimaryClip()!!.getItemAt(0)
                    pasteData = item.text.toString();
                    onReceiveSMS("", pasteData);
                    clipboard.setPrimaryClip(ClipData.newPlainText("", ""));
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * Check if any of fields is empty.
     * @return True if any of fields is empty, false if not.
     */
    fun isValidation(): Boolean {
        if (
            tokenText1?.text.toString().equals("") ||
            tokenText2?.text.toString().equals("") ||
            tokenText3?.text.toString().equals("") ||
            tokenText4?.text.toString().equals("") ||
            tokenText5?.text.toString().equals("") ||
            tokenText6?.text.toString().equals("")
        ) return false;
        return true;
    }

    /**
     * Navigate between token fields. If user is typing, check and go to the next.
     * If user is ereasing, check and go to the preview field.
     */
    private fun navigationInputToken() {
        tokenText1?.apply {
            nextToFocus(tokenText2, tokenText1)
            initTokenValidationWith(inputLayout1)
        }
        tokenText2?.apply {
            nextToFocus(tokenText3, tokenText1)
            initTokenValidationWith(inputLayout2)
        }
        tokenText3?.apply {
            nextToFocus(tokenText4, tokenText2)
            initTokenValidationWith(inputLayout3)
        }
        tokenText4?.apply {
            nextToFocus(tokenText5, tokenText3)
            initTokenValidationWith(inputLayout4)
        }
        tokenText5?.apply {
            nextToFocus(tokenText6, tokenText4)
            initTokenValidationWith(inputLayout5)
        }
        tokenText6?.apply {
            initTokenValidationWith(inputLayout6)
            clearFocusAndHideKeyboard()
            nextToFocus(tokenText6, tokenText5)
        }
    }

    /**
     * Clear focus when token fields is fulfilled.
     * @receiver Input texto to perform action.
     */
    fun TextInputEditText.clearFocusAndHideKeyboard() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setContinueButtonEnable(isValidation())
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrBlank()) {
                    Keyboard.hideSoftKeyboard(
                        context,
                        windowToken
                    )
                }
            }
        })
    }

    private fun setContinueButtonEnable(enable: Boolean) {
        if (enable) {
            continueButton?.apply {
                alpha = 1f;
                isClickable = true;
            }
        } else {
            continueButton?.apply {
                alpha = 0.45f;
                isClickable = false;
            }
        }
    }

    /**
     * Clear focus when token fields is fulfilled.
     * @receiver Input text to perform action.
     */
    private fun TextInputEditText.initTokenValidationWith(
        textInputLayout: TextInputLayout?
    ) {
        val token = FormField()
        invisibleTokenText?.let { text ->
            textInputLayout?.let { input ->
                token.inicialize(
                    context,
                    this,
                    text,
                    input
                )
                token.onRenderCountMessage("Preencher token corretamente.")
            }
        }
    }


    /**
     * Static method do create instance of TokenFragment.
     */
    companion object {

        private lateinit var dataConfirmationUpdateArray: ArrayList<DataConfirmationUpdate>

        /**
         * Create instance of TokenFragment.
         * @param instance Index of instance to be created.
         * @return TokenFragment created.
         */
        fun newInstance(
            instance: Int,
            array: ArrayList<DataConfirmationUpdate>,
            isCheck1: Boolean,
            isCheck2: Boolean,
            isToken: Boolean
        ): TokenFragment {
            this.dataConfirmationUpdateArray = array
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            val fragment =
                TokenFragment()
            fragment.setArguments(args)
            return fragment
        }

    }
}
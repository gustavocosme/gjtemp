package prudential.pobmobilecustomerappandroid.ui.activity.autentication

import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_token_validation.*
import me.aflak.libraries.dialog.FingerprintDialog
import org.jetbrains.anko.startActivity
import prudential.pobmobilecustomerappandroid.App
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.broadcast.SMSBroadcastReceiver
import prudential.pobmobilecustomerappandroid.extensions.alertCustom
import prudential.pobmobilecustomerappandroid.extensions.nextToFocus
import prudential.pobmobilecustomerappandroid.extensions.prudentialLogin
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.SMSReceiverListener
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.ui.activity.app.MainActivity
import prudential.pobmobilecustomerappandroid.ui.dialogs.DialogLoadView
import prudential.pobmobilecustomerappandroid.ui.viewmodel.ConfigViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.ConfigViewModelFactory
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModelFactory
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.form.FormField
import prudential.pobmobilecustomerappandroid.utils.form.Keyboard


/**
 * Class to validate token from SMS and e-mail.
 * This class implements SMSReceiverListener
 *
 */
class TokenValidationActivity : AppCompatActivity(), SMSReceiverListener {

    private var firstAccess = false
    private lateinit var countDownTimer: CountDownTimer
    private val configViewModel by viewModels<ConfigViewModel> { ConfigViewModelFactory(application) }
    private val userViewModel by viewModels<UserViewModel> { UserViewModelFactory(application) }
//    val db by lazy { AppDBSingleton.inicialize(this) }

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_token_validation)
        showOrHideFirstAccess()
        navigationInputToken()
        getTokenFromSMS()
        countDownTimer()

        activity_token_validation_continue_button.alpha = 0.45f;
        activity_token_validation_continue_button.isClickable = false;

        overridePendingTransition(
            R.anim.right_to_left_in,
            R.anim.right_to_left_out
        );

        isTeste();
    }

    /**
     * Check if app is running in mode test, and mock some results.
     */
    fun isTeste() {
        if (App.IS_TEST) {
            activity_token_validation_token_1.setText("1")
            activity_token_validation_token_2.setText("4")
            activity_token_validation_token_3.setText("0")
            activity_token_validation_token_4.setText("9")
            activity_token_validation_token_5.setText("7")
            activity_token_validation_token_6.setText("9")
        }

    }

    /**
     * Init SMSBroadcastReceiver and listener to SMS action.
     * @return Unit
     */
    private fun getTokenFromSMS() {
        val smsBroadcastReceiver = SMSBroadcastReceiver()
        smsBroadcastReceiver.setOnSMSReceiverListener(this)
        registerReceiver(
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

                activity_token_validation_resend_count.text = time;

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
    fun resendSMS(view: View) {

        countDownTimer()
        showCountDown(true)

        findViewById<TextView>(R.id.textView16).setText("Não recebeu? Fique tranquilo!");
        activity_token_validation_continue_button.alpha = 0.45f;
        activity_token_validation_continue_button.isClickable = false;


    }

    /**
     * Set visibilities to true or false on count down views.
     * @param show - State of visibility
     */
    private fun showCountDown(show: Boolean) {

        //activity_token_validation_continue_button.alpha = 1f;
        //activity_token_validation_continue_button.isClickable = true;

        findViewById<TextView>(R.id.textView16).setText("Não recebeu? Vamos tentar mais uma vez!");
        activity_token_validation_resend_msg.showOrGone(show)
        activity_token_validation_resend_count.showOrGone(show)
        activity_token_validation_resend_button.showOrGone(!show)
    }

    /**
     * Cancel count down when all fields are fullfiled.
     */
    private fun cancelCountDown() {
        countDownTimer.cancel()
        activity_token_validation_resend_count.text = "1:00"
    }

    /**
     * Receive SMS data to perform action and fill all fields.
     * @param origin SMS origin/author.
     * @param message Message body of received SMS.
     */
    override fun onReceiveSMS(origin: String, message: String) {
        if (message.length == 6) {

            activity_token_validation_token_1.setText(message.substring(0))
            activity_token_validation_token_2.setText(message.substring(1))
            activity_token_validation_token_3.setText(message.substring(2))
            activity_token_validation_token_4.setText(message.substring(3))
            activity_token_validation_token_5.setText(message.substring(4))
            activity_token_validation_token_6.setText(message.substring(5))
            cancelCountDown()
            //alertCustom(getString(R.string.token_ok), TypeAlertCustom.OK);
        } else {
            //alertCustom(getString(R.string.erro_token),TypeAlertCustom.ERROR);
        }
    }

    /**
     * Called when activity is resumed and check if has text on clipboard to paste on token fields to validate.
     */
    override fun onResume() {
        super.onResume()
        try {
            val clipboard: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            var pasteData = ""
            when {
                !clipboard.hasPrimaryClip() and !clipboard.getPrimaryClipDescription()!!
                    .hasMimeType(MIMETYPE_TEXT_PLAIN) -> {
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
     * Show or hide first access flow according to firstAccess variable.
     */
    private fun showOrHideFirstAccess() {
        firstAccess = intent.getBooleanExtra("first_access", false)
        activity_token_validation_flow.showOrGone(firstAccess)


        //if(firstAccess)
          //  activity_token_check.visibility =  View.GONE;
    }

    /**
     * Open Biometric Activity according to the configuration.
     * @param view View clicked by user.
     */
    fun openBiometricActivity(view: View) {
        if (!isValidation()) {
            alertCustom(getString(R.string.erro_token), TypeAlertCustom.ERROR);
            return;
        }
//        db.onUpdateConfigUpdateToken(activity_token_check.isChecked);
        configViewModel.onUpdateConfigUpdateToken(activity_token_check.isChecked)
        activity_token_validation_continue_button.isEnabled = false;

        firstAccess = intent.getBooleanExtra("first_access", false);
        val isLogin = intent.getBooleanExtra("isLogin", false);


        configViewModel.config.observe(this, Observer {
            if (it is Resource.Success) {
                val config = it.data


                if(config.isTouch)
                {
                    initTouch();
                }
                else
                if(config.isTouchIdSave)
                {
                    startActivity<MainActivity>()

                }else{

                    startActivity<BiometricAuthActivity>("isCheckView" to true)

                }

                /*
                when {
                    config.isTouchIdSave -> {
                        startActivity<MainActivity>()
                        return@Observer;
                    }
                    config.isTouch -> startActivity<MainActivity>()
                    else -> {
                        if (!config.isTouchIdSave) {
                            startActivity<BiometricAuthActivity>("isCheckView" to true)
                        } else {
                            startActivity<MainActivity>()
                        }
                    }
                }

                 */
            }

        })


        /*

        if (isLogin) {
            configViewModel.config.observe(this, Observer {
                if (it is Resource.Success) {
                    val config = it.data
                    when {
                        config.isTouchIdSave -> {
                            startActivity<MainActivity>()
                            return@Observer;
                        }
                        config.isTouch -> startActivity<MainActivity>()
                        else -> {
                            if (!config.isTouchIdSave) {
                                startActivity<BiometricAuthActivity>("isCheckView" to true)
                            } else {
                                startActivity<MainActivity>()
                            }
                        }
                    }
                }
            })

            /*
            if (Config.CURRENT!!.isTouchIdSave) {
                startActivity<MainActivity>()
                return;
            }

            if (Config.CURRENT!!.isTouch) {

                startActivity<MainActivity>()

            } else {

                if (!Config.CURRENT!!.isTouchIdSave) {

                    startActivity<BiometricAuthActivity>("isCheckView" to true)

                } else {

                    startActivity<MainActivity>()

                }

            }

             */

            return
        }

        if (firstAccess) {
            //startActivity<BiometricAuthActivity>("isCheckView" to false)
        } else {
            Looper.myLooper()?.let {
                Handler(it).postDelayed({

                    configViewModel.config.observe(this, Observer {
                        if (it is Resource.Success) {

                            val config = it.data;





                            when {
                                config.isTouchIdSave -> {
                                    startActivity<MainActivity>()
                                    return@Observer;
                                }
                                config.isTouch -> startActivity<MainActivity>()
                                else -> {
                                    if (!config.isTouchIdSave) {
                                        startActivity<BiometricAuthActivity>("isCheckView" to true)
                                    } else {
                                        startActivity<MainActivity>()
                                    }
                                }
                            }


                        }
                    })


                    //startActivity<LoginActivity>("isAlertPassword" to true)
                }, 0)
            }
        }


         */
    }

    lateinit var dialod: DialogLoadView
    fun initTouch() {
        FingerprintDialog.initialize(this).prudentialLogin(
            this,
            getString(R.string.touch_id_title_login),
            getString(R.string.touch_id_descripition_start),
            {

                if (it) {

                    dialod = DialogLoadView(this)
                    dialod.show()
                    userViewModel.login("aa","aa", LoginActivity.isRemember).observe(this, Observer {

                        when (it) {

                            is Resource.Loading -> {

                            }
                            is Resource.Success -> {

                                startActivity<MainActivity>()

                                Looper.myLooper()?.let {
                                    Handler(it).postDelayed({
                                        dialod.dismiss()

                                    }, 1500)
                                }

                            }
                            is Resource.Failure -> {

                                Looper.myLooper()?.let {
                                    Handler(it).postDelayed({
                                        dialod.dismiss()

                                    }, 1500)
                                }
                            }
                        }
                    })


                } else {
                    userViewModel.logout.observe(this, Observer {
                        if (it is Resource.Success) {
                            startActivity<LoginActivity>()
                            finish()
                        }
                    })
                }
            })
    }


    /**
     * Check if any of fields is empty.
     * @return True if any of fields is empty, false if not.
     */
    fun isValidation(): Boolean {
        if (
            activity_token_validation_token_1.text.toString().equals("") ||
            activity_token_validation_token_2.text.toString().equals("") ||
            activity_token_validation_token_3.text.toString().equals("") ||
            activity_token_validation_token_4.text.toString().equals("") ||
            activity_token_validation_token_5.text.toString().equals("") ||
            activity_token_validation_token_6.text.toString().equals("")
        ) return false;
        return true;
    }

    /**
     * Navigate between token fields. If user is typing, check and go to the next.
     * If user is ereasing, check and go to the preview field.
     */
    private fun navigationInputToken() {
        activity_token_validation_token_1.apply {
            nextToFocus(activity_token_validation_token_2, activity_token_validation_token_1)
            initTokenValidationWith(textInputLayout4)
        }
        activity_token_validation_token_2.apply {
            nextToFocus(activity_token_validation_token_3, activity_token_validation_token_1)
            initTokenValidationWith(textInputLayout5)
        }
        activity_token_validation_token_3.apply {
            nextToFocus(activity_token_validation_token_4, activity_token_validation_token_2)
            initTokenValidationWith(textInputLayout6)
        }
        activity_token_validation_token_4.apply {
            nextToFocus(activity_token_validation_token_5, activity_token_validation_token_3)
            initTokenValidationWith(textInputLayout7)
        }
        activity_token_validation_token_5.apply {
            nextToFocus(activity_token_validation_token_6, activity_token_validation_token_4)
            initTokenValidationWith(textInputLayout8)
        }
        activity_token_validation_token_6.apply {
            initTokenValidationWith(textInputLayout9)
            clearFocusAndHideKeyboard()
            nextToFocus(activity_token_validation_token_6, activity_token_validation_token_5)
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
                if (isValidation()) {
                    activity_token_validation_continue_button.alpha = 1f;
                    activity_token_validation_continue_button.isClickable = true;
                } else {
                    activity_token_validation_continue_button.alpha = 0.45f;
                    activity_token_validation_continue_button.isClickable = false;
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrBlank()) {
                    Keyboard.hideSoftKeyboard(
                        this@TokenValidationActivity,
                        currentFocus?.windowToken
                    )
                }
            }
        })
    }

    /**
     * Clear focus when token fields is fulfilled.
     * @receiver Input text to perform action.
     */
    private fun TextInputEditText.initTokenValidationWith(
        textInputLayout: TextInputLayout
    ) {
        val token = FormField()
        token.inicialize(
            this@TokenValidationActivity,
            this,
            activity_token_invisible_textview,
            textInputLayout
        )
        token.onRenderCountMessage("Preencher token corretamente.")
    }

    /**
     * Handle native back pressed by user.
     */
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            R.anim.left_to_right_in,
            R.anim.left_to_right_out
        )
    }

    /**
     * Handle arrow left click to perform backPressed.
     * @param view View clicked by user.
     */
    fun onClickBack(view: View) {
        onBackPressed();
    }


}
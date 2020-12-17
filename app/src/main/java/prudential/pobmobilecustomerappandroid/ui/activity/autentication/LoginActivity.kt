package prudential.pobmobilecustomerappandroid.ui.activity.autentication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_login.*
import me.aflak.libraries.dialog.FingerprintDialog
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.startActivity
import prudential.pobmobilecustomerappandroid.App
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.adjustHeight
import prudential.pobmobilecustomerappandroid.extensions.alertCustom
import prudential.pobmobilecustomerappandroid.extensions.prudentialLogin
import prudential.pobmobilecustomerappandroid.extensions.removeCPFChars
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
 * Class screen Login! This screen starts when you first enter the app.
 */

class LoginActivity : AppCompatActivity() {

    //################################################//
    //VAR
    //################################################//

    /**
     * Class variables.
     */
    companion object {
        var isRemember = false
    }

    //    val db by lazy { AppDBSingleton.inicialize(this) }
    private val userViewModel by viewModels<UserViewModel> { UserViewModelFactory(application) }
    private val configViewModel by viewModels<ConfigViewModel> { ConfigViewModelFactory(application) }

    lateinit var dialod: DialogLoadView
    var formDoc: FormField = FormField()
    var formPassword: FormField = FormField()
    var tentativas = 0
    var fakeDocOk = "078.612.534-90"
    var fakeSenha = "g10"

    //################################################//
    //INIT
    //################################################//

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        activity_login_remember_checkbox.isChecked = false

        buildEvents()
        buildForm()
        buildTest()
        isAlertPassword();
        adjustHeightButton()
    }

    fun adjustHeightButton() {
        activity_login_firstaccess_button.adjustHeight()
    }

    fun isAlertPassword() {
        Handler().postDelayed({

            if (intent != null)
                if (intent.getBooleanExtra("isAlertPassword", false))
                    alertCustom(getString(R.string.token_password_ok), TypeAlertCustom.OK);

        }, 500)


    }


    /**
     * Role responsible for starting a rapid developer test
     */

    fun buildTest() {

        if (!App.IS_TEST) {
            try {
                kotlin.run {

                    formDoc.isErro = false;
                    val cpf = App.APP.pref.getStringPreferences("cpf")
                    activity_login_cpf_input.setText(cpf)
                    formDoc.isOk = true;
                    formDoc.isErro = true;

                    if(!cpf.equals(""))
                    {
                        activity_login_remember_checkbox.isChecked = true
                        isRemember = true;
                    }

                }


            } catch (e: Exception) {

            }
        }

        if (App.IS_TEST) {
            activity_login_cpf_input.setText(fakeDocOk)
            activity_login_password_input.setText(fakeSenha)
            activity_login_remember_checkbox.isChecked = true
            activity_login_login_button.isClickable = true
            isRemember = true;

        }
    }

    /**
     * Function responsible for starting the for class that manages the form
     */

    private fun buildForm() {

        formDoc.inicialize(
            this,
            activity_login_cpf_input,
            textView3,
            activity_login_password_input_layout
        )

        formPassword.inicialize(this, activity_login_password_input, textView4, senhaLayout)
        builFormDoc()
        builFormPassword()

    }

    /**
     * Function responsible for initiating document field actions
     */

    private fun builFormDoc() {

        formDoc.addEventsDocCpFCnpjValidation({

            isValidationButton()

        })
    }

    /**
     * Function responsible for initiating password field actions
     */

    private fun builFormPassword() {

        formPassword.addEventsPassword { isValidationButton(); }
        formPassword.onSendKeyboard {
            onInitModel()
        }
    }

    /**
     * Function responsible for initiating click actions
     */

    private fun buildEvents() {

        activity_login_main.setOnClickListener { Keyboard.removeThis(this) }
        activity_login_login_button.setOnClickListener { onInitModel() }
        activity_login_remember_checkbox.onCheckedChange { buttonView, isChecked ->

            isRemember = isChecked
        }
    }

    /**
     * Function responsible for validating the form and making the connection with the API
     */

    private fun onInitModel() {

        Keyboard.removeThis(this)

        if (!activity_login_cpf_input.text.toString()
                .equals(fakeDocOk) || !activity_login_password_input.text.toString()
                .equals(fakeSenha)
        ) {

            formDoc.error("Usuário e/ou senha incorretos.")
            formPassword.error("Usuário e/ou senha incorretos.")
            alertCustom("Usuário e/ou senha incorretos.", TypeAlertCustom.ERROR)
            return
        }

        if (!isValidation()) {
            return
        }

        activity_login_login_button.isEnabled = false

        if (activity_login_remember_checkbox.isChecked) {
            App.APP.pref.addCommitPreferences(
                "cpf",
                activity_login_cpf_input.text.toString().removeCPFChars()
            )

        } else {

            App.APP.pref.addCommitPreferences("cpf", "")
        }

        //initTestValidation();

        kotlin.run {
            dialod = DialogLoadView(this)
            dialod.show()
        }

        Handler().postDelayed({

            onRequest()

        }, 1000)
    }

    /**
     * Function responsible for initiating the API request
     */

    fun onRequest() {

        val valueCPF = ""
        val valuePassword = ""

        userViewModel.login(valueCPF, valuePassword, isRemember).observe(this, Observer {

            when (it) {

                is Resource.Loading -> {

                }
                is Resource.Success -> {

                    initTestValidation()
                    Looper.myLooper()?.let {
                        Handler(it).postDelayed({

                            activity_login_login_button.isEnabled = true
                            dialod.dismiss()

                        }, 1500)
                    }
                }
                is Resource.Failure -> {

                    alertCustom(getString(R.string.erro_conexao), TypeAlertCustom.ERROR)
                    dialod.dismiss()
                    activity_login_login_button.isEnabled = true
                }
            }
        })
    }

    //################################################//
    //VALIDATION
    //################################################//

    /**
     *Function responsible for Validating the form
     */

    private fun isValidation(): Boolean {


        var resp = true
        if (!formDoc.isOk)
            resp = false

        if (!formPassword.isValidationCall())
            resp = false

        return resp
    }

    /**
     * Function responsible for Validating the form button
     */

    fun isValidationButton() {
        if (formDoc.isOk && formPassword.isOk) {
            activity_login_login_button.isClickable = true
            activity_login_login_button.alpha = 1.0f

        } else {

            activity_login_login_button.isClickable = false
            activity_login_login_button.alpha = 0.45f
        }
    }

    /**
     * Function responsible for starting a developer test, simulating the API
     */

    fun initTestValidation() {

        if (activity_login_cpf_input.text.toString()
                .equals(fakeDocOk) && activity_login_password_input.text.toString()
                .equals(fakeSenha)
        ) {

            tentativas = 0

            //if (!activity_login_remember_checkbox.isChecked) {
              //  startActivity<MainActivity>();

            //} else {
                configViewModel.config.observe(this, Observer {
                    if (it is Resource.Success) {

                        val config = it.data

                        if(config.isToken && (config.isTouch))
                        {
                            initTouch();
                        }
                        else
                        if (!config.isToken) {
                            openTokenValidationActivity()
                        } else {
                            if (!config.isTouchIdSave) {
                                startActivity<BiometricAuthActivity>("isCheckView" to true)
                            } else {
                                startActivity<MainActivity>();
                            }
                        }


                    }
                })

            //}


        } else {

            tentativas++

            if (tentativas == 4) {
                alertCustom("Você só tem mais uma tentativa.", TypeAlertCustom.ALERT)

            } else if (tentativas >= 5) {

                alertCustom("Bloqueado.", TypeAlertCustom.ERROR)

            } else {

                formDoc.error("Usuário e/ou senha incorretos.")
                formPassword.error("Usuário e/ou senha incorretos.")
                alertCustom("Usuário e/ou senha incorretos.", TypeAlertCustom.ERROR)

            }
        }
    }

    fun initTouch() {



        FingerprintDialog.initialize(this).prudentialLogin(
            this,
            getString(R.string.touch_id_title_login),
            getString(R.string.touch_id_descripition_start),
            {

                if (it) {
                    startActivity<MainActivity>()
                    finish()

                } else {

                }
            })
    }

    //################################################//
    //NAVIGATION
    //################################################//

    /**
     * Function responsible for going to the next screen forgot your password
     */

    fun openForgotPassword(view: View) {

        isRemember = true;
        startActivity<ForgotPasswordActivity>()
    }


    /**
     * Function responsible for going to next to generate new password
     */

    fun onFirstAccessClick(view: View) {

        isRemember = true;
        startActivity<ForgotPasswordActivity>("first_access" to true)
    }

    override fun onResume() {

        Log.e("onResume","onResume");
        isRemember = activity_login_remember_checkbox.isChecked;

        super.onResume()


    }

    override fun onBackPressed() {

        Keyboard.removeThis(this)
        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)

    }

    fun openTokenValidationActivity() {

        startActivity<TokenValidationActivity>("isLogin" to true)

    }


}//END CLASS
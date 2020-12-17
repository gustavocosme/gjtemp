package prudential.pobmobilecustomerappandroid.ui.activity.autentication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_forgot_password.textView3
import org.jetbrains.anko.startActivity
import prudential.pobmobilecustomerappandroid.App
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.alertCustom
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModelFactory
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.form.Keyboard
import prudential.pobmobilecustomerappandroid.ui.dialogs.DialogLoadView
import prudential.pobmobilecustomerappandroid.utils.form.FormField

/**
 * Class responsible for forgot my password and first step of creating a new password
 */

class ForgotPasswordActivity : AppCompatActivity() {

    //################################################//
    //VAR
    //################################################//

    /**
     * Class variables.
     */

    private var firstAccess = false
    var formDoc: FormField = FormField();
    lateinit var dialod: DialogLoadView;
    private lateinit var userViewModel: UserViewModel

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        showOrHideFirstAccess()
        buildForm();
        buildEvents();
        initViewModel();
        buildTest();


        overridePendingTransition(R.anim.right_to_left_in,
            R.anim.right_to_left_out);

    }

    //################################################//
    //FORM
    //################################################//

    /**
     * Role responsible for starting a rapid developer test
     */

    fun buildTest()
    {
        if(App.IS_TEST)
        {
            activity_forgot_password_input.setText("078.612.534-90");
        }
    }

    /**
     * Function responsible for starting the for class that manages the form
     */

    private fun buildForm() {

        formDoc.inicialize(this,activity_forgot_password_input,textView3,activity_forgot_password_input_layout);
        builFormDoc();

        formDoc.onSendKeyboard {
            onInitModel()
        }

    }

    /**
     * Function responsible for initiating document field actions
     */

    private fun builFormDoc() {

        formDoc.addEventsDocCpFCnpjValidation({

            isValidationButton();

        });

    }

    //################################################//
    //VALIDATION
    //################################################//

    /**
     *Function responsible for Validating the form
     */

    private fun isValidation():Boolean {

        var resp = true;
        if(!formDoc.isOk)
            resp = false;

        return resp;

    }

    /**
     * Function responsible for Validating the form button
     */

    fun isValidationButton()
    {
        if(formDoc.isOk)
        {
            activity_forgot_continue_button.isClickable = true;
            activity_forgot_continue_button.alpha = 1.0f

        }else{

            activity_forgot_continue_button.isClickable = false;
            activity_forgot_continue_button.alpha = 0.45f

        }

    }

    //################################################//
    //EVENTS USER
    //################################################//

    private fun buildEvents() {

        activity_forgot_main.setOnClickListener{ Keyboard.removeThis(this) }
        activity_forgot_continue_button.setOnClickListener { onInitModel()}

    }

    //################################################//
    //MODEL
    //################################################//


    /**
     * Function responsible for starting the class model
     */

    private fun initViewModel() {
        val userViewModelFactory =  UserViewModelFactory(application)
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }


    /**
     * Function responsible for validating the form and making the connection with the API
     */

    private fun onInitModel()
    {
        Keyboard.removeThis(this);

        if(!isValidation())
        {
            return;
        }

        activity_forgot_continue_button.isEnabled = false;

        kotlin.run {

            dialod = DialogLoadView(this);
            dialod.show();

        }

        Handler().postDelayed({

            onRequest();

        }, 1000)
    }

    /**
     * Function responsible for initiating the API request
     */


    fun onRequest() {

        val valueCPF        = activity_forgot_password_input.text.toString();
        userViewModel.forgotPassword(valueCPF,firstAccess).observe(this, Observer{


            when (it) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {

                    openCreateNewPassword()

                    Looper.myLooper()?.let {
                        Handler(it).postDelayed({

                            activity_forgot_continue_button.isEnabled = true;
                            dialod.dismiss();


                        }, 1500);
                    }


                }
                is Resource.Failure -> {

                    alertCustom(getString(R.string.erro_conexao), TypeAlertCustom.ERROR)
                    activity_forgot_continue_button.isEnabled = true;
                    dialod.dismiss();

                }
            }
        })
    }

    //################################################//
    //NAVEGATION
    //################################################//

    /**
     * Function responsible update layout new password
     */

    private fun showOrHideFirstAccess() {
        firstAccess = intent.getBooleanExtra("first_access", false)

        if(firstAccess)
        {
            findViewById<TextView>(R.id.textView).setText("Bem vindo!");
        }

        if(!firstAccess)
        {
            findViewById<TextView>(R.id.textView).setText("Esqueci minha senha");
            findViewById<TextView>(R.id.textView2).setText("Sem problemas, acontece com todo mundo.");

        }

        activity_forgot_password_flow.showOrGone(firstAccess)
    }

    /**
     * Function responsible for going to the next screen new password
     */

    fun openCreateNewPassword() {

        startActivity<NewPasswordActivity>("first_access" to firstAccess)

    }

    /**
     * Function responsible Back Screen
     */

    fun onClickBack(view: View) {
        onBackPressed();
    }

    /**
     * Function responsible Back Screen animation
     */

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(
            R.anim.left_to_right_in,
            R.anim.left_to_right_out
        )
    }

}
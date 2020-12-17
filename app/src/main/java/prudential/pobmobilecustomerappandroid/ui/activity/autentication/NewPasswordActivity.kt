package prudential.pobmobilecustomerappandroid.ui.activity.autentication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_new_password.*
import kotlinx.android.synthetic.main.activity_new_password.senhaLayout
import me.aflak.libraries.dialog.FingerprintDialog
import org.jetbrains.anko.startActivity
import prudential.pobmobilecustomerappandroid.App
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.*
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.ui.activity.app.MainActivity
import prudential.pobmobilecustomerappandroid.ui.dialogs.DialogLoadView
import prudential.pobmobilecustomerappandroid.ui.viewmodel.ConfigViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.ConfigViewModelFactory
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModelFactory
import prudential.pobmobilecustomerappandroid.utils.Dialogs
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.form.DataValidationPassword
import prudential.pobmobilecustomerappandroid.utils.form.FormField
import prudential.pobmobilecustomerappandroid.utils.form.Keyboard

class NewPasswordActivity : AppCompatActivity() {

    //################################################//
    //VAR
    //################################################//

    /**
     * Class variables.
     */
//    val db by lazy { AppDBSingleton.inicialize(this) }
    private val configViewModel by viewModels<ConfigViewModel> { ConfigViewModelFactory(application) }

    private var firstAccess = false
    private lateinit var userViewModel: UserViewModel
    lateinit var dialod: DialogLoadView
    var isValueEspecialValidation = false
    var formPassword: FormField = FormField()
    var formPassword2: FormField = FormField()
    var formDay: FormField = FormField()
    var formYear: FormField = FormField()
    var formMonth: FormField = FormField()
    var dataPasswordValidation = DataValidationPassword()
    var isTipsAnimation = false
    var selectDay = 0
    var selectMonthPosition = 0
    var isSelect = false
    val Days = intArrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    val Months = arrayOf(
        "Janeiro",
        "Fevereiro",
        "Março",
        "Abril",
        "Maio",
        "Junho",
        "Julho",
        "Agosto",
        "Setembro",
        "Outubro",
        "Novembro",
        "Dezembro"
    )

    //################################################//
    //INIT
    //################################################//

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_new_password)
        showOrHideFirstAccess()
        buildForm()
        buildEvents()
        initViewModel()
        dateNavigtionField()

        overridePendingTransition(
            R.anim.right_to_left_in,
            R.anim.right_to_left_out
        )

        isTest()
    }

    fun dateNavigtionField() {
        acivity_new_password_day_input.nextToFocus(
            acivity_new_password_month_input,
            acivity_new_password_day_input,
            2
        )
        acivity_new_password_month_input.nextToFocus(
            acivity_new_password_year_input,
            acivity_new_password_day_input,
            2
        )
        acivity_new_password_year_input.nextToFocus(
            acivity_new_password_year_input,
            acivity_new_password_month_input,
            4
        )
    }

    fun isTest() {
        if (App.IS_TEST) {
            acivity_new_password_day_input.setText("01")
            acivity_new_password_month_input.setText("2")
            acivity_new_password_year_input.setText("1988")
            txtBank.setText("qwertyui")
            acivity_new_password_confirm_password_input.setText("qwertyui")
            activity_new_password_create_button.isEnabled = true

            formDay.isOk = true
            formMonth.isOk = true
            formYear.isOk = true
            formPassword2.isOk = true
            formPassword.isOk = true

        }
    }

    //################################################//
    //FORM
    //################################################//

    /**
     * Function responsible for starting the for class that manages the form
     */

    private fun buildForm() {
        formPassword.inicialize(this, txtBank, textView7, senhaLayout)
        formPassword2.inicialize(
            this,
            acivity_new_password_confirm_password_input,
            textView8,
            senhaLayout2
        )
        formDay.inicialize(this, acivity_new_password_day_input, textView6, textInputLayout)
        formYear.inicialize(this, acivity_new_password_year_input, textView6, textInputLayout3)
        formMonth.inicialize(this, acivity_new_password_month_input, textView6, textInputLayout2)

        formPassword.addEventsPassword { isValidationButton(); }

        if (!isSelect)
            buildFormDateChange()
        else buildFormSelect()
        buildFormPasswords()

    }


    fun buildFormPasswords() {
        formPassword.onFocus = {

            formYear.isYearValidation()

            if (!isSelect)
                isDateValidation()
            else isDateValidationSelect()
        }

        formPassword.onChangePasswordValidation {
            onChangePasswordItens(it)
        }

        formPassword2.addEventsPassword { isValidationButton(); }

        formPassword2.change {
            onChangePassword2()
        }

        formPassword2.onSendKeyboard {
            onInitModel()
        }

    }

    fun buildFormDateChange() {
        formDay.addEventsDay { isValidationButton(); isDateValidation(); }
        formMonth.addEventsMonth { isValidationButton(); isDateValidation(); }
        formYear.addEventsYear { isValidationButton(); }
    }

    fun onChangePassword2() {
        if (txtBank.text.toString().count() == 0) {
            formPassword2.isOk = false
            formPassword.isOk = false
            formPassword2.error(getString(R.string.erro_password_erro_3))
            formPassword.error(getString(R.string.erro_password_erro_3))

        } else {

            if (
                acivity_new_password_confirm_password_input.text.toString().count() >=
                txtBank.text.toString().count() &&
                !txtBank.text.toString()
                    .equals(acivity_new_password_confirm_password_input.text.toString())
            ) {

                formPassword2.isOk = false
                formPassword2.error(getString(R.string.erro_password_erro))

            } else {

                if (!isValueEspecialValidation) {
                    alertCustom(getString(R.string.erro_password_erro_2), TypeAlertCustom.ERROR)
                    formPassword2.isOk = false
                    formPassword2.error(getString(R.string.erro_password_erro))
                } else {
                    formPassword2.isOk = true
                    formPassword2.errorClose()
                }
            }
        }
        isValidationButton()
    }


    //################################################//
    //DATES SELECT
    //################################################//

    fun buildFormSelect() {
        acivity_new_password_btn_year.visibility = View.VISIBLE
        acivity_new_password_btn_month.visibility = View.VISIBLE
        acivity_new_password_btn_day.visibility = View.VISIBLE

        acivity_new_password_day_input.isEnabled = false
        acivity_new_password_month_input.isEnabled = false
        acivity_new_password_year_input.isEnabled = false
        acivity_new_password_day_input.isFocusable = false
        acivity_new_password_month_input.isFocusable = false
        acivity_new_password_year_input.isFocusable = false

        val maxLength = 3
        val filters = arrayOfNulls<InputFilter>(1)
        filters[0] = LengthFilter(maxLength)
        acivity_new_password_month_input.filters = filters

        acivity_new_password_year_input.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_triangle,
            0
        )
        acivity_new_password_month_input.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_triangle,
            0
        )
        acivity_new_password_day_input.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            R.drawable.ic_triangle,
            0
        )

    }

    fun onSelectDay(v: View) {
        var total = 31

        val array = ArrayList<String>()
        for (i in 1..total) {
            array.add(i.toString())
        }

        Dialogs.addListA(this, "Dia", array, object : Dialogs.CALL {

            override fun call(value: String?, p: Int) {

                selectDay = value!!.toInt()
                formDay.daySelect = selectDay

                if (selectMonthPosition != 0) {
                    if (selectDay > Days[selectMonthPosition]) {

                        acivity_new_password_day_input.setText("")
                        formDay.isOk = false
                        formDay.error(getString(R.string.erro_password_day_month))

                    } else {

                        acivity_new_password_day_input.setText(value)
                        formDay.setValue(value)
                        formDay.isOk = true
                        formMonth.errorClose()
                        formDay.errorClose()
                    }

                } else {

                    formDay.setValue(value)
                    acivity_new_password_day_input.setText(value)
                    formDay.isOk = true
                    formDay.errorClose()
                }
                selectDay = p
                isValidationButton()
            }
        })
    }

    fun onSelectMonht(v: View) {
        Dialogs.addList(this, "Mês", Months, object : Dialogs.CALL {

            override fun call(value: String?, p: Int) {

                selectMonthPosition = p
                formDay.monthSelect = selectMonthPosition

                if (selectDay != 0) {
                    if (selectDay > Days[selectMonthPosition]) {
                        acivity_new_password_month_input.setText("")
                        formMonth.isOk = false
                        formMonth.error(getString(R.string.erro_password_day_month))
                    } else {
                        acivity_new_password_month_input.setText(value!!)
                        formMonth.setValue(value)
                        formMonth.isOk = true
                        formMonth.errorClose()
                    }
                } else {

                    formMonth.isOk = true
                    formMonth.setValue(value!!)
                    formMonth.errorClose()
                    acivity_new_password_month_input.setText(value)
                }

                isValidationButton()
            }
        })
    }

    fun onSelectYear(v: View) {
        val array = ArrayList<String>()
        for (i in 1940..2004) {

            array.add(i.toString())

        }

        Dialogs.addListA(this, "Ano", array, object : Dialogs.CALL {

            override fun call(value: String?, p: Int) {

                acivity_new_password_year_input.setText(value!!)
                formYear.setValue(value)
                formYear.errorClose()
                formYear.isOk = true
                isDateValidationSelect()
                isValidationButton()

            }

        })
    }

    //################################################//
    //EVENTS USER
    //################################################//

    private fun buildEvents() {
        acivity_new_password_main.setOnClickListener { Keyboard.removeThis(this) }
        activity_new_password_create_button.setOnClickListener { onInitModel() }

    }

    //################################################//
    //VALIDATION
    //################################################//

    /**
     *Function responsible for Validating Dates
     */

    fun isDateValidationSelect() {
        if (!formDay.isOk) {
            formDay.error(getString(R.string.erro_data_erro))
        }

        if (!formMonth.isOk) {
            formMonth.error(getString(R.string.erro_data_erro))
        }

        if (!formYear.isOk) {
            formYear.error(getString(R.string.erro_data_erro))
        }
    }

    fun isDateValidation() {

        if (formDay.daySelect == 0 || formMonth.monthSelect == 0) {
            return
        }

        if (!formDay.dateValidation(formDay.daySelect, formMonth.monthSelect)) {
            alertCustom(getString(R.string.erro_password_day_month), TypeAlertCustom.ERROR)
            formDay.error(getString(R.string.erro_password_day_month))
            formMonth.error(getString(R.string.erro_password_day_month))

            formDay.isOk = false
            formMonth.isOk = false
            isValidationButton()
        } else {
            formDay.isOk = true
            formMonth.isOk = true
            formDay.errorClose()
            formMonth.errorClose()
            isValidationButton()
        }
    }

    /**
     *Function responsible for Validating Icons password
     */

    fun onChangePasswordItens(data: DataValidationPassword) {
        if (data.CHAR_8) {
            activity_new_password_length_text_validation.setImageResource(0)
            activity_new_password_length_text_validation.setImageResource(R.drawable.ic_check_circle_correct)
        } else {
            activity_new_password_length_text_validation.setImageResource(0)
            activity_new_password_length_text_validation.setImageResource(R.drawable.ic_check_circle)
        }
        if (!data.NO_3_REPETION) {
            activity_new_password_number_text_validation.setImageResource(0)
            activity_new_password_number_text_validation.setImageResource(R.drawable.ic_check_circle_correct)
        } else {
            activity_new_password_number_text_validation.setImageResource(0)
            activity_new_password_number_text_validation.setImageResource(R.drawable.ic_check_circle)
        }

        dataPasswordValidation = data

        if (data.CHAR_8 && !data.NO_3_REPETION) {
            formPassword.isValidationCall()
            isValueEspecialValidation = true

        } else {
            isValueEspecialValidation = false
        }
    }

    /**
     *Function responsible for all form
     */

    private fun isValidation(): Boolean {
        var resp = true

        if (!formDay.isOk)
            resp = false

        if (!formMonth.isOk)
            resp = false

        if (!formYear.isOk)
            resp = false

        if (!formPassword.isOk)
            resp = false

        formPassword2.setValue(txtBank.text.toString())
        formPassword2.onRendePassWordValidationCopy(txtBank.text.toString())
        if (!formPassword2.isOk)
            resp = false

        if (!isValueEspecialValidation) {
            alertCustom(getString(R.string.erro_password_no), TypeAlertCustom.ERROR)
            resp = false
        }

        return resp
    }

    /**
     *Function responsible for validation button form
     */

    fun isValidationButton() {
        var passCopy = false

        if (txtBank.text.toString()
                .equals(acivity_new_password_confirm_password_input.text.toString())
        )
            passCopy = true

        if (formPassword.isOk && formPassword2.isOk && formDay.isOk && formYear.isOk && formMonth.isOk && formPassword2.isOk && formPassword.isOk && passCopy) {
            activity_new_password_create_button.isClickable = true
            activity_new_password_create_button.alpha = 1.0f

        } else {

            activity_new_password_create_button.isClickable = false
            activity_new_password_create_button.alpha = 0.45f
        }
    }

    /**
     * Function responsible for starting the class model
     */
    private fun initViewModel() {
        val userViewModelFactory = UserViewModelFactory(application)
        userViewModel = ViewModelProvider(this, userViewModelFactory).get(UserViewModel::class.java)
    }

    /**
     * Function responsible for validating the form and making the connection with the API
     */
    private fun onInitModel() {
        Keyboard.removeThis(this)

        if (!isValidation()) {
            return
        }

        kotlin.run {

            dialod = DialogLoadView(this)
            dialod.show()

        }

        activity_new_password_create_button.isEnabled = false

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
        val valeuDay = ""

        //val valeuMonth      = acivity_new_password_month_input.text.toString();
        //val valeuYear       = acivity_new_password_year_input.text.toString();

        userViewModel.newPassword(valueCPF, valuePassword, valeuDay, LoginActivity.isRemember)
            .observe(this, Observer {

                when (it) {
                    is Resource.Success -> {

                        dialod.dismiss()

                        kotlin.run {
                            openTokenValidationActivity()
                            //activity_new_password_create_button.isEnabled = true;
                            Looper.myLooper()?.let {
                                Handler(it).postDelayed({
                                    activity_new_password_create_button.isEnabled = true
                                }, 1500)
                            }
                        }

                    }
                    is Resource.Failure -> {

                        alertCustom(getString(R.string.erro_conexao), TypeAlertCustom.ERROR)
                        dialod.dismiss()

                    }
                }
            })
    }

    /**
     * Function responsible update layout new password
     */
    private fun showOrHideFirstAccess() {
        firstAccess = intent.getBooleanExtra("first_access", false)

        if (!firstAccess) {
            findViewById<TextView>(R.id.textView2).text = "Ok, vamos mudar sua senha."
        }

        activity_new_password_flow.showOrGone(firstAccess)
    }

    /**
     * Function responsible for going to the next screen TOKEN
     */
    fun openTokenValidationActivity() {

        if(firstAccess)
        {
            startActivity<TokenValidationActivity>("first_access" to firstAccess)
            return;
        }

        configViewModel.config.observe(this, Observer {
            if (it is Resource.Success) {




                val config = it.data

                if(config.isToken && (config.isTouch))
                {
                    initTouch();
                }
                else
                if (!config.isToken) {
                    startActivity<TokenValidationActivity>("first_access" to firstAccess)
                } else {
                    when {

                        //!firstAccess -> startActivity<TokenValidationActivity>("isAlertPassword" to true)
                        config.isTouchIdSave or config.isToken -> startActivity<MainActivity>()

                        else -> {
                            if (!config.isTouchIdSave) {
                                startActivity<BiometricAuthActivity>("isCheckView" to false)
                            } else {
                                startActivity<MainActivity>()
                            }
                        }
                    }
                }
            }
        })
    }

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
     * Function responsible Back Screen
     */
    fun onClickBack(view: View) {
        onBackPressed()
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

    fun onClickTips(v: View) {
        Keyboard.removeThis(this)

        if (isTipsAnimation) {
            isTipsAnimation = false
            acivity_new_password_tips_triangle.hideAnime({})
            acivity_new_password_tips.hideAnime({
                acivity_new_password_tips.visibility = View.GONE
                acivity_new_password_tips_triangle.visibility = View.GONE
                true
            })

            acivity_new_password_tips_triangle.hideAnime({ true })

        } else {
            this.isTipsAnimation = true
            acivity_new_password_tips.showAnime()
            acivity_new_password_tips_triangle.showAnime()
        }
    }
}
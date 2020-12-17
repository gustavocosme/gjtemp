package prudential.pobmobilecustomerappandroid.ui.activity.autentication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_biometric_auth.*
import org.jetbrains.anko.startActivity
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.alertCustom
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.ui.activity.app.MainActivity
import prudential.pobmobilecustomerappandroid.ui.dialogs.DialogLoadView
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModelFactory
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.form.Keyboard

/**
 * Class screen BiometricAuthActivity! This screen validation touchID.
 */

class BiometricAuthOkActivity : AppCompatActivity() {


    /**
     * Class variables.
     */

    private lateinit var userViewModel: UserViewModel

    lateinit var dialod: DialogLoadView

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_biometric_auth_ok)
        cardView.showAnime(1000)

        initViewModel()

    }

    /**
     * Function responsible for initiating click actions Next
     */

    fun onClickNext(v: View) {

        onInitModel()
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

        activity_biometric_skip_button.isEnabled = false

        val valueCPF = ""
        val valuePassword = ""

        userViewModel.login(valueCPF, valuePassword, false)
            .observe(this, Observer {

                when (it) {

                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {


                        startActivity<MainActivity>()

                        Looper.myLooper()?.let {
                            Handler(it).postDelayed({

                                activity_biometric_skip_button.isEnabled = true
                                dialod.dismiss()

                            }, 1500)
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
     * Function responsible for Back home close
     */

    override fun onBackPressed() {

        Keyboard.removeThis(this)

        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)

    }

}//END CLASS
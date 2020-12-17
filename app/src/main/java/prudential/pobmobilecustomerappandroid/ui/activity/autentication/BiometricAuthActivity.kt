package prudential.pobmobilecustomerappandroid.ui.activity.autentication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_biometric_auth.*
import me.aflak.libraries.dialog.FingerprintDialog
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.startActivity
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.adjustHeight
import prudential.pobmobilecustomerappandroid.extensions.alertCustom
import prudential.pobmobilecustomerappandroid.extensions.prudential
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.ui.activity.app.MainActivity
import prudential.pobmobilecustomerappandroid.ui.dialogs.DialogLoadView
import prudential.pobmobilecustomerappandroid.ui.viewmodel.ConfigViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.ConfigViewModelFactory
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModelFactory
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.form.Keyboard

/**
 * Class screen BiometricAuthActivity! This screen validation touchID.
 */

class BiometricAuthActivity : AppCompatActivity() {


    /**
     * Class variables.
     */


//    val db by lazy { AppDBSingleton.inicialize(this) }
    private val configViewModel by viewModels<ConfigViewModel> { ConfigViewModelFactory(application) }
    private val userViewModel by viewModels<UserViewModel> { UserViewModelFactory(application) }
    var isCheckView = false
    var isTouch = false
    lateinit var dialod: DialogLoadView

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric_auth)
        adjustHeightButton()
        showOrHideCheck()
        cardView.showAnime(1000)

        activity_biometric_check.isChecked = false
        activity_biometric_check.onCheckedChange { buttonView, isChecked ->
            //Config.CURRENT!!.isTouchIdSave = isChecked
            //ConfigDao.updateCurrent()

//            db.onUpdateConfigUpdateTouchBtn(isChecked);
            configViewModel.onUpdateConfigUpdateTouchBtn(isChecked)

        }

    }

    fun adjustHeightButton() {
        activity_biometric_authorize_button.adjustHeight()
    }

    /**
     * Function responsible for initiating click actions Next
     */

    fun onClickNext(v: View) {

        //Config.CURRENT!!.isTouch = false;
        //ConfigDao.updateCurrent();
//        db.onUpdateConfigUpdateTouch(false);
        configViewModel.onUpdateConfigUpdateTouch(false)
        onInitModel();

    }

    /**
     * Function responsible for initiating click actions Validation TouchId
     */

    fun onClickOk(v: View) {


        FingerprintDialog.initialize(this).prudential(
            context = this,
            title = getString(R.string.touch_id_title_cadastre),
            message = getString(R.string.touch_id_descripition),
            call = {
                if (it) {

                    isTouch = true;
                    //Config.CURRENT!!.isTouch = true
                    //ConfigDao.updateCurrent();
//                    db.onUpdateConfigUpdateTouch(true);
                    configViewModel.onUpdateConfigUpdateTouch(true)
                    onInitModel()
                } else {
                    userViewModel.logout.observe(this, Observer {
                        /**
                         * Do something on logout.
                         */
                    })
                }
            })

    }

    /**
     * Function responsible update layout new password
     */

    private fun showOrHideCheck() {


        //if(Config.CURRENT!!.touchIdCount > 0)
        //{
        //isCheckView = true;

        //}else{
        // isCheckView = false;

        //}

        //Config.CURRENT!!.touchIdCount = Config.CURRENT!!.touchIdCount + 1
        //ConfigDao.updateCurrent()

        isCheckView = intent.getBooleanExtra("isCheckView", false)


        if (isCheckView) {
            activity_biometric_check.visibility = View.VISIBLE

        } else {

            activity_biometric_check.visibility = View.GONE
        }


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

        userViewModel.login(valueCPF, valuePassword, LoginActivity.isRemember)
            .observe(this, Observer {

                when (it) {

                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {


                        if (isTouch) {
                            startActivity<BiometricAuthOkActivity>()
                        } else {
                            startActivity<MainActivity>()
                        }

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
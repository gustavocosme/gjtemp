package prudential.pobmobilecustomerappandroid.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nineoldandroids.view.ViewHelper
import kotlinx.android.synthetic.main.activity_splash_screen.*
import me.aflak.libraries.dialog.FingerprintDialog
import org.jetbrains.anko.startActivity
import prudential.pobmobilecustomerappandroid.App
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.prudentialLogin
import prudential.pobmobilecustomerappandroid.model.Resource
import prudential.pobmobilecustomerappandroid.ui.activity.app.MainActivity
import prudential.pobmobilecustomerappandroid.ui.activity.autentication.LoginActivity
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModel
import prudential.pobmobilecustomerappandroid.ui.viewmodel.UserViewModelFactory

/**
 * Class build to show logo for a few seconds and start main activity.
 */
class SplashScreen : AppCompatActivity() {

    //    val db by lazy { AppDBSingleton.inicialize(this) }
    private val userViewModel by viewModels<UserViewModel> { UserViewModelFactory(application) }

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen)

        showSplashScreen();
        buildAnimate();
    }

    /**
     * Builds animation fade out.
     */
    fun buildAnimate() {
        ViewHelper.setAlpha(activity_splash_logo, 0f);
        com.nineoldandroids.view.ViewPropertyAnimator.animate(activity_splash_logo)
            .setDuration(2500)
            .alpha(1.0f)
    }

    /**
     * Show logo for 2 seconds, then start main activity....
     */
    fun showSplashScreen() {
        val time = if (App.IS_TEST) 0L else 3000L;
        Looper.myLooper()?.let {
            Handler(it).postDelayed({


                if(true)
                {
                    startActivity<MainActivity>()
                    finish()

                }


                //checkIfUserIsNullAndInitActivty()



//                if (db.isUser()) {
//                    if (db.config!!.isTouch && db.user!!.isLogin) {
//
//                        kotlin.run {
//
//                            initTouch();
//
//                        }
//
//                    } else
//                        if (db.user!!.isLogin) {
//                            startActivity<MainActivity>()
//                            finish()
//
//                        }
//
//                } else {
//
//                    startActivity<LoginActivity>()
//                    finish()
//
//                }
            }, time);
        }
    }

    /**
     * Check if user is exists and perform action on response.
     */
    private fun checkIfUserIsNullAndInitActivty() {
        userViewModel.isUserExists.observe(this, Observer {
            if (it is Resource.Success) {
                val exists = it.data
                if (exists) {
                    checkIfIsLoggedAndTouchIdEnable()
                } else {
                    startActivity<LoginActivity>()
                    finish()
                }
            }
        })
    }

    /**
     * Check if user is logged and if user has enable login with Touch Id.
     */
    private fun checkIfIsLoggedAndTouchIdEnable() {
        userViewModel.isLoggedAndTouchIdEnable.observe(this, Observer {
            if (it is Resource.Success) {
                val loggedAndTouchIdEnable = it.data
                if (loggedAndTouchIdEnable) {
                    initTouch()
                } else {
                    checkIfUserIsLogged()
                }
            }
        })
    }

    /**
     * Check if user is already logged.
     */
    private fun checkIfUserIsLogged() {
        userViewModel.isLogged.observe(this, Observer {
            if (it is Resource.Success) {
                val isLogged = it.data
                if (isLogged) {
                    startActivity<MainActivity>()
                    finish()
                }
            }
        })
    }

    /**
     * Show Touch Id dialog to perform user login.
     */
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
                    userViewModel.logout.observe(this, Observer {
                        if (it is Resource.Success) {
                            startActivity<LoginActivity>()
                            finish()
                        }
                    })
                }
            })
    }

}
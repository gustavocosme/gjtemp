package prudential.pobmobilecustomerappandroid.extensions

import android.content.Context
import me.aflak.libraries.callback.FingerprintDialogSecureCallback
import me.aflak.libraries.dialog.FingerprintDialog
import me.aflak.libraries.utils.FingerprintToken
import org.jetbrains.anko.startActivity
import prudential.pobmobilecustomerappandroid.db.AppDBSingleton
import prudential.pobmobilecustomerappandroid.App
import prudential.pobmobilecustomerappandroid.ui.activity.autentication.LoginActivity

/**
 * Check if touch id dialog is available.
 * @receiver instance of [FingerprintDialog] to use it's companion functions
 * @param context Context of app to check finger print.
 * @return true if its available, false if not.
 */
fun FingerprintDialog.isValidation(context: Context): Boolean {
    return FingerprintDialog.isAvailable(context);
}

/**
 * Check if device has fingerprint reader and show if does.
 * Perform login with finger print.
 * @receiver instance of [FingerprintDialog]
 * @param context Context of screen to show dialog.
 * @param title Title to be shown in dialog.
 * @param message Message to be shown in dialog
 * @param call Perform callback with boolean after success or cancel.
 */
fun FingerprintDialog.prudentialLogin(
    context: Context,
    title: String,
    message: String,
    call: (Boolean) -> Any
) {

    if (FingerprintDialog.isAvailable(context)) {
        FingerprintDialog.initialize(context)
            .title(title)
            .message(message)
            .callback(object : FingerprintDialogSecureCallback {
                override fun onAuthenticationCancel() {
                    call(false)
//                    val db by lazy { AppDBSingleton.inicialize(context) }
//                    db.onLogout();
//
//                    context.startActivity<LoginActivity>()
                }

                override fun onAuthenticationSucceeded() {
                    call(true);
                }

                override fun onNewFingerprintEnrolled(token: FingerprintToken?) {
                }

            }, "KeyName1")
            .tryLimit(2, {

            })
            .usePasswordButton({
                call(false)
//                val db by lazy { AppDBSingleton.inicialize(context) }
//                db.onLogout();
//                context.startActivity<LoginActivity>()

            }).show();
    } else {
        call(false);
    }
}

/**
 * Check if device has fingerprint reader and show if does.
 * @receiver instance of [FingerprintDialog]
 * @param context Context of screen to show dialog.
 * @param title Title to be shown in dialog.
 * @param message Message to be shown in dialog
 * @param call Perform callback with boolean after success or cancel.
 */
fun FingerprintDialog.prudential(
    context: Context,
    title: String,
    message: String,
    call: (Boolean) -> Any
) {

    if (FingerprintDialog.isAvailable(context)) {

        FingerprintDialog.initialize(context)
            .title(title)
            .message(message)
            .callback(object : FingerprintDialogSecureCallback {

                override fun onAuthenticationCancel() {

//                    val db by lazy { AppDBSingleton.inicialize(context) }
//                    db.onLogout();
                    call(false);

                }

                override fun onAuthenticationSucceeded() {
                    call(true);
                }

                override fun onNewFingerprintEnrolled(token: FingerprintToken?) {
                }
            }, "KeyName1")
            .show();
    } else {
        call(false);
    }
}
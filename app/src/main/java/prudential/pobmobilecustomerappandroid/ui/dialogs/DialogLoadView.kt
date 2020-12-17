package prudential.pobmobilecustomerappandroid.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import org.jetbrains.anko.keyguardManager
import prudential.pobmobilecustomerappandroid.R

/**
 * Class to hendle dialog view.
 * @property getContext Context to show dialog.
 */
class DialogLoadView(context: Context) : Dialog(context) {

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(window!!.currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT));
        window!!.setBackgroundDrawableResource(android.R.color.transparent);
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        setCanceledOnTouchOutside(false)
    }

    /**
     * Called after view is created.
     */
    override fun onStart() {
        super.onStart()

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        window!!.setLayout(width, height)
    }


}
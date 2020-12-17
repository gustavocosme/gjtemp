package prudential.pobmobilecustomerappandroid.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import kotlinx.android.synthetic.main.dialog_text.*
import prudential.pobmobilecustomerappandroid.R

/**
 * Class to handle dialog with text.
 * @param context
 */
class DialogText(context: Context,titleP:String,messageP:String) : Dialog(context) {

    var title = "";
    var message = "";

    /**
     * Init vars
     */
    init {
        title = titleP;
        message = messageP;

    }

    /**
     * Start class events and inflate layout.
     * @param savedInstanceState - Get state of application.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_text)

        btnClose.setOnClickListener {
            dismiss();
        }

        btnClose2.setOnClickListener {
            dismiss();
        }

        try {

            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(window!!.currentFocus!!.windowToken, 0)

        }catch (e:Exception)
        {

        }

        window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT));
        window!!.setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(true)

        txtTitleDialog.setText(title);
        txtMessageDialog.setText(message);

    }

    /**
     * Method called after on create.
     */
    override fun onStart() {
        super.onStart()

        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        window!!.setLayout(width, height)
    }


}
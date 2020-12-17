package prudential.pobmobilecustomerappandroid.ui.dialogs

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.dialog_protocal_ok.*
import kotlinx.android.synthetic.main.dialog_text.btnClose
import kotlinx.android.synthetic.main.dialog_text.txtTitleDialog
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.alertCustom
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.TypeExtractDialogSheet


/**
 * Class to handle protocol copy dialog.
 */
class DialogProtocalCopy(
    context: Context,
    title1P: String,
    title2P: String,
    title3P: String,
    dataP: String,
    codeP: String,
    messageP: String,
    isCopyP: Boolean = true,
    typeP: TypeExtractDialogSheet = TypeExtractDialogSheet.NORMAL
) : Dialog(context) {

    var title = "";
    var message = "";
    var code = "";
    var date = "";
    var title2 = "";
    var title3 = "";
    var isCopy = true;
    var type = TypeExtractDialogSheet.NORMAL;

    /**
     * Init vars
     */
    init {
        title = title1P;
        title2 = title2P;
        title3 = title3P;

        message = messageP;
        code = codeP;
        date = dataP;
        isCopy = isCopyP
        type = typeP;
    }

    /**
     * Method called when Dialog is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.dialog_protocal_ok);

        btnClose.setOnClickListener {
            dismiss();
        }

        iconCopy.setOnClickListener {
            onClickCopy();
        }

        cardCopyG.setOnClickListener {
            onClickCopy();
        }

        txtTitleDialog.setText(title);
        txtMessageSub.setText(title2);
        txtStatus.setText(title3);

        txtMessageDialogProtocal.setText(message);
        txtCod.setText("Número do protocolo: " + code);

        if (message.equals("")) {
            txtMessageDialogProtocal.showOrGone(false);
        }

        txtDates.setText(date);

        cardCopyG.showOrGone(isCopy)

        if (type == TypeExtractDialogSheet.ATENCION) {
            cardCopyG.showOrGone(false);
            iconStatus.setImageResource(0)
            iconStatus.setImageResource(R.drawable.ic_extract_atencion_orange)
            txtStatus.setTextColor(context.resources.getColor(R.color.attentionColor))
        }

        try {

            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(window!!.currentFocus!!.windowToken, 0)

        } catch (e: Exception) {
        }

        window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT));
        window!!.setBackgroundDrawableResource(android.R.color.transparent);
        setCanceledOnTouchOutside(true)

    }

    /**
     * Copy text to clipboard.
     */
    fun onClickCopy() {

        val copy = "Protocolo de solicitação: 1828738\n" +
                "Seguro: 1283747\n" +
                "Vida Inteira Mais\n" +
                "Pagamento de prêmio\n" +
                "Em: 10/11/2020 às 19:09"

        val clipboardManager =
            context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clipData = ClipData.newPlainText(copy, copy)
        clipboardManager!!.setPrimaryClip(clipData)
        alertCustom(context.getString(R.string.copy_sucess), TypeAlertCustom.OK);


        //dismiss();

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
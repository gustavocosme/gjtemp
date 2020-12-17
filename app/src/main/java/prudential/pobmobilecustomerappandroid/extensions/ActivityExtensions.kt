package prudential.pobmobilecustomerappandroid.extensions

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.nineoldandroids.view.ViewHelper
import com.nineoldandroids.view.ViewPropertyAnimator
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.model.PaymentBasicaModel
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.dialogs.DialogProtocalCopy
import prudential.pobmobilecustomerappandroid.ui.dialogs.DialogText
import prudential.pobmobilecustomerappandroid.ui.dialogs.ExtractDialogSheet
import prudential.pobmobilecustomerappandroid.utils.Dialogs
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.TypeExtractDialogSheet


/**
 * Class to store state of custom alert.
 */
class ActivityExtensions {
    /**
     * @property isCustomAlert Store state to define if custom alert should be visible or not.
     */
    companion object {
        var isCustomAlert = false;
    }
}

/**
 * Show custom banner view with custom message to validation input fields.
 * @receiver AppCompatActivity context.
 * @param message REceive string message to show on layout.
 * @param type Type of custom alert ([TypeAlertCustom]).
 * @param timeSleep Time message will be shown.
 */

fun AppCompatActivity.alertCustom(message: String, type: TypeAlertCustom, timeSleep: Long = 2,title:String = "") {
    if (ActivityExtensions.isCustomAlert) {
        return;
    }

    ActivityExtensions.isCustomAlert = true;

    var param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT
    );

    var hs = (24 * resources.getDisplayMetrics().density).toInt();
    param.setMargins(0, hs+10, 0, 0)

    val v = RelativeLayout(this);
    v.layoutParams = param;


    var i = R.layout.banner_attention;

    if(!title.equals(""))
    i = R.layout.banner_attention_title;

    val viewA = LayoutInflater.from(this).inflate(i, null, false)

    v.visibility = View.GONE;
    v.addView(viewA, param);



    val border = viewA.findViewById<MaterialCardView>(R.id.card)

    val txt = viewA.findViewById<TextView>(R.id.banner_attention_text_message)
    txt.setText(message);
    val titleV = viewA.findViewById<TextView>(R.id.banner_attention_text_title)
    titleV.setText(title)

    val btnClose = viewA.findViewById<ImageButton>(R.id.banner_attention_button_close)
    val topImg = viewA.findViewById<ImageView>(R.id.banner_attention_top_image)
    val icon = viewA.findViewById<ImageView>(R.id.banner_attention_icon_attention)

    if (type == TypeAlertCustom.ALERT) {
        icon.setImageResource(R.drawable.ic_alert);
        border.setStrokeColor(ContextCompat.getColor(this, R.color.attentionColor))
        topImg.setBackgroundColor(ContextCompat.getColor(this, R.color.attentionColor))
        titleV.setTextColor(ContextCompat.getColor(this, R.color.attentionColor))

    }

    if (type == TypeAlertCustom.ERROR) {
        icon.setImageResource(R.drawable.ic_attention);
        border.setStrokeColor(ContextCompat.getColor(this, R.color.errorColor))
        topImg.setBackgroundColor(ContextCompat.getColor(this, R.color.errorColor))
        titleV.setTextColor(ContextCompat.getColor(this, R.color.errorColor))

    }

    if (type == TypeAlertCustom.OK) {
        icon.setImageResource(R.drawable.ic_check_circle_correct);
        border.setStrokeColor(ContextCompat.getColor(this, R.color.colorGreenSuccess))
        topImg.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreenSuccess))
        titleV.setTextColor(ContextCompat.getColor(this, R.color.colorGreenSuccess))

    }


    v.visibility = View.VISIBLE;
    ViewHelper.setY(v, -800f)

    var timeAnime: Long = 200
    var delayRemove: Long = timeAnime * 2

    ViewPropertyAnimator.animate(v).setDuration(timeAnime)
        .y(33f)

    btnClose.setOnClickListener {
        run {
            ViewPropertyAnimator.animate(v).setDuration(timeAnime)
                .y(-800f)
        }

        Handler().postDelayed({


            ActivityExtensions.isCustomAlert = false;
            v.removeAllViews();
        }, delayRemove)

    }

    Handler().postDelayed({
        kotlin.run {
            ViewPropertyAnimator.animate(v).setDuration(timeAnime)
                .y(-800f)
        }

        Handler().postDelayed({
            ActivityExtensions.isCustomAlert = false;
            v.removeAllViews();

        }, delayRemove)

    }, timeSleep * 1000)

    addContentView(v, param);
}

/**
 * Show custom banner view with custom message to validation input fields.
 * @receiver AppCompatActivity context.
 * @param message REceive string message to show on layout.
 * @param type Type of custom alert ([TypeAlertCustom]).
 * @param timeSleep Time message will be shown.
 */
fun Dialog.alertCustom(message: String, type: TypeAlertCustom, timeSleep: Long = 2,title:String = "") {
    if (ActivityExtensions.isCustomAlert) {
        return;
    }
    ActivityExtensions.isCustomAlert = true;
    var param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT
    );
    param.setMargins(0, 0, 0, 0)

    var i = R.layout.banner_attention;

    if(!title.equals(""))
        i = R.layout.banner_attention_title;

    val v = RelativeLayout(context);
    v.layoutParams = param;
    val viewA = LayoutInflater.from(context).inflate(i, null, false)
    ViewHelper.setY(v, -800f)
    v.visibility = View.GONE;
    v.addView(viewA, param);

    val border = viewA.findViewById<MaterialCardView>(R.id.card)

    val txt = viewA.findViewById<TextView>(R.id.banner_attention_text_message)
    txt.setText(message);
    val titleV = viewA.findViewById<TextView>(R.id.banner_attention_text_title)
    titleV.setText(title)

    val btnClose = viewA.findViewById<ImageButton>(R.id.banner_attention_button_close)
    val topImg = viewA.findViewById<ImageView>(R.id.banner_attention_top_image)
    val icon = viewA.findViewById<ImageView>(R.id.banner_attention_icon_attention)

    if (type == TypeAlertCustom.ALERT) {
        icon.setImageResource(R.drawable.ic_alert);
        border.setStrokeColor(ContextCompat.getColor(context, R.color.attentionColor))
        topImg.setBackgroundColor(ContextCompat.getColor(context, R.color.attentionColor))
        titleV.setTextColor(ContextCompat.getColor(context, R.color.attentionColor))

    }

    if (type == TypeAlertCustom.ERROR) {
        icon.setImageResource(R.drawable.ic_attention);
        border.setStrokeColor(ContextCompat.getColor(context, R.color.errorColor))
        topImg.setBackgroundColor(ContextCompat.getColor(context, R.color.errorColor))
        titleV.setTextColor(ContextCompat.getColor(context, R.color.errorColor))

    }

    if (type == TypeAlertCustom.OK) {
        icon.setImageResource(R.drawable.ic_check_circle_correct);
        border.setStrokeColor(ContextCompat.getColor(context, R.color.colorGreenSuccess))
        topImg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreenSuccess))
        titleV.setTextColor(ContextCompat.getColor(context, R.color.colorGreenSuccess))
    }

    v.visibility = View.VISIBLE;

    var timeAnime: Long = 200
    var delayRemove: Long = timeAnime * 2

    ViewPropertyAnimator.animate(v).setDuration(timeAnime)
        .y(33f)

    btnClose.setOnClickListener {
        run {
            ViewPropertyAnimator.animate(v).setDuration(timeAnime)
                .y(-800f)
        }

        Handler().postDelayed({


            ActivityExtensions.isCustomAlert = false;
            v.removeAllViews();
        }, delayRemove)

    }

    Handler().postDelayed({
        kotlin.run {
            ViewPropertyAnimator.animate(v).setDuration(timeAnime)
                .y(-800f)
        }

        Handler().postDelayed({
            ActivityExtensions.isCustomAlert = false;
            v.removeAllViews();

        }, delayRemove)

    }, timeSleep * 1000)

    addContentView(v, param);
}

/**
 * Show custom banner view with custom message to validation input fields.
 * @receiver AppCompatActivity context.
 * @param message REceive string message to show on layout.
 * @param type Type of custom alert ([TypeAlertCustom]).
 * @param timeSleep Time message will be shown.
 */
fun ConstraintLayout.alertCustom(message: String, type: TypeAlertCustom, timeSleep: Long = 2,title:String = "") {
    if (ActivityExtensions.isCustomAlert) {
        return;
    }
    ActivityExtensions.isCustomAlert = true;
    var param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT
    );
    param.setMargins(0, 0, 0, 0)

    var i = R.layout.banner_attention;

    if(!title.equals(""))
        i = R.layout.banner_attention_title;

    val v = RelativeLayout(context);
    v.layoutParams = param;
    val viewA = LayoutInflater.from(v.context).inflate(i, null, false)
    ViewHelper.setY(v, -800f)
    v.visibility = View.GONE;
    v.addView(viewA, param);

    val border = viewA.findViewById<MaterialCardView>(R.id.card)

    val txt = viewA.findViewById<TextView>(R.id.banner_attention_text_message)
    txt.setText(message);
    val titleV = viewA.findViewById<TextView>(R.id.banner_attention_text_title)
    titleV.setText(title)

    val btnClose = viewA.findViewById<ImageButton>(R.id.banner_attention_button_close)
    val topImg = viewA.findViewById<ImageView>(R.id.banner_attention_top_image)
    val icon = viewA.findViewById<ImageView>(R.id.banner_attention_icon_attention)

    if (type == TypeAlertCustom.ALERT) {
        icon.setImageResource(R.drawable.ic_alert);
        border.setStrokeColor(ContextCompat.getColor(context, R.color.attentionColor))
        topImg.setBackgroundColor(ContextCompat.getColor(context, R.color.attentionColor))
        titleV.setTextColor(ContextCompat.getColor(context, R.color.attentionColor))

    }

    if (type == TypeAlertCustom.ERROR) {
        icon.setImageResource(R.drawable.ic_attention);
        border.setStrokeColor(ContextCompat.getColor(v.context, R.color.errorColor))
        topImg.setBackgroundColor(ContextCompat.getColor(v.context, R.color.errorColor))
        titleV.setTextColor(ContextCompat.getColor(context, R.color.errorColor))

    }

    if (type == TypeAlertCustom.OK) {
        icon.setImageResource(R.drawable.ic_check_circle_correct);
        border.setStrokeColor(ContextCompat.getColor(context, R.color.colorGreenSuccess))
        topImg.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreenSuccess))
        titleV.setTextColor(ContextCompat.getColor(context, R.color.colorGreenSuccess))
    }

    v.visibility = View.VISIBLE;

    var timeAnime: Long = 200
    var delayRemove: Long = timeAnime * 2

    ViewPropertyAnimator.animate(v).setDuration(timeAnime)
        .y(0f)

    btnClose.setOnClickListener {
        run {
            ViewPropertyAnimator.animate(v).setDuration(timeAnime)
                .y(-800f)
        }

        Handler().postDelayed({


            ActivityExtensions.isCustomAlert = false;
            v.removeAllViews();
        }, delayRemove)

    }

    Handler().postDelayed({
        kotlin.run {
            ViewPropertyAnimator.animate(v).setDuration(timeAnime)
                .y(-800f)
        }

        Handler().postDelayed({
            ActivityExtensions.isCustomAlert = false;
            v.removeAllViews();

        }, delayRemove)

    }, timeSleep * 1000)

    addView(v, param);
}

/**
 * Show custom banner view with custom message to validation input fields.
 * @receiver AppCompatActivity context.
 * @param message REceive string message to show on layout.
 * @param viewY Y of view.
 */
fun AppCompatActivity.alertCustomTips(message: String, viewY: View) {

    var param: RelativeLayout.LayoutParams = RelativeLayout.LayoutParams(
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT
    );

    param.setMargins(0, 0, 0, 0)

    val v = RelativeLayout(this);
    v.layoutParams = param;

    val viewA = LayoutInflater.from(this).inflate(R.layout.dialog_text_alert_tools, null, false)
    val txt = viewA.findViewById<TextView>(R.id.txtMessageDialog)
    txt.setText(message);
    v.addView(viewA, param);

    v.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    val height = v.getMeasuredHeight() + 145;

    val mypopupWindow: PopupWindow

    mypopupWindow = PopupWindow(
        v,
        RelativeLayout.LayoutParams.MATCH_PARENT,
        RelativeLayout.LayoutParams.WRAP_CONTENT,
        true
    )
    mypopupWindow.showAsDropDown(viewY, 0, -height, Gravity.CENTER)

    viewA.setOnClickListener {

        mypopupWindow.dismiss();

    }


}

/**
 * Show dialog.
 * @receiver Context froma activity that is calling.
 * @param title Title of dialog.
 * @param message Message of dialog.
 */
fun AppCompatActivity.alertShowText(title: String, message: String) {

    val d = DialogText(this, title, message);
    d.show();
}

/**
 * Show "Copy Protocol" dialog.
 * @receiver Context froma activity that is calling.
 */
fun AppCompatActivity.alertShowExtractCopy(
    title1: String,
    title2: String,
    title3: String,
    data: String,
    code: String,
    message: String,
    isCopy: Boolean = true,
    type: TypeExtractDialogSheet = TypeExtractDialogSheet.NORMAL
) {

    val d = DialogProtocalCopy(this, title1, title2, title3, data, code, message, isCopy, type);
    d.show();

}

/**
 * Add list to dialog.
 * @receiver Context of activity.
 * @param title Title of dialog.
 * @param array Array to be added.
 * @param call Callback to indicate item click.
 */
fun AppCompatActivity.listMenu(title: String, array: ArrayList<String>, call: Dialogs.CALL) {
    Dialogs.addListA(this, title, array, call);
}

/**
 * List menu payments.
 * @receiver Context f activity.
 * @param call Callback to indicate item click.
 */
fun AppCompatActivity.listMenuPagment(call: Dialogs.CALL) {

    val list = ArrayList<String>()
    list.add("Reemitir cobrança no cartão 0992")
    list.add("Gerar boleto")
    Dialogs.addListA(this, null, list, call);

}

/**
 * Payment list card.
  * @param call Callback to indicate item click.
 */
fun AppCompatActivity.listMenuPagmentCard(call: Dialogs.CALL) {

    val list = ArrayList<String>()
    list.add("Reemitir cobrança no cartão 0992")
    list.add("Visualizar boleto")
    list.add("Copiar código de barras")
    //list.add("Simulação Developer pagamento sucesso")
    Dialogs.addListA(this, null, list, call);
}

/**
 * Payment list billet copy .
 * @param callP Callback to indicate item click.
 */
fun AppCompatActivity.listMenuPagmentBilletCopy(callP: Dialogs.CALL) {

    val list = ArrayList<String>()
    list.add("Visualizar boleto");
    list.add("Copiar código de barras");

    Dialogs.addListA(this, null, list, callP)

}

/**
 * Show success dialog and copy text string to clipboard.
 * @receiver Context of activity.
 * @param string Sting to be coppied.
 */
fun AppCompatActivity.copyString(string: String) {

    alertCustom(getString(R.string.copy_sucess), TypeAlertCustom.OK)
    val clipboard: ClipboardManager =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    clipboard.setPrimaryClip(ClipData.newPlainText(string, string));

}

/**
 * Open extract dialog sheet dialog
 * @receiver Context of actvity.
 */
fun AppCompatActivity.openExtratSheetDialog(
    type: TypeExtractDialogSheet,
    data: PaymentBasicaModel?
) {

    val d = ExtractDialogSheet.newInstance(0, type, data)
    d.show(Tab.INSTANCE.supportFragmentManager, "Gloria a Deus!")

}






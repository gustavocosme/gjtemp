package prudential.pobmobilecustomerappandroid.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.muddzdev.quickshot.QuickShot
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import gustavocosme.ui.BaseFragment
import kotlinx.android.synthetic.main.activity_extract_conprovant.*
import org.jetbrains.anko.textColor
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.alertCustom
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.model.PaymentBasicaModel
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.utils.Dialogs
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.TypeExtractDialogSheet
import java.io.File
import java.util.*

class ExtractModuleFragment : BaseFragment() {

    lateinit var txtNumberApolice: TextView;
    lateinit var txtNameApolice: TextView;
    lateinit var txtNameSegurado: TextView;
    lateinit var txtDateVeciment: TextView;
    lateinit var txtCardNumber: TextView;
    lateinit var txtCardNameCardPerson: TextView;
    lateinit var txtDataPagament: TextView;
    lateinit var txtTitle: TextView;
    lateinit var icon: ImageView;
    lateinit var iconCard: ImageView;
    lateinit var btnShared: Button;
    lateinit var btnSave: Button
    lateinit var containerShared: ConstraintLayout
    lateinit var container: ConstraintLayout
    lateinit var line: View;

    lateinit var data: PaymentBasicaModel;
    lateinit var type: TypeExtractDialogSheet;

    init {

        isLogo = false;
        title = "Comprovante";
        setLayout(R.layout.fragment_extract_conprovant);
    }

    override fun init() {

        initViews();
        initEvents();
        setInfo();
    }

    fun initViews()
    {
        txtNumberApolice = cachedView!!.findViewById(R.id.txtApolice) as TextView
        txtNameApolice = cachedView!!.findViewById(R.id.txtNameApolice) as TextView
        txtNameSegurado = cachedView!!.findViewById(R.id.txtNameSegurado) as TextView
        txtDateVeciment = cachedView!!.findViewById(R.id.txtDate) as TextView
        txtCardNumber = cachedView!!.findViewById(R.id.txtCard) as TextView
        btnSave = cachedView!!.findViewById(R.id.btn_download) as Button
        btnShared = cachedView!!.findViewById(R.id.btn_shared) as Button
        txtCardNameCardPerson = cachedView!!.findViewById(R.id.txtDatePagament2) as TextView
        txtDataPagament = cachedView!!.findViewById(R.id.txtDatePagament) as TextView
        txtTitle = cachedView!!.findViewById(R.id.txtTitle) as TextView
        icon = cachedView!!.findViewById(R.id.icon) as ImageView
        iconCard = cachedView!!.findViewById(R.id.icon_card) as ImageView
        containerShared = cachedView!!.findViewById(R.id.container) as ConstraintLayout
        container = cachedView!!.findViewById(R.id.containerG) as ConstraintLayout
        line = cachedView!!.findViewById(R.id.line) as View
        line.visibility = View.GONE;
    }

    fun initEvents()
    {
        btnSave.setOnClickListener { onClickSave(); }
        btnShared.setOnClickListener { onClickShared(); }
    }

    @SuppressLint("UseRequireInsteadOfGet")
    fun setInfo(){

        type = arguments!!.getSerializable("type") as TypeExtractDialogSheet
        //data = b!!.getSerializable("data") as PaymentBasicaModel

        if (type == TypeExtractDialogSheet.ASSISTENT) {
            iconCard.setImageResource(R.drawable.ic_extract_pagament_form)
            txtCardNumber.setText("Assistencia Financeira (SPL)");
            line.visibility = View.VISIBLE;
        }

        if (type == TypeExtractDialogSheet.ATENCION) {
            txtTitle.setText("Pagamento pendente");
            containerShared.visibility = View.GONE;
            txtDataPagament.setText("Não realizado")
            txtTitle.textColor = activity!!.getColor(R.color.attentionColor)
            icon.setImageResource(R.drawable.ic_extract_atencion_orange)
        }

        if (type == TypeExtractDialogSheet.FUTURE) {
            txtTitle.setText("Pagamento futuro");
            icon.setImageResource(R.drawable.ic_extract_time_future)
            (cachedView!!.findViewById(R.id.txt6) as TextView).showOrGone(false);
            (cachedView!!.findViewById(R.id.txt7) as TextView).showOrGone(false);
            (cachedView!!.findViewById(R.id.txtDatePagament) as TextView).showOrGone(false);
            (cachedView!!.findViewById(R.id.txtCardName) as TextView).showOrGone(false);
        }


        if (type == TypeExtractDialogSheet.NORMAL) {

        }

    }

    /**
     * Share extract with third apps.
     */
    fun onClickShared() {

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Texto");
        Tab.INSTANCE.startActivity(Intent.createChooser(shareIntent, ""))

    }

    /**
     * Download and save generated extract.
     */
    fun onClickSave() {


        val permissions = arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        Permissions.check(
            context,
            permissions,
            null,
            null,
            object : PermissionHandler() {

                override fun onGranted() {


                    containerShared.visibility = View.GONE;

                    var uniqueID = UUID.randomUUID().toString();

                    val direct = File(
                        Environment.getExternalStorageDirectory().toString() + "/Prudential"
                    )
                    if (!direct.exists()) {
                        val wallpaperDirectory = File("/sdcard/Prudential/")
                        wallpaperDirectory.mkdirs()
                    }
                    QuickShot.of(containerG)
                        .setResultListener(object : QuickShot.QuickShotListener {

                            override fun onQuickShotFailed(path: String?, errorMsg: String?) {

                                Log.e("ERRO", errorMsg!!);

                            }

                            override fun onQuickShotSuccess(path: String?) {

                                //container.alertCustom("Extrato baixado com sucesso!",TypeAlertCustom.OK);
                                Tab.INSTANCE!!.alertCustom("Extrato baixado com sucesso!",TypeAlertCustom.OK);

                            }

                        })
                        .enableLogging()
                        .setFilename(uniqueID)
                        .setPath(direct.absolutePath)
                        .toPNG()
                        .save();


                    containerShared.visibility = View.VISIBLE;

                }

                override fun onDenied(
                    contextq: Context?,
                    deniedPermissions: ArrayList<String>?
                ) {


                }


            })

    }

    companion object {
        fun newInstance(instance: Int,type:TypeExtractDialogSheet): ExtractModuleFragment {
            val args = Bundle()
            args.putInt(ARGS_INSTANCE, instance)
            args.putSerializable("type", type)
            val fragment = ExtractModuleFragment()
            fragment.setArguments(args)
            return fragment
        }
    }


}//END CLASS

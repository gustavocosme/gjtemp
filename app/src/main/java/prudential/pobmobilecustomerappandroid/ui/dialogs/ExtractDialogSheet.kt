package prudential.pobmobilecustomerappandroid.ui.dialogs

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.muddzdev.quickshot.QuickShot
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import gustavocosme.ui.BaseFragment
import org.jetbrains.anko.textColor
import prudential.pobmobilecustomerappandroid.R

import prudential.pobmobilecustomerappandroid.model.PaymentBasicaModel
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.utils.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

/**
 * Class build to Extract Dialog infos.
 */
class ExtractDialogSheet : BottomSheetDialogFragment() {


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
    lateinit var contentView:View
    lateinit var btnShared:Button;
    lateinit var btnSave:Button
    lateinit var containerShared:ConstraintLayout
    lateinit var container:ConstraintLayout
    lateinit var line: View;

    lateinit var data:PaymentBasicaModel;
    lateinit var type:TypeExtractDialogSheet;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Create custom view.
     */
    override fun setupDialog(dialog: Dialog, style: Int) {
        contentView = View.inflate(context, R.layout.dialog_extract_ok, null);

        dialog.setContentView(contentView)


        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog


            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            BottomSheetBehavior.from<FrameLayout?>(bottomSheet!!).state =
                BottomSheetBehavior.STATE_EXPANDED
        }

        inicialize();

    }

    /**
     * Inicialize components
     */
    fun inicialize()
    {
        txtNumberApolice = contentView.findViewById(R.id.txtApolice) as TextView
        txtNameApolice  = contentView.findViewById(R.id.txtNameApolice) as TextView
        txtNameSegurado  = contentView.findViewById(R.id.txtNameSegurado) as TextView
        txtDateVeciment  = contentView.findViewById(R.id.txtDate) as TextView
        txtCardNumber  = contentView.findViewById(R.id.txtCard) as TextView
        btnSave  = contentView.findViewById(R.id.btn_download) as Button
        btnShared  = contentView.findViewById(R.id.btn_shared) as Button
        txtCardNameCardPerson  = contentView.findViewById(R.id.txtDatePagament2) as TextView
        txtDataPagament  = contentView.findViewById(R.id.txtDatePagament) as TextView
        txtTitle  = contentView.findViewById(R.id.txtTitle) as TextView
        icon  = contentView.findViewById(R.id.icon) as ImageView
        iconCard  = contentView.findViewById(R.id.icon_card) as ImageView
        containerShared  = contentView.findViewById(R.id.container) as ConstraintLayout
        container  = contentView.findViewById(R.id.containerG) as ConstraintLayout
        line  = contentView.findViewById(R.id.line) as View
        line.visibility = View.GONE;

        val b = arguments;

        type = b!!.getSerializable("type") as TypeExtractDialogSheet
        //data = b!!.getSerializable("data") as PaymentBasicaModel

        if(type == TypeExtractDialogSheet.ASSISTENT)
        {
            iconCard.setImageResource(R.drawable.ic_extract_pagament_form)
            txtCardNumber.setText("Assistencia Financeira (SPL)");
            line.visibility = View.VISIBLE;
        }

        if(type == TypeExtractDialogSheet.ATENCION)
        {
            txtTitle.setText("Pagamento pendente");
            containerShared.visibility = View.GONE;
            txtDataPagament.setText("Não realizado")
            txtTitle.textColor = contentView.context.getColor(R.color.attentionColor)
            icon.setImageResource(R.drawable.ic_extract_atencion_orange)

        }

        if(type == TypeExtractDialogSheet.NORMAL)
        {

        }

        btnSave.setOnClickListener { onClickSave(); }
        btnShared.setOnClickListener { onClickShared(); }

    }

    /**
     * share text with third apps.
     */
    fun onClickShared(){

        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type="text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Texto");
        Tab.INSTANCE.startActivity(Intent.createChooser(shareIntent,""))

    }

    /**
     * Donwload and save image.
     */
    fun onClickSave(){
        val permissions = arrayOf(
         Manifest.permission.WRITE_EXTERNAL_STORAGE,
         Manifest.permission.READ_EXTERNAL_STORAGE
        )

        Permissions.check(
            context ,
            permissions,
            null ,
            null,
            object : PermissionHandler() {

                override fun onGranted() {


                    containerShared.visibility = View.GONE;
                    val v = contentView;
                    v.setBackgroundColor(resources.getColor(R.color.colorWhite));

                    //val b = contentView.getViewBitmap(v)!!;
                    //ShareSocialG10.SHARE_SAVE(b, SOCIAL_TYPES.GLOBAL,context!!);
                    var uniqueID = UUID.randomUUID().toString();

                    val direct = File(
                        Environment.getExternalStorageDirectory().toString() + "/Prudential"
                    )
                    if (!direct.exists()) {
                        val wallpaperDirectory = File("/sdcard/Prudential/")
                        wallpaperDirectory.mkdirs()
                    }
                    QuickShot.of(v).setResultListener(object:QuickShot.QuickShotListener{

                        override fun onQuickShotFailed(path: String?, errorMsg: String?) {

                            Log.e("ERRO",errorMsg!!);

                        }

                        override fun onQuickShotSuccess(path: String?) {

                            Dialogs.add(Tab.INSTANCE,"Extrato baixado com sucesso!");

                        }

                    } )
                        .enableLogging()
                        .setFilename(uniqueID)
                        .setPath(direct.absolutePath)
                        .toPNG()
                        .save();


                    v.setBackgroundColor(Color.TRANSPARENT);

                    containerShared.visibility = View.VISIBLE;

                }

                override fun onDenied(
                    contextq: Context?,
                    deniedPermissions: ArrayList<String>?
                ) {



                }


            })

    }


    /**
     * Inicialize fragment static
     */
    companion object {

        fun newInstance(
            instance: Int,
            type:TypeExtractDialogSheet,
            data: PaymentBasicaModel?
        ): ExtractDialogSheet {

            val args = Bundle()
            args.putInt(BaseFragment.ARGS_INSTANCE, instance)
            args.putSerializable("type",type);
            args.putSerializable("data",data);

            val fragment =
                ExtractDialogSheet()
            fragment.setArguments(args)
            return fragment
        }

    }



}//END CLASS
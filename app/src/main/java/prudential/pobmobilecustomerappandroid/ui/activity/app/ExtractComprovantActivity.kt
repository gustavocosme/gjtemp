package prudential.pobmobilecustomerappandroid.ui.activity.app

import android.Manifest
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
import kotlinx.android.synthetic.main.activity_extract_conprovant.*
import org.jetbrains.anko.textColor
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.model.PaymentBasicaModel
import prudential.pobmobilecustomerappandroid.utils.Dialogs
import prudential.pobmobilecustomerappandroid.utils.TypeExtractDialogSheet
import prudential.pobmobilecustomerappandroid.utils.navigation.Ac
import java.io.File
import java.util.*

/**
 * Class to hendle comprovant extract.
 */
class ExtractComprovantActivity : Ac() {

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

    /**
     * Call this method to create view.
     * @param savedInstanceState State of activity.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_extract_conprovant)

        initToolbar("Comprovante");

        inicialize();

    }

    /**
     * Inicialize views from layout.
     */
    fun inicialize() {
        txtNumberApolice = findViewById(R.id.txtApolice) as TextView
        txtNameApolice = findViewById(R.id.txtNameApolice) as TextView
        txtNameSegurado = findViewById(R.id.txtNameSegurado) as TextView
        txtDateVeciment = findViewById(R.id.txtDate) as TextView
        txtCardNumber = findViewById(R.id.txtCard) as TextView
        btnSave = findViewById(R.id.btn_download) as Button
        btnShared = findViewById(R.id.btn_shared) as Button
        txtCardNameCardPerson = findViewById(R.id.txtDatePagament2) as TextView
        txtDataPagament = findViewById(R.id.txtDatePagament) as TextView
        txtTitle = findViewById(R.id.txtTitle) as TextView
        icon = findViewById(R.id.icon) as ImageView
        iconCard = findViewById(R.id.icon_card) as ImageView
        containerShared = findViewById(R.id.container) as ConstraintLayout
        container = findViewById(R.id.containerG) as ConstraintLayout
        line = findViewById(R.id.line) as View
        line.visibility = View.GONE;

        type = intent.getSerializableExtra("type") as TypeExtractDialogSheet
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
            txtTitle.textColor = getColor(R.color.attentionColor)
            icon.setImageResource(R.drawable.ic_extract_atencion_orange)
        }

        if (type == TypeExtractDialogSheet.FUTURE) {
            txtTitle.setText("Pagamento futuro");
            icon.setImageResource(R.drawable.ic_extract_time_future)

            txt6.showOrGone(false);
            txt7.showOrGone(false);
            txtDatePagament.showOrGone(false);
            txtCardName.showOrGone(false);
        }


        if (type == TypeExtractDialogSheet.NORMAL) {

        }

        btnSave.setOnClickListener { onClickSave(); }
        btnShared.setOnClickListener { onClickShared(); }

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
            this,
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

                                Dialogs.add(
                                    this@ExtractComprovantActivity,
                                    "Extrato baixado com sucesso!"
                                );

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


}

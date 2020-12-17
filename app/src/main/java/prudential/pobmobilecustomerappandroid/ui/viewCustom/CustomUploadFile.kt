package prudential.pobmobilecustomerappandroid.ui.viewCustom

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.jetbrains.anko.alert
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.drawUnderLine
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.interfaces.OnUploadFile
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.adapter.SimpleSelectAdapter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Custom component to handle upload file.
 * This component has a custom layout and offer methods to who is calling to customize it.
 * It offer custom properties on layout, to show or not some fields.
 * @param context Context of who is calling.
 * @param attributeSet Attributes from layout.
 */
class CustomUploadFile(context: Context, val attributeSet: AttributeSet) :
    MaterialCardView(context, attributeSet), OnUploadFile, OnRecyclerClickListener<String> {

    private lateinit var layout: View
    private lateinit var justificationContainer: ConstraintLayout
    private lateinit var uploadFileButton: Button
    private lateinit var closeFileButton: ImageView
    private lateinit var documentName: MaterialTextView
    private lateinit var documentGroup: Group

    private lateinit var justificationInput: TextInputEditText

    private lateinit var justificationRecyclerView: RecyclerView
    private lateinit var justificationInclude: View

    private lateinit var documentAttached: Uri

    private val justificationOptionAdapter by lazy { SimpleSelectAdapter(this) }

    /**
     * Default init methods.
     */
    init {
        inflateLayout()
        inflateViews()
        getCustomAttrs()
        setOnUploadFileButtonClick()
        initJstificationOptionSelecAdapter()
        justificationInputClick()
        closeFileButtonClick()
    }

    /**
     * Inflate custom layout.
     */
    private fun inflateLayout() {
        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layout = mInflater.inflate(R.layout.custom_upload_file, this, true)
    }

    /**
     * Inflate views.
     */
    private fun inflateViews() {
        justificationContainer = layout.findViewById(R.id.custom_upload_file_justification)
        uploadFileButton = layout.findViewById(R.id.custom_upload_file_button)
        documentName = layout.findViewById(R.id.custom_upload_file_document_name)
        documentGroup = layout.findViewById(R.id.custom_upload_file_document_group)
        justificationInclude = layout.findViewById(R.id.custom_upload_file_justification_include)
        justificationInput = layout.findViewById(R.id.custom_upload_file_justify_input)
        closeFileButton = layout.findViewById(R.id.custom_upload_file_close_button)
    }

    /**
     * Handle justification input text click if visible.
     */
    private fun justificationInputClick() {
        justificationInput.setOnClickListener {
            justificationInclude.showOrGone(!justificationInclude.isVisible)
        }
    }

    private fun closeFileButtonClick() {
        closeFileButton.setOnClickListener {
            documentName.text = ""
            documentGroup.showOrGone(false)
        }
    }

    /**
     * Init justification options recycler view and adpter.
     */
    private fun initJstificationOptionSelecAdapter() {
        justificationRecyclerView =
            justificationInclude.findViewById(R.id.dropdown_list_select_recycler)
        justificationRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = justificationOptionAdapter
        }
        justificationOptionAdapter.submitList(
            mutableListOf(
                "Alteração de nome",
                "Alteração estado civíl"
            )
        )
    }

    /**
     * Handle upload file button click.
     */
    private fun setOnUploadFileButtonClick() {
        uploadFileButton.setOnClickListener {
            context.alert {
                items(listOf("Câmera", "Arquivos", "Cancelar")) { dialog, index ->
                    when (index) {
                        0 -> dispatchTakePictureIntent()
                        1 -> openFileChoose()
                    }
                }
            }.show()
        }
    }

    /**
     * Check permissions and take picture of native camera.
     * Ask permision if not authorized.
     */
    private fun dispatchTakePictureIntent() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(context.packageManager)?.also {
                    val photoFile: File? = createImageFile()
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            context,
                            "prudential.pobmobilecustomerappandroid",
                            it
                        )
                        Tab.INSTANCE.apply {
                            setUploadFileListener(this@CustomUploadFile)
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            startActivityForResult(takePictureIntent, Codes.OPEN_CAMERA)
                        }
                    }
                }

            }
        } else {
            ActivityCompat.requestPermissions(
                Tab.INSTANCE,
                arrayOf(Manifest.permission.CAMERA),
                Codes.OPEN_CAMERA
            )
        }
    }

    /**
     * Create file to insert toke image on it.
     * Use current timestamp to create custom and unique title.
     * @return File created.
     */
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "DOC_${timeStamp}",
            ".jpg",
            storageDir
        )
    }


    /**
     * Open model to choose file.
     */
    private fun openFileChoose() {
        val fileIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/jpg"
            type = "application/pdf"
        }
        Tab.INSTANCE.apply {
            setUploadFileListener(this@CustomUploadFile)
            startActivityForResult(fileIntent, Codes.OPEN_FILE)
        }
    }

    /**
     * Get custom attrs from layout.
     */
    private fun getCustomAttrs() {
        context.obtainStyledAttributes(attributeSet, R.styleable.CustomUploadFile).apply {
            try {
                showJustification(
                    getBoolean(
                        R.styleable.CustomUploadFile_showJustification,
                        false
                    )
                )

            } finally {
                recycle()
            }
        }
    }

    /**
     * Handle justification input field visibility.
     * @param show True to show, false to not show justification.
     */
    fun showJustification(show: Boolean) {
        justificationContainer.showOrGone(show)
    }

    /**
     * Implementation to handle file choosed.
     * @param file File choosed by user.
     * @param requestCode RequestCode sent by user on requisition.
     */
    override fun fileChoosedToUpload(file: Uri?, requestCode: Int) {
        if (file != null) {
            documentAttached = file
            val fileName = when (file.scheme) {
                ContentResolver.SCHEME_FILE -> getSchemeFileName(file)
                ContentResolver.SCHEME_CONTENT -> getSchemeContentFileName(file)
                else -> null
            }
            fileName?.let {
                setFileName(it)
            }
        } else {
            setFileName(createImageFile().name)
        }
    }

    /**
     * Get file name, if file is content.
     * @param file File data.
     * @return Title of file.
     */
    private fun getSchemeContentFileName(file: Uri) = context.contentResolver.query(
        file,
        null,
        null,
        null,
        null
    )?.let { cursor ->
        cursor.run {
            if (moveToFirst()) {
                getString(getColumnIndex(OpenableColumns.DISPLAY_NAME))
            } else {
                null
            }
        }.also { cursor.close() }
    }

    /**
     * Get file name, if file is path.
     * @param file File data.
     * @return Title of file.
     */
    private fun getSchemeFileName(file: Uri) = File(file.path).name

    private fun setFileName(it: String) {
        documentGroup.showOrGone(true)
        documentName.drawUnderLine()
        documentName.text = it
    }


    /**
     * Handle justification item click.
     * @param model Item clicked.
     * @param position Position of item clicked.
     */
    override fun onRecyclerItemClick(model: String, position: Int) {
        justificationInput.setText(model)
        justificationInclude.showOrGone(false)
    }

    /**
     * Object to handle request codes.
     */
    object Codes {
        const val OPEN_FILE = 0
        const val OPEN_CAMERA = 1
    }


}
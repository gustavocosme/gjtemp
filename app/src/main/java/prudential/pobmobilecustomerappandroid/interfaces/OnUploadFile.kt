package prudential.pobmobilecustomerappandroid.interfaces

import android.net.Uri

interface OnUploadFile {

    fun fileChoosedToUpload(file: Uri?, requestCode: Int)

}
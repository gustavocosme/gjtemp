package prudential.pobmobilecustomerappandroid.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.util.Log
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.utils.model.AsyncTaskCustom
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


enum class SOCIAL_TYPES(val value: Int) {

    GLOBAL(1),
    INSTAGRAM(2),
    ZAP(3)


}

object ShareSocialG10 {


    fun SHARE_SAVE(image: Bitmap, type: SOCIAL_TYPES,context: Context) {





            val builder = VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())

            try {


                val a = AsyncTaskCustom()
                a.call = object:AsyncTaskCustom.Call{

                    override fun onComplete(result: Any?) {

                        Dialogs.add(Tab.INSTANCE,"Extrato salvo com sucesso!");

                    }

                    override fun onRun(): Any {

                        var uniqueID = UUID.randomUUID().toString();
                        return  createDirectoryAndSaveFile(image,uniqueID+".jpg");

                    }

                }

                a.execute();




            }catch (e:Exception)
            {
                Log.e("ERRO", "IOException while trying to write file for sharing: " + e.message)

            }

        /*

            var uri: Uri? = null


            try {

                var uniqueID = UUID.randomUUID().toString();

                val root = Environment.getExternalStorageDirectory()
                val file = File(root.absolutePath + "/DCIM/Camera/$uniqueID.png")

                val stream = FileOutputStream(file)
                image.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.close()

                uri = Uri.fromFile(file);
                Log.e("SALVO COM SUCESSO", "SALVO COM SUCESSO")

                /*

                if (type == SOCIAL_TYPES.GLOBAL) {


                    SHARE_GLOBAL(uri,context);
                }

                if (type == SOCIAL_TYPES.INSTAGRAM) {
                    SHARE_INSTAGRAM_FEED(uri,context);
                }

                if (type == SOCIAL_TYPES.ZAP) {
                    SHARE_ZAP(uri,context);
                }

                 */


            } catch (e: IOException) {


                Log.e("ERRO", "IOException while trying to write file for sharing: " + e.message)

            }


         */


    }

    private fun createDirectoryAndSaveFile(
        imageToSave: Bitmap,
        fileName: String
    ):Boolean {
        val direct = File(
            Environment.getExternalStorageDirectory().toString() + "/Prudential"
        )
        if (!direct.exists()) {
            val wallpaperDirectory = File("/sdcard/Prudential/")
            wallpaperDirectory.mkdirs()
        }
        val file = File("/sdcard/Prudential/", fileName)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 60, out)
            out.flush()
            out.close()
            return true;
        } catch (e: Exception) {
            return false;
            e.printStackTrace()
        }
    }


    private fun SHARE_GLOBAL(uri: Uri,context:Context) {

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/png"
        context.startActivity(intent)

    }

    private fun SHARE_INSTAGRAM_FEED(uri: Uri,context:Context) {

        val share = Intent(Intent.ACTION_SEND)
        share.setPackage("com.instagram.android")
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        share.type = "image/*"
        share.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(share, ""))
    }

    private fun SHARE_ZAP(uri: Uri,context:Context) {

        val share = Intent(Intent.ACTION_SEND)
        share.setPackage("com.whatsapp")
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        share.type = "image/jpeg"
        share.putExtra(Intent.EXTRA_TEXT, "");

        share.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(Intent.createChooser(share, ""))
    }


}
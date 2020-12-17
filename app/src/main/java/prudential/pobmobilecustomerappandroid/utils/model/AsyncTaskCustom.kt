package prudential.pobmobilecustomerappandroid.utils.model

import android.os.AsyncTask

class AsyncTaskCustom() : AsyncTask<Void?, Void?, Any>() {

    var call: Call? = null
    var resp: Any? = null

    override fun onPostExecute(result: Any) {
        call!!.onComplete(result)
    }

    interface Call {
        fun onRun(): Any
        fun onComplete(result: Any?)
    }

    override fun doInBackground(vararg params: Void?): Any? {
        return call?.onRun()
    }
}
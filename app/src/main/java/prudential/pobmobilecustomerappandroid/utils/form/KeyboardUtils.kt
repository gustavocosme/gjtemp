package prudential.pobmobilecustomerappandroid.utils.form

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.graphics.Rect;
import android.os.Build;
import android.view.ViewTreeObserver;


object Keyboard {
    fun removeThis(ac: Activity) {
        try {
            val view = ac.currentFocus
            if (view != null) {
                val imm =
                    ac.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }

    fun hideSoftKeyboard(context: Context, i: IBinder?) {
        try {
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(i, 0)
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }

    fun showSoftKeyboard(context: Context, i: IBinder?) {
        try {
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInputFromWindow(i, 0, 0)
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }

    fun showSoftKeyboard(context: Activity, view: View) {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            view.requestFocus()
            inputMethodManager.showSoftInput(view, 0)
        } catch (e: Exception) {
            // TODO: handle exception
        }
    }
} //END CLAS

package prudential.pobmobilecustomerappandroid.extensions

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.ImageView

fun ImageView.removeSaturation() {


    //val matrix = ColorMatrix()
    //matrix.setSaturation(0f)
    //val filter = ColorMatrixColorFilter(matrix)
    //drawable.colorFilter = filter
    alpha = 0.3f;
}

fun ImageView.addSaturation() {

    //drawable.colorFilter = null;
    alpha = 1.0f;
}
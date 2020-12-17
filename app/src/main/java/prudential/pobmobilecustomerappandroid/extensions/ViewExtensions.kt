package prudential.pobmobilecustomerappandroid.extensions

import android.graphics.Bitmap
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.Transformation
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.nineoldandroids.view.ViewHelper
import com.nineoldandroids.view.ViewPropertyAnimator

/**
 * Draw underline in given text.
 * @receiver TextView to draw underline.
 */
fun TextView.drawUnderLine() {
    val text = SpannableString(this.text.toString())
    text.setSpan(UnderlineSpan(), 0, text.length, 0)
    paintFlags = getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG
    this.text = text
}

/**
 * Perform animation with fade alpha 0.0 to 1.0 on a current View in a certain time.
 * @param time Time animation will be performed
 * @receiver view to be animated.
 */
fun View.showAnime(time: Long = 300) {
    visibility = View.VISIBLE;
    ViewHelper.setAlpha(this, 0f)
    ViewPropertyAnimator.animate(this).setDuration(time)
        .alpha(1.0f);

}

fun View.showAnimeDelay(time: Long = 300) {

    ViewHelper.setAlpha(this, 0f)

    Looper.myLooper()?.let {
        Handler(it).postDelayed({
            ViewPropertyAnimator.animate(this).setDuration(time)
                .alpha(1.0f);
        }, 500);
    }



}


/**
 * Perform animation with fade alpha Toggle
 * @param time Time animation will be performed
 * @receiver view to be animated.
 */
fun View.showAnimeToggle(time: Long = 500) {

    ViewPropertyAnimator.animate(this).setDuration(0)
        .alpha(0.0f)

    Looper.myLooper()?.let {
        Handler(it).postDelayed({
            ViewPropertyAnimator.animate(this).setDuration(time)
                .alpha(1.0f);
        }, 200);
    }

}



/**
 * Perform animation with fade Rotation Toggle
 * @param time Time animation will be performed
 * @receiver view to be animated.
 */
fun View.showAnimeRotationToggle(time: Long = 200) {

    ViewPropertyAnimator.animate(this).setDuration(time).rotation(rotation+180)

}

fun View.animeExpand() {

    //showAnime()
    val matchParentMeasureSpec = View.MeasureSpec.makeMeasureSpec(
        (parent as View).width,
        View.MeasureSpec.EXACTLY
    )
    val wrapContentMeasureSpec =
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = measuredHeight

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    layoutParams.height = 1
    visibility = View.VISIBLE
    val a: Animation = object : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation?
        ) {
            layoutParams.height =
                if (interpolatedTime == 1f) LinearLayout.LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
            requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Expansion speed of 1dp/ms
    a.interpolator = LinearInterpolator()
    a.setDuration(70)
    startAnimation(a)
}


fun View.animeCollapse() {

    //hideAnime {  }
    val initialHeight = measuredHeight
    val a: Animation = object : Animation() {
        override fun applyTransformation(
            interpolatedTime: Float,
            t: Transformation
        ) {
            if (interpolatedTime == 1f) {
                visibility = View.GONE
            } else {
                layoutParams.height =
                    initialHeight - (initialHeight * interpolatedTime).toInt()
                requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    // Collapse speed of 1dp/ms
    a.interpolator =  LinearInterpolator()
    a.setDuration(70)
    startAnimation(a)
}


fun View.showAnimeRotation(r:Float) {

    ViewPropertyAnimator.animate(this).setDuration(200).rotation(r)

}


/**
 * Hide animation with delay on a current view.
 * @param onComplete Callback is called after hide animation finish.
 * @receiver view to be animated.
 */
fun View.hideAnime(onComplete: () -> Any) {
    ViewPropertyAnimator.animate(this).setDuration(300)
        .alpha(0.0f)

    Looper.myLooper()?.let {
        Handler(it).postDelayed({
            onComplete();
        }, 600);
    }
}

/**
 * Set current [View] visibility to true if no parameter informed.
 * @param show should be considered to performe view visibility.
 * @receiver Any view to perform visibility change.
 */
fun View.showOrGone(show: Boolean = true) {
    visibility = if (show) View.VISIBLE else View.GONE
}

/**
 * Adjust height of button, if system font is setted to large or text has 2 or more lines.
 * @receiver Button from layout to perform action.
 */
fun Button.adjustHeight() {
    val fontScale = resources.configuration.fontScale
    post {
        if (fontScale > 1 || lineCount > 1) {
            layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        }
    }
}


/**
 * Adjust height of button, if system font is setted to large or text has 2 or more lines.
 * @receiver AppCompatCheckBox from layout to perform action.
 */
fun AppCompatCheckBox.adjustHeight() {
    val fontScale = resources.configuration.fontScale
    post {
        if (fontScale > 1 || lineCount > 1) {
            layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
        }
    }
}

/**
 * Draw the view into a bitmap.
 */
fun View.getViewBitmap(v: View): Bitmap? {
    v.clearFocus()
    v.isPressed = false
    val willNotCache = v.willNotCacheDrawing()
    v.setWillNotCacheDrawing(false)

    // Reset the drawing cache background color to fully transparent
    // for the duration of this operation
    val color = v.drawingCacheBackgroundColor
    v.drawingCacheBackgroundColor = 0
    if (color != 0) {
        v.destroyDrawingCache()
    }
    v.buildDrawingCache()
    val cacheBitmap = v.drawingCache
    if (cacheBitmap == null) {
        return null
    }

    val bitmap = Bitmap.createBitmap(cacheBitmap)

    // Restore the view
    v.destroyDrawingCache()
    v.setWillNotCacheDrawing(willNotCache)
    v.drawingCacheBackgroundColor = color
    return bitmap
}

/**
 * Change text fields focus to simulate navigation between components.
 * @receiver Current focused input field.
 * @param nextInputEditText Next input text to become focused.
 * @param backtInputEditText Preview input field to became focused if user clear current input text.
 * @param length Length of characters of each input field to jump to the next input field.
 */
fun TextInputEditText.nextToFocus(
    nextInputEditText: TextInputEditText?,
    backtInputEditText: TextInputEditText?,
    length: Int = 1
) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            if (!s.isNullOrBlank() && s.length == length) {
                clearFocus()
                nextInputEditText?.requestFocus()
            } else if (s?.length == 0) {
                clearFocus()
                backtInputEditText?.requestFocus()
            }
        }
    })
}

/**
 * Check variable and set opaccity according to the variable.
 * @receiver View to be changed.
 * @param hasOpaccity True to set helf opaccity, false to remove.
 */
fun View.isEnabledToClick(isEnable: Boolean) {
    alpha = if (!isEnable) 0.5F else 1.0F
    isClickable = isEnable
    isSelected = isEnable
}
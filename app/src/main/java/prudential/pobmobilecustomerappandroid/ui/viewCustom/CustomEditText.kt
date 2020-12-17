package prudential.pobmobilecustomerappandroid.ui.viewCustom

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getDrawableOrThrow
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.backgroundDrawable
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.showOrGone

/**
 * Custom edit text with a custom view on start of component.
 * Customa attrs set on layout.
 * @param context Context of who is calling.
 * @param attributeSet Attributes from layout.
 */
class CustomEditText(context: Context, val attributes: AttributeSet) :
    FrameLayout(context, attributes) {

    private lateinit var layout: View
    private lateinit var textWithBackground: MaterialTextView
    private lateinit var firstView: ConstraintLayout
    private lateinit var inputText: TextInputEditText
    private lateinit var iconTextView: ImageView
    private var viewText: String? = null
    private var viewWidth: Float = 0F
    private var viewHeight: Float = 0F
    private var viewBackgroundColor: Int = 0
    private var viewIcon: Drawable? = null

    /**
     * Default init method.
     */
    init {
        inflateLayout()
        inflateViews()
        getCustomAttrs()
    }

    /**
     * Inflate custom layout.
     */
    private fun inflateLayout() {
        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layout = mInflater.inflate(R.layout.custom_edit_text, this, true)
    }

    /**
     * Inflate custom views.
     */
    private fun inflateViews() {
        textWithBackground = layout.findViewById(R.id.custom_edit_text_view_text)
        firstView = layout.findViewById(R.id.custom_edit_text_view)
        inputText = layout.findViewById(R.id.custom_edit_text_input)
        iconTextView = layout.findViewById(R.id.custom_edit_text_icon)
    }

    /**
     * Get custom attrs from layout.
     */
    private fun getCustomAttrs() {
        context.obtainStyledAttributes(attributes, R.styleable.CustomEditText).apply {
            try {
                viewWidth = getDimension(R.styleable.CustomEditText_viewWidth, 0f)
                viewHeight = getDimension(R.styleable.CustomEditText_viewHeight, 0f)
                viewText = getString(R.styleable.CustomEditText_viewText)
                viewBackgroundColor = getColor(
                    R.styleable.CustomEditText_viewBackgroundColor,
                    context.resources.getColor(R.color.colorGreyBackInputText, context.theme)
                )
                viewIcon =
                    getDrawable(R.styleable.CustomEditText_viewIcon)


                setTextWidthAndHeigth(viewWidth.toInt(), viewHeight.toInt())
                setTextToView(viewText ?: "")
                setCustomIcon(viewIcon)
                setViewBackgroundColor(viewBackgroundColor)

            } finally {
                recycle()
            }
        }
    }

    /**
     * Set custom icon to start view.
     * @param viewIcon Custom drawable.
     */
    private fun setCustomIcon(viewIcon: Drawable?) {
        iconTextView.showOrGone(viewIcon != null)
        iconTextView.backgroundDrawable = viewIcon
    }

    /**
     * Set view size.
     * @param customWidht Width of view.
     * @param customHeight Height of view.
     */
    private fun setTextWidthAndHeigth(customWidht: Int, customHeight: Int) {
        val params = firstView.layoutParams.apply {
            width = customWidht
            height = customHeight
        }
        firstView.layoutParams = params
    }

    /**
     * Set custom text to the view.
     * @param text String to set on textView.
     */
    fun setTextToView(text: String) {
        textWithBackground.setText(text)
    }

    /**
     * Set View background color.
     * Default os gray.
     * @param color Resource of color.
     */
    fun setViewBackgroundColor(color: Int) {
        firstView.backgroundColor = color
    }

    /**
     * Set text to the input field layout.
     */
    var text: String = inputText.text.toString()
        set(value) {
            inputText.setText(value)
        }

}

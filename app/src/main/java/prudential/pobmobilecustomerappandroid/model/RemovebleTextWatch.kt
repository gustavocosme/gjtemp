package prudential.pobmobilecustomerappandroid.model

import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

/**
 * Data class to handle textwatch listener.
 */
data class RemovebleTextWatch(
    val inputEditText: TextInputEditText,
    val textWatcher: TextWatcher
)

package prudential.pobmobilecustomerappandroid.utils.form.currency

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*


class MoneyTextWatcher : TextWatcher {
    private val editTextWeakReference: WeakReference<EditText?>?
    private val locale: Locale?

    lateinit var call:(value:String)->Any
    constructor(editText: EditText?, locale: Locale?, call:(value:String)->Any) {
        editTextWeakReference = WeakReference<EditText?>(editText)
        this.locale = if (locale != null) locale else Locale.getDefault()
        this.call = call;
    }

    constructor(editText: EditText?) {
        editTextWeakReference = WeakReference<EditText?>(editText)
        locale = Locale.getDefault()
    }

    override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {

        val editText: EditText = editTextWeakReference?.get()!!
        editText.removeTextChangedListener(this)
        val parsed: BigDecimal? = parseToBigDecimal(editText.text.toString(), locale)
        val formatted: String =
            NumberFormat.getCurrencyInstance(locale).format(parsed)
        // NumberFormat.getNumberInstance(locale).format(parsed); // sem o simbolo de moeda
        editText.setText(formatted)
        call(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)

    }

    override fun afterTextChanged(editable: Editable?) {

    }

    private fun parseToBigDecimal(value: String?, locale: Locale?): BigDecimal? {
        val replaceable = String.format(
            "[%s,.\\s]",
            NumberFormat.getCurrencyInstance(locale).currency.symbol
        )
        val cleanString = value!!.replace(replaceable.toRegex(), "")
        return BigDecimal(cleanString).setScale(
            2, BigDecimal.ROUND_FLOOR
        ).divide(
            BigDecimal(100), BigDecimal.ROUND_FLOOR
        )
    }
}

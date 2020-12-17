package prudential.pobmobilecustomerappandroid.utils.form.currency

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import com.google.android.material.textfield.TextInputEditText
import prudential.pobmobilecustomerappandroid.R
import java.util.*


/**
 * Class build to Menu Slide APP.
 */
class CurrencyEditTextG(txtP: TextInputEditText, var call:(value:String)->Any) {

    lateinit var cachedView: View;
    lateinit var txt: TextInputEditText

    private var currentLocale: Locale? = null

    private var defaultLocale = Locale.US

    private var allowNegativeValues = false

    private var rawValue = 0L

    private var textWatcher: CurrencyTextWatcher? = null
    private var hintCache: String? = null

    private var decimalDigits = 0



    init {


        txt = txtP;

        setLocale(Locale("pt","BR"));
        init();
        processAttributes();

    }



    /**
     * Enable the user to input negative values
     */
    fun setAllowNegativeValues(negativeValuesAllowed: Boolean) {
        allowNegativeValues = negativeValuesAllowed
    }

    /**
     * Returns whether or not negative values have been allowed for this CurrencyEditText field
     */
    fun areNegativeValuesAllowed(): Boolean {
        return allowNegativeValues
    }

    /**
     * Retrieve the raw value that was input by the user in their currencies lowest denomination (e.g. pennies).
     *
     * IMPORTANT: Remember that the location of the decimal varies by currentCurrency/Locale. This method
     * returns the raw given value, and does not account for locality of the user. It is up to the
     * calling application to handle that level of conversion.
     * For example, if the text of the field is $13.37, this method will return a long with a
     * value of 1337, as penny is the lowest denomination for USD. It will be up to the calling
     * application to know that it needs to handle this value as pennies and not some other denomination.
     *
     * @return The raw value that was input by the user, in the lowest denomination of that users
     * deviceLocale.
     */
    fun getRawValue(): Long {
        return rawValue
    }

    /**
     * Sets the value to be formatted and displayed in the CurrencyEditText view.
     *
     * @param value - The value to be converted, represented in the target currencies lowest denomination (e.g. pennies).
     */
    fun setValue(value: Long) {
        val formattedText = format(value)
        txt.setText(formattedText)
    }

    /**
     * The current locale used by this instance of CurrencyEditText. By default, will be the users
     * device locale unless that locale is not ISO 3166 compliant, in which case the defaultLocale will
     * be used.
     *
     * @return the Locale object for the given users configuration
     */
    fun getLocale(): Locale? {
        return currentLocale
    }

    /**
     * Override the locale used by CurrencyEditText (which is the users device locale by default).
     *
     * Will also update the hint text if a custom hint was not provided.
     *
     * IMPORTANT - This method does NOT update the currently set Currency object used by
     * this CurrencyEditText instance. If your use case dictates that Currency and Locale
     * should never break from their default pairing, use 'configureViewForLocale(locale)' instead
     * of this method.
     * @param locale The deviceLocale to set the CurrencyEditText box to adhere to.
     */
    fun setLocale(locale: Locale) {
        currentLocale = locale
        refreshView()
    }

    /**
     * Convenience method to get the current Hint back as a string rather than a CharSequence
     */
    fun getHintString(): String? {
        val result: CharSequence = txt.getHint() ?: return null
        return txt.getHint().toString()
    }

    /**
     * Returns the number of decimal digits this CurrencyEditText instance is currently configured
     * to use. This value will be based on the current Currency object unless the value
     * was overwritten by setDecimalDigits().
     */
    fun getDecimalDigits(): Int {
        return decimalDigits
    }

    /**
     * Sets the number of decimal digits the currencyTextFormatter will use, overriding
     * the number of digits specified by the current currency. Note, however,
     * that calls to setCurrency() and configureViewForLocale() will override this value.
     *
     * Note that this method will also invoke the formatter to update the current view if the current
     * value is not null/empty.
     *
     * @param digits The number of digits to be shown following the decimal in the formatted text.
     * Value must be between 0 and 340 (inclusive).
     * @throws IllegalArgumentException If provided value does not fall within the range (0, 340) inclusive.
     */
    fun setDecimalDigits(digits: Int) {
        require(!(digits < 0 || digits > 340)) { "Decimal Digit value must be between 0 and 340" }
        decimalDigits = digits
        refreshView()
    }

    /**
     * Sets up the CurrencyEditText view to be configured for a given locale, using that
     * locales default currency (so long as the locale is ISO-3166 compliant). If there is
     * an issue retrieving the locales currency, the defaultLocale field will be used.
     *
     * This is the most 'fool proof' way of configuring a CurrencyEditText view when not
     * relying on the default implementation, and is the recommended approach for handling
     * locale/currency setup if you choose not to rely on the default behavior.
     *
     * Note that this method will set the decimalDigits field, potentially overriding
     * values from previous setDecimalDigits calls.
     */
    fun configureViewForLocale(locale: Locale?) {
        currentLocale = locale
        val currentCurrency = getCurrencyForLocale(locale)
        decimalDigits = currentCurrency.defaultFractionDigits
        refreshView()
    }

    /**
     * Override the locale to be used in the event that the users device locale is not ISO 3166 compliant.
     * Defaults to Locale.US.
     * NOTE: Be absolutely sure that this value is supported by ISO 3166. See
     * Java.util.Locale.getISOCountries() for a list of currently supported ISO 3166 locales (note that this list
     * may not be identical on all devices)
     * @param locale The fallback locale used to recover gracefully in the event of the current locale value failing.
     */
    fun setDefaultLocale(locale: Locale?) {
        defaultLocale = locale
    }

    /**
     * The currently held default Locale to fall back on in the event of a failure with the Locale field (typically
     * due to the locale being set to a non-standards-compliant value.
     */
    fun getDefaultLocale(): Locale? {
        return defaultLocale
    }

    /**
     * Pass in a value to have it formatted using the same rules used during data entry.
     * @param val A string which represents the value you'd like formatted. It is expected that this string will be in the same format returned by the getRawValue() method (i.e. a series of digits, such as
     * "1000" to represent "$10.00"). Note that formatCurrency will take in ANY string, and will first strip any non-digit characters before working on that string. If the result of that processing
     * reveals an empty string, or a string whose number of digits is greater than the max number of digits, an exception will be thrown.
     * @return A deviceLocale-formatted string of the passed in value, represented as currentCurrency.
     */
    fun formatCurrency(`val`: String): String? {
        return format(`val`)
    }

    /**
     * Pass in a value to have it formatted using the same rules used during data entry.
     * @param rawVal A long which represents the value you'd like formatted. It is expected that this value will be in the same format returned by the getRawValue() method (i.e. a series of digits, such as
     * "1000" to represent "$10.00").
     * @return A deviceLocale-formatted string of the passed in value, represented as currentCurrency.
     */
    fun formatCurrency(rawVal: Long): String? {
        return format(rawVal)
    }

    /*
    PRIVATE HELPER METHODS
     */

    /*
    PRIVATE HELPER METHODS
     */
    private fun refreshView() {
        txt.setText(format(getRawValue()))
        updateHint()
    }

    private fun format(`val`: Long): String {
        return CurrencyTextFormatter.formatText(
            `val`.toString(),
            currentLocale,
            defaultLocale,
            decimalDigits
        )
    }

    private fun format(`val`: String): String? {
        return CurrencyTextFormatter.formatText(`val`, currentLocale, defaultLocale, decimalDigits)
    }

    private fun init() {

        this.txt.setInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED)
        currentLocale = retrieveLocale()
        val currentCurrency = getCurrencyForLocale(currentLocale)
        decimalDigits = currentCurrency.defaultFractionDigits
        initCurrencyTextWatcher()
    }

    private fun initCurrencyTextWatcher() {
        if (textWatcher != null) {
            this.txt.removeTextChangedListener(textWatcher)
        }
        textWatcher = CurrencyTextWatcher(this,call)
        this.txt.addTextChangedListener(textWatcher)
    }

    private fun processAttributes() {

        hintCache = getHintString()
        updateHint()
        setAllowNegativeValues(false)
        setDecimalDigits(decimalDigits)
    }

    private fun updateHint() {
        if (hintCache == null) {
            txt.setHint(getDefaultHintValue())
        }
    }

    private fun getDefaultHintValue(): String? {
        return try {
            Currency.getInstance(currentLocale).symbol
        } catch (e: Exception) {
            Log.w(
                "CurrencyEditText",
                String.format(
                    "An error occurred while getting currency symbol for hint using locale '%s', falling back to defaultLocale",
                    currentLocale
                )
            )
            try {
                Currency.getInstance(defaultLocale).symbol
            } catch (e1: Exception) {
                Log.w(
                    "CurrencyEditText",
                    String.format(
                        "An error occurred while getting currency symbol for hint using default locale '%s', falling back to USD",
                        defaultLocale
                    )
                )
                Currency.getInstance(Locale.US).symbol
            }
        }
    }

    private fun retrieveLocale(): Locale {
        val locale: Locale
        locale = try { txt.context.resources.configuration.locale
        } catch (e: Exception) {
            Log.w(
                "CurrencyEditText",
                String.format(
                    "An error occurred while retrieving users device locale, using fallback locale '%s'",
                    defaultLocale
                ),
                e
            )
            defaultLocale
        }
        return locale
    }

    private fun getCurrencyForLocale(locale: Locale?): Currency {
        val currency: Currency
        currency = try {
            Currency.getInstance(locale)
        } catch (e: Exception) {
            try {
                Log.w(
                    "CurrencyEditText",
                    String.format(
                        "Error occurred while retrieving currency information for locale '%s'. Trying default locale '%s'...",
                        currentLocale,
                        defaultLocale
                    )
                )
                Currency.getInstance(defaultLocale)
            } catch (e1: Exception) {
                Log.e(
                    "CurrencyEditText",
                    "Both device and configured default locales failed to report currentCurrency data. Defaulting to USD."
                )
                Currency.getInstance(Locale.US)
            }
        }
        return currency
    }

    fun setRawValue(value: Long) {
        rawValue = value
    }



}//END CLASS


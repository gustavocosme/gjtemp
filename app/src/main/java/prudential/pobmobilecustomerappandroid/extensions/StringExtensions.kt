package prudential.pobmobilecustomerappandroid.extensions

import android.util.Log
import prudential.pobmobilecustomerappandroid.utils.DateUtils
import prudential.pobmobilecustomerappandroid.utils.TypeDoc
import prudential.pobmobilecustomerappandroid.utils.form.FormHelper
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Matcher
import java.util.regex.Pattern

//fun String.maskString(mask: String): String? {
//
//    var i = 0
//    var mascara: String? = ""
//    for (m in mask.toCharArray()) {
//        if (m != '#') {
//            mascara += m
//            continue
//        }
//        mascara += try {
//            this[i]
//        } catch (e: Exception) {
//            break
//        }
//        i++
//    }
//    return mascara
//}

/**
 * Using regex date validate.
 * @receiver any string to perform validate date.
 * @return number of Boolean.
 */

fun String.dateValidate():Boolean
{
    return DateUtils().validateDate(this)
}


/**
 * Using regex to count letter of a given string.
 * @receiver any string to perform count.
 * @return number of letters.
 */

fun String.countLetter(): Int {
    val a = this.replace("[^A-Za-z0-9]", "").toLowerCase(Locale.ROOT)
    return a.count()
}

/**
 * Using regex to check if has at least one number.
 * @receiver string to be checked.
 * @return true if find number, false if not.
 */
fun String.isOneNumber(): Boolean {
    val p: Pattern = Pattern.compile("([0-9])")
    val m: Matcher = p.matcher(this)
    return m.find()
}

/**
 * Using regex to check if string is repeating same char 3 times in a row.
 * @receiver string to be checked.
 * @return true if find, false if not.
 */
fun String.isNo3Repeat(): Boolean {
    val p: Pattern = Pattern.compile("(\\w)\\1+\\1+")
    val m: Matcher = p.matcher(this)
    return m.find()
}
/**
 * Using regex to check if string has especial character.
 * @receiver string to be checked.
 * @return true if find, false if not.
 */
fun String.isCharEspecial(): Boolean {
    val p: Pattern = Pattern.compile("[!@#\$%&*()_+=|<>?{}\\\\[\\\\]~-]")
    val m: Matcher = p.matcher(this)
    return m.find()
}

/**
 * Replacing special characters with blank space.
 * @receiver string to be replaced.
 * @return new string without replaced characters.
 */
fun String.removeCPFChars(): String {
    return replace(".", "").replace("-", "").replace("/", "")
}

/**
 * Check if string is a valid CPF or CNPJ.
 * @receiver string to be checked.
 * @return true if is CPF or CNPF, false if not.
 */
fun String.isValidationCpfCnpj(): Boolean = FormHelper.getDocType(removeCPFChars()) != TypeDoc.NONE

/**
 * Validate password with 8 characters, especial character and not repeat 3 characters ina row.
 * @receiver string to be checked.
 * @return true if is validated, false if not.
 */
fun String.isValidationPasswordPrudential(): Boolean = isOneNumber() && isCharEspecial() && countLetter() >= 8












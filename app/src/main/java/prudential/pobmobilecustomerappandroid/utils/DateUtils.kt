package prudential.pobmobilecustomerappandroid.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateUtils {


    /**
     * Validate date format with regular expression
     * @param dateStr date address for validation
     * @return true valid date format, false invalid date format
     */

    val DATE_FORMAT: String? = "dd/MM/yyyy"

    fun dateBeetWeen(dateStr:String,dateStr2:String):Boolean
    {

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
        val date: Date = sdf.parse(dateStr)
        val date2: Date = sdf.parse(dateStr2)
        val diff: Long =  date2.getTime()-date.getTime();
        val day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)

        if(day <= 0)
            return false;
        else
            return true;

    }

    fun validateDate(date: String): Boolean {
        return try {
            val df: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.ROOT)
            df.setLenient(false)
            df.parse(date)
            true
        } catch (e: ParseException) {
            false
        }
    }


}
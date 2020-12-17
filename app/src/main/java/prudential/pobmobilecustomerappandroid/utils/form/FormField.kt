package prudential.pobmobilecustomerappandroid.utils.form

import android.content.Context
import android.graphics.PorterDuff
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import org.jetbrains.anko.textColor
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.*
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.TypeDoc


class FormField {

    lateinit var txt: EditText
    lateinit var txtError: TextView
    lateinit var layoutErro: TextInputLayout
    lateinit var context: Context
    lateinit var isValidationCall: () -> Boolean
    lateinit var onFocus: () -> Any

    var daySelect = 0
    var monthSelect = 0

    val Days = intArrayOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    val Months = arrayOf(
        "Janeiro",
        "Fevereiro",
        "Março",
        "Abril",
        "Maio",
        "Junho",
        "Julho",
        "Agosto",
        "Setembro",
        "Outubro",
        "Novembro",
        "Dezembro"
    )

    var isErro = true
    var isOk = false

    fun inicialize(
        context: Context,
        txt: EditText,
        txtError: TextView,
        layoutErro: TextInputLayout
    ) {

        this.txt = txt
        this.txtError = txtError
        this.layoutErro = layoutErro
        this.context = context

        layoutErro.setErrorIconTintMode(PorterDuff.Mode.MULTIPLY)
        layoutErro.isErrorEnabled = true
        layoutErro.setErrorIconDrawable(0)
        layoutErro.setHintAnimationEnabled(false);

        try {

            val errorView: TextView = layoutErro.findViewById(com.google.android.material.R.id.textinput_error)
            errorView.maxLines = 1
            errorView.textSize = 0f;
            errorView.isSingleLine = true

        }catch (e:Exception)
        {

        }


    }

    fun setValue(text: String) {
        txt.setText(text)
    }

    //EVENTS USER VALIDATION

    fun addEventsMoneyValidation(call: () -> Any) {

        txt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                isOk = false;

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }


    fun addEventsDocCpFCnpjValidation(call: () -> Any) {

        onRenderDocValidation()

        txt.addTextChangedListener(CpfCnpjMaks.insert(txt))

        txt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                isOk = false;
                errorClose();


                if (p0.toString().length == 14 || p0.toString().length >= 18) {

                    isOk = true

                    /*
                    if (getTypeDocString(p0.toString()) != TypeDoc.CPF) {

                        error(context.getString(R.string.erro_doc))
                        isOk = false

                    }else{

                        isOk = true
                        errorClose()

                    }


                     */

                    call()

                }else{


                    isOk = false
                    call()

                }

                /*
                if (p0.toString().length == 18) {

                    isOk = true;

                    /*
                    if (getTypeDocString(p0.toString()) != TypeDoc.CNPJ) {

                        error(context.getString(R.string.erro_doc))
                        isOk = false

                    } else {

                        isOk = true
                        errorClose()

                    }

                     */

                    call()


                }else{

                    isOk = false
                    call()


                }

                 */


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }


    fun addEventDateComplete(call: () -> Any) {

        txt.addTextChangedListener(Mask.insert("##/##/####", txt));

        txt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                isOk = false

                if (p0.toString().count() < 10) {
                    isOk = false
                    errorClose();
                    return
                }

                if (txt.text.toString().equals("")) {

                    isOk = false
                    errorClose();
                    return
                }

                isOk = p0.toString().dateValidate();

                if(!isOk)
                {
                    error(context.getString(R.string.erro_doc_data))
                }

                call();
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }


    fun addEventsPassword(call: () -> Any) {

        onRendePassWordValidation()

        txt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (isValidationCount()) {

                    errorClose()
                    isOk = true

                } else {

                    isOk = false
                }

                call()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }

    fun addEventsDay(call: () -> Any) {

        txt.setOnFocusChangeListener { view, hasFocus ->

            if (!hasFocus) {

                if (!isOk) {
                    error(context.getString(R.string.erro__day))
                } else {

                    errorClose()
                }

            }
        }

        txt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {


                if (p0.toString().equals("0") || p0.toString()
                        .equals("")
                ) {
                    isOk = false
                    errorClose()
                } else {


                    var d = 0

                    if (!txt.text.toString().equals("")) {
                        d = txt.text.toString().toInt()
                    }

                    val day = d


                    if (day < 32 && day > 0) {

                        daySelect = day
                        isOk = true
                        errorClose()

                    } else {

                        isOk = false
                        error(context.getString(R.string.erro__day))

                    }

                    call()

                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }

    fun addEventsMonth(call: () -> Any) {

        txt.setOnFocusChangeListener { view, hasFocus ->

            if (!hasFocus) {

                if (!isOk) {
                    isOk = false
                    error(context.getString(R.string.erro__month))
                }

            }
        }

        txt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (p0.toString().equals("0") || p0.toString()
                        .equals("")
                ) {

                    errorClose()
                    isOk = false

                } else {


                    var d = 0

                    if (!txt.text.toString().equals("")) {
                        d = txt.text.toString().toInt()
                    }


                    val month = d

                    if (month < 13 && month > 0) {
                        monthSelect = month

                        isOk = true
                        errorClose()
                    } else {

                        isOk = false
                        error(context.getString(R.string.erro__month))

                    }

                    call()

                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }

    fun addEventsYear(call: () -> Any) {

        setIsValidationCall {

            if (txt.text.toString().toString().count() < 4) {
                isOk = false
                error(context.getString(R.string.erro__year))
                return@setIsValidationCall false
            }

            var d = 0

            if (!txt.text.toString().equals("")) {
                d = txt.text.toString().toInt()
                errorClose()
                return@setIsValidationCall false
            }

            val year = d

            if (year < 2021 && year > 1930) {
                isOk = true
                errorClose()

            } else {

                isOk = false
                error(context.getString(R.string.erro__year))

            }

            return@setIsValidationCall isOk

        }

        txt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (p0.toString().count() < 4) {
                    isOk = false
                    //error("Ano inválido.")
                    return
                }

                var d = 0

                if (!txt.text.toString().equals("")) {
                    d = txt.text.toString().toInt()
                }

                val year = d

                if (year < 2021 && year > 1930) {
                    isOk = true
                    errorClose()

                } else {

                    isOk = false
                    error("Ano inválido.")

                }

                call()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })


    }


    //RENDER VALIDATION VALIDATION

    fun onRenderCountMessage(error: String) {
        setIsValidationCall {

            var resp = true
            isOk = true

            if (!isValidationCount()) {
                error(error)
                resp = false
                isOk = false
            }

            if (resp) {
                errorClose()
            }

            resp


        }
    }

    fun onRendePassWordValidationCopy(copy: String) {
        setIsValidationCall {

            var resp = true
            isOk = true

            if (!copy.equals(txt.text.toString())) {
                error(context.getString(R.string.erro_fiel_0_password_two))
                resp = false
                isOk = false

            }

            if (!isValidationCount()) {
                error(context.getString(R.string.erro_fiel_0_password))
                resp = false
                isOk = false
            }

            if (resp) {
                errorClose()
            }

            resp


        }
    }

    fun onRendePassWordValidation() {
        setIsValidationCall {

            var resp = true
            isOk = true

            if (!isValidationCount()) {
                error(context.getString(R.string.erro_fiel_0_password))
                resp = false
                isOk = false

            }

            //if (!isEspecialPasswordValidation(txt.text.toString())) {
            //error(context.getString(R.string.erro_password_no))
            //resp = false
            //}

            if (resp) {
                errorClose()
            }

            resp


        }
    }

    fun onRenderDocValidation() {
        setIsValidationCall {

            var resp = true
            isOk = true

            if (getTypeDoc() == TypeDoc.NONE) {
                error(context.getString(R.string.erro_doc))
                resp = false
                isOk = false

            } else
                if (!isValidationCount()) {
                    error(context.getString(R.string.erro_fiel_0_cpf_cnpj))
                    resp = false
                    isOk = false

                }

            if (resp) {
                errorClose()
            }

            resp


        }

    }

    fun error(error: String, sleepReturn: Boolean = false) {

        if (isErro) {
            (context as AppCompatActivity).alertCustom(error, TypeAlertCustom.ERROR)
            txtError.textColor = context.resources.getColor(R.color.errorColor)
            layoutErro.error = error
            //Log.e(error, error)

        }


    }

    fun errorQuad() {

        if (isErro) {
            txtError.textColor = context.resources.getColor(R.color.errorColor)
            layoutErro.error = ""
            //Log.e(error, error)

        }


    }

    fun errorClose() {
        txtError.textColor = context.resources.getColor(R.color.colorBlackText)
        layoutErro.error = ""
    }

    //VALIDATION

    fun isYearValidation() {

        if (txt.text.toString().equals("")) {
            return

        }

        if (txt.text.toString().count() < 4) {
            isOk = false
            error("Ano inválido.")
            return
        }

    }

    fun dateValidation(day: Int, month: Int): Boolean {

        if (day == 0 || month == 0) {
            return false

        }

        if (day > Days[month - 1]) {
            return false
        }
        return true
    }


    fun setIsValidationCall(isValidationCall: () -> Boolean) {
        this.isValidationCall = isValidationCall

        txt.setOnFocusChangeListener { view, hasFocus ->

            if (!hasFocus) {

                isValidationCall()

            } else {

                try {

                    onFocus()

                } catch (e: Exception) {

                }

            }
        }

    }


    fun getTypeDoc(): TypeDoc {
        return FormHelper.getDocType(txt.text.toString().removeCPFChars())
    }

    fun getTypeDocString(value:String): TypeDoc {
        return FormHelper.getDocType(value.removeCPFChars())
    }


    fun isValidationCPF(): Boolean {

        return FormHelper.checkCPF(txt.text.toString())

    }

    fun isValidationCNPJ(): Boolean {

        return FormHelper.checkCNPJ(txt.text.toString())

    }

    fun isValidationCount(): Boolean {

        if (txt.text.toString().count() <= 0) {
            return false
        }

        return true

    }

    fun isEspecialPasswordValidation(value: String): Boolean {

        if (value.toString().countLetter() >= 8 && value.toString()
                .isCharEspecial() && value.toString().isOneNumber()
        ) {

            return true

        }

        return false

    }

    //EVENTS//

    fun onSendKeyboard(onSend: () -> Any) {

        txt.imeOptions = EditorInfo.IME_ACTION_SEND

        txt.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEND) {

                onSend()
                return@OnEditorActionListener true

            }
            false
        })

    }

    fun onAddCountLengtValidation(lenght: Int, onRender: () -> Any) {
        txt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                //Log.e("TOTAL",p0.toString().count().toString());
                if (p0.toString().length == lenght) {
                    this@FormField.isValidationCall()
                }

                if (onRender != null) {
                    onRender()
                }

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    fun onChangePasswordValidation(call: (data: DataValidationPassword) -> Any) {

        var data = DataValidationPassword()

        setIsValidationCall {

            data.CHAR_8 = false

            if (txt.text.toString().countLetter() >= 8) {
                data.CHAR_8 = true
            }

            data.NO_3_REPETION = txt.text.toString().isNo3Repeat()



            if (data.CHAR_8 && !data.NO_3_REPETION) {
                errorClose()
                true

            } else {

                error(context.getString(R.string.erro_password_erro_2))
                false

            }


            /*

            data.ESPECIAL_1 = txt.text.toString().isCharEspecial()
            data.NUMBER_1 = txt.text.toString().isOneNumber()

            if(data.CHAR_8 && data.ESPECIAL_1 && data.NUMBER_1)
            {
                errorClose();
                true;

            }else{

                error(context.getString(R.string.erro_password_erro_2))
                false
            }


             */

            false
        }


        txt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                //Log.e("Letter count", p0.toString().countLetter().toString());
                //Log.e("1 Number count",p0.toString().isOneNumber().toString());
                //Log.e("Is Especial",p0.toString().isCharEspecial().toString());

                data.CHAR_8 = false

                if (p0.toString().countLetter() >= 8) {

                    data.CHAR_8 = true

                }

                data.NO_3_REPETION = p0.toString().isNo3Repeat()


                if ((!data.CHAR_8 || data.NO_3_REPETION) && p0.toString().countLetter() >= 8)
                    error(context.getString(R.string.erro_password_erro_2))

                call(data)


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    fun change(onRender: () -> Any) {

        txt.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                onRender()

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }


}

class DataValidationPassword {

    var CHAR_8 = false
    var NO_3_REPETION = false
    var NUMBER_1 = false
    var ESPECIAL_1 = false

}
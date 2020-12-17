package prudential.pobmobilecustomerappandroid.ui.viewCustom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import prudential.pobmobilecustomerappandroid.App
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.adjustHeight
import prudential.pobmobilecustomerappandroid.extensions.nextToFocus
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.utils.DateUtils
import prudential.pobmobilecustomerappandroid.utils.form.FormField
import prudential.pobmobilecustomerappandroid.utils.form.Keyboard

/**
 * Class build to Extract Filter.
 */
class ExtractFilter(context: Context) : RelativeLayout(context) {

    lateinit var cachedView: View;
    lateinit var checkAccomplished: AppCompatCheckBox;
    lateinit var checkLate: AppCompatCheckBox;
    lateinit var checkCredit: AppCompatCheckBox;
    lateinit var checkDebit: AppCompatCheckBox;
    lateinit var checkFinancial: AppCompatCheckBox;
    lateinit var checkBillet: AppCompatCheckBox;
    lateinit var btnRemoveAll: Button;

    var arrayChecks: ArrayList<AppCompatCheckBox> = ArrayList();
    lateinit var btnFilter: Button;
    lateinit var txtDateIn: TextInputEditText;
    lateinit var txtDateOut: TextInputEditText;
    var formIn: FormField = FormField()
    var formOut: FormField = FormField()
    lateinit var call: (value: DataFilterExtract) -> Any

    init {

        inflate();

    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.extract_filter, this, true)

        inicialize();

    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        checkAccomplished =
            cachedView.findViewById(R.id.filter_check_realizado) as AppCompatCheckBox;
        checkLate = cachedView.findViewById(R.id.filter_check_atrasado) as AppCompatCheckBox;
        checkCredit = cachedView.findViewById(R.id.filter_check_credito) as AppCompatCheckBox;
        checkDebit = cachedView.findViewById(R.id.filter_check_debito) as AppCompatCheckBox;
        checkFinancial =
            cachedView.findViewById(R.id.filter_check_assistencia) as AppCompatCheckBox;
        checkBillet = cachedView.findViewById(R.id.filter_check_boleto) as AppCompatCheckBox;
        txtDateIn = cachedView.findViewById(R.id.filter_data_txt_de) as TextInputEditText;
        txtDateOut = cachedView.findViewById(R.id.filter_data_txt_ate) as TextInputEditText;
        btnFilter = cachedView.findViewById(R.id.btnNext) as Button;
        btnRemoveAll = cachedView.findViewById(R.id.btnBack) as Button;




        txtDateIn.nextToFocus(
            txtDateOut,
            txtDateIn,
            10
        )

        //txtDateOut.nextToFocus(
        //  txtDateIn,
        //txtDateOut,
        //10
        //)

        formIn.inicialize(
            context,
            txtDateIn,
            TextView(context),
            cachedView.findViewById(R.id.filter_data_layout_de) as TextInputLayout
        )
        formOut.inicialize(
            context,
            txtDateOut,
            TextView(context),
            cachedView.findViewById(R.id.filter_data_layout_ates) as TextInputLayout
        )

        formIn.addEventDateComplete({})
        formOut.addEventDateComplete({

            if (txtDateOut.text.toString().length == 10) {
                if (formIn.isOk && formOut.isOk) {
                    if (!DateUtils().dateBeetWeen(
                            txtDateIn.text.toString(),
                            txtDateOut.text.toString()
                        ) && !txtDateIn.text.toString().equals(txtDateOut.text.toString())
                    ) {
                        formOut.error(context.getString(R.string.erro__date_two))
                        formIn.error(context.getString(R.string.erro__date_two))

                        formOut.errorClose();
                        formIn.errorClose();


                    }
                }
            }

        })

        if (App.IS_TEST) {
            //txtDateIn.setText("17/10/1988");
            //txtDateOut.setText("18/10/1988");
        }

        cachedView.findViewById<ConstraintLayout>(R.id.container)
            .setOnClickListener { Keyboard.removeThis(Tab.INSTANCE) }

        formOut.onSendKeyboard {

            Keyboard.removeThis(Tab.INSTANCE);
            onFilter();
        }

        /*
        formIn.setIsValidationCall {

            val r = txtDateIn.text.toString().dateValidate();
            formIn.isOk = r;

            if(!r)
            {
                formIn.error(context.getString(R.string.erro_data_erro))
                false;
            }else{
                true;
            }

        }

        formOut.setIsValidationCall {

            val r = txtDateOut.text.toString().dateValidate();
            formOut.isOk = r;

            if(!r)
            {
                formOut.error(context.getString(R.string.erro_data_erro))
                false;

            }else{

                formOut.errorClose();
                true;
            }

        }

         */

        arrayChecks.add(checkAccomplished);
        arrayChecks.add(checkLate);
        arrayChecks.add(checkCredit);
        arrayChecks.add(checkDebit);
        arrayChecks.add(checkFinancial);
        arrayChecks.add(checkBillet);

        btnRemoveAll.setOnClickListener { onReset() };
        btnFilter.setOnClickListener { onFilter() };

        adjustHeightButton();

    }

    fun adjustHeightButton() {

        for (i in arrayChecks) {
            i.adjustHeight()
        }
    }


    /**
     * Click event reset all
     */
    fun onReset() {
        for (i in arrayChecks) {
            i.isChecked = false;
        }

        formIn.isOk = false;
        formOut.isOk = false;
        txtDateIn.setText("");
        txtDateOut.setText("");
    }

    /**
     * Click filter action call
     */
    fun onFilter() {
        if (txtDateIn.text.toString().length > 0 || txtDateOut.text.toString().length > 0) {
            if (!formIn.isOk || !formOut.isOk) {
                formOut.error(context.getString(R.string.erro_doc_datas))
                return;
            }

            if (formIn.isOk && formOut.isOk) {

                if (!DateUtils().dateBeetWeen(
                        txtDateIn.text.toString(),
                        txtDateOut.text.toString()
                    ) && !txtDateIn.text.toString().equals(txtDateOut.text.toString())
                ) {
                    formOut.error(context.getString(R.string.erro__date_two))
                    formIn.error(context.getString(R.string.erro__date_two))

                    formOut.errorClose();
                    formIn.errorClose();

                    return;
                }
            }

        }




        formIn.errorClose();
        formOut.errorClose();

        val data =
            DataFilterExtract();

        for (i in arrayChecks) {
            if (i.isChecked) {
                data.count++;
            }
        }

        if (formIn.isOk) {
            data.count++;
        }

        if (formOut.isOk) {
            data.count++;
        }

        if (checkAccomplished.isChecked) {
            data.isAccomplished = checkAccomplished.isChecked;
        }
        if (checkCredit.isChecked) {
            data.isCredit = checkCredit.isChecked;
        }
        if (checkDebit.isChecked) {
            data.isDebit = checkDebit.isChecked;
        }
        if (checkFinancial.isChecked) {
            data.isFinancial = checkFinancial.isChecked;
        }
        if (checkLate.isChecked) {
            data.isLate = checkLate.isChecked
        }
        if (checkBillet.isChecked) {
            data.isBillet = checkBillet.isChecked;
        }
        data.dateInS = txtDateIn.text.toString();
        data.dateOut = txtDateOut.text.toString();

        Tab.INSTANCE.onCloseSlide();

        try {
            call(data);
        } catch (e: Exception) {
        }


    }

}//END CLASS

/**
 * Object return filter
 */
class DataFilterExtract() {
    var isAccomplished: Boolean? = null;
    var isLate: Boolean? = null;
    var isCredit: Boolean? = null;
    var isDebit: Boolean? = null;
    var isFinancial: Boolean? = null;
    var isBillet: Boolean? = null;
    var dateInS = "";
    var dateOut = "";
    var count = 0;

    /**
     * Check if object has null fields.
     * @receiver Object to be checked.
     * @return True if all fields is null, false if any of them is not null.
     */
    fun isAllNull(): Boolean = isFinancial == null &&
            isCredit == null &&
            isDebit == null &&
            isBillet == null

    /**
     * Check if both dateInS and dateOut is blank.
     * @return True if is blank, false if not.
     */
    fun isDateNull() = dateInS == "" && dateOut == ""

    /**
     * Check if isAccomplished and isLate is null.
     * @return True if both is null, false, if not.
     */
    fun isPaidNull() = isAccomplished == null && isLate == null

}
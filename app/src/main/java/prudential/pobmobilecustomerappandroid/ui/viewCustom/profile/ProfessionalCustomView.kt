package prudential.pobmobilecustomerappandroid.ui.viewCustom.profile

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_user_professional.view.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.alertCustom
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.model.DataConfirmationUpdate
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.adapter.SimpleSelectAdapter
import prudential.pobmobilecustomerappandroid.ui.fragment.UserConfirmationUpdateFragment
import prudential.pobmobilecustomerappandroid.utils.Dialogs
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom
import prudential.pobmobilecustomerappandroid.utils.form.FormField
import prudential.pobmobilecustomerappandroid.utils.form.Keyboard
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Class build to Extract Filter.
 */
class ProfessionalCustomView(context: Context) : RelativeLayout(context),
    OnRecyclerClickListener<String> {

    lateinit var cachedView: View

    lateinit var containerButton: ConstraintLayout;
    lateinit var txtProfessionalFiel: EditText;
    lateinit var txtProfessionalMainFiel: EditText;
    lateinit var txtMoneyFiel:EditText;

    var formMoney: FormField = FormField()
    var formProfessional: FormField = FormField()
    var formMain: FormField = FormField()
    lateinit var listCard: CardView

    lateinit var list: RecyclerView
    var isList = false

    private val selectAdapterProf by lazy { SimpleSelectAdapter(this) }

    var dataValidationMoney:DataConfirmationUpdate = DataConfirmationUpdate()
    var dataValidationProfessional:DataConfirmationUpdate = DataConfirmationUpdate()
    var dataValidationProfessionalMain:DataConfirmationUpdate = DataConfirmationUpdate()


    init {

        inflate()

    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {

        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.fragment_user_professional, this, true)

        inicialize()

    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        list = cachedView.findViewById(R.id.list) as RecyclerView
        listCard = cachedView.findViewById(R.id.listCard) as CardView

        containerButton = cachedView.findViewById(R.id.containerBtn) as ConstraintLayout
        txtMoneyFiel = cachedView.findViewById(R.id.txtMoneyy) as EditText
        txtProfessionalFiel = cachedView.findViewById(R.id.txtProfessional) as EditText
        txtProfessionalMainFiel = cachedView.findViewById(R.id.txtMain) as EditText

        cachedView.apply {


            dataValidationMoney.label1 = txt1.text.toString();
            dataValidationProfessional.label1 = txt2.text.toString();
            dataValidationProfessionalMain.label1 = txt3.text.toString();

            dataValidationMoney.current = "R$ 12.000,00"
            dataValidationProfessional.current = "Design"
            dataValidationProfessionalMain.current = "UX"

            formMoney.inicialize(
                context,
                txtMoneyy,
                txt1,
                l1
            )

            /*
            CurrencyEditTextG(txtMoneyy,{


                isValidationButton();

                ""

                //isValidationButton();

            });*/



            txtMoneyy.setText(dataValidationMoney.current)
            txtProfessional.setText(dataValidationProfessional.current)
            txtProfessionalMainFiel.setText(dataValidationProfessionalMain.current)

            formProfessional.inicialize(
                context,
                txtProfessional,
                txt2,
                l2
            )

            formMain.inicialize(
                context,
                txtProfessionalMainFiel,
                txt3,
                l3
            )

            formProfessional.change {
                isValidationButton();
            }

            formMain.change {
                isValidationButton();
            }



            btnProfessional.setOnClickListener { onClickProfessional() }
            btnNext.setOnClickListener { onClickSave(); }
            btnBack.setOnClickListener { onClickCancel(); }
            containerBtn.visibility = View.GONE

            formMain.onSendKeyboard {

               Keyboard.removeThis(Tab.INSTANCE);
            }



        }

        initMoney();
        initRecyclerView()
    }


    fun initMoney()
    {

        txtMoneyy.addTextChangedListener(object : TextWatcher {

            var  locale: Locale = Locale("pt","BR")



            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if(txtMoneyy.text.toString().equals(""))
                {
                    txtMoneyy.setText("R$ 0,00");
                    return;
                }

                txtMoneyy.removeTextChangedListener(this)
                val parsed: BigDecimal? = parseToBigDecimal(txtMoneyy.text.toString(), locale)
                val formatted: String =
                    NumberFormat.getCurrencyInstance(locale).format(parsed)
                // NumberFormat.getNumberInstance(locale).format(parsed); // sem o simbolo de moeda
                txtMoneyy.setText(formatted)

                Log.e("txtMoneyFiel",txtMoneyFiel.text.toString());
                Log.e("dataValidationMoney",dataValidationMoney.current);


                /*

               var a = txtMoneyFiel.text.toString().replace(".","").replace(",","").replace("R$","").replace(" ","").trim().toDouble()
               var b = dataValidationMoney.current.toString().replace(".","").replace(",","").replace("R$","").replace(" ","").trim().toDouble()

                Log.e("A",a.toString());
                Log.e("B",b.toString());

                if(a == b)
                {
                    Log.e("GLORIA A DEUS","GLORIA A DEUS");
                    containerButton.visibility = View.GONE;
                }else{
                    containerButton.visibility = View.VISIBLE;
                }

                 */

                isValidationButton();
                txtMoneyy.setSelection(formatted.length)
                txtMoneyy.addTextChangedListener(this)

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

            private fun parseToBigDecimal(value: String?, locale: Locale?): BigDecimal? {

                try {
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
                }catch (e:Exception)
                {
                    return BigDecimal(0);
                }
            }

        })


    }



    /**
     * Inicialie Adapter
     */
    private fun initRecyclerView() {
        list.apply {
            layoutManager = LinearLayoutManager(cachedView.context)
            adapter = selectAdapterProf
        }

        val profs = mutableListOf<String>()
        profs.add("T.I")
        profs.add("Design")
        profs.add("Arquiteto")
        profs.add("Advogado")
        selectAdapterProf.submitList(profs)
    }


    /**
     * On Click Select Professional
     */
    fun onClickProfessional() {

        if (isList) {
            listCard.showOrGone(false)
            isList = false
        } else {
            listCard.showOrGone(true)
            list.showAnime()
            isList = true
        }
    }

    /**
     * On click button save
     */
    fun onClickSave() {


        Keyboard.removeThis(Tab.INSTANCE);

        dataValidationMoney.initHold();
        dataValidationProfessionalMain.initHold();
        dataValidationProfessional.initHold();

        dataValidationMoney.current = txtMoneyFiel.text.toString();
        dataValidationProfessionalMain.current = txtProfessionalMainFiel.text.toString();
        dataValidationProfessional.current = txtProfessionalFiel.text.toString();

        UserConfirmationUpdateFragment.CALL = {

             dataValidationMoney.current = dataValidationMoney.hold;
             dataValidationProfessional.current = dataValidationMoney.hold;
             dataValidationProfessionalMain.current = dataValidationMoney.hold;

             dataValidationMoney.current = txtMoneyFiel.text.toString();
             dataValidationProfessional.current = txtProfessionalFiel.text.toString();
             dataValidationProfessionalMain.current = txtProfessionalMainFiel.text.toString();

             containerButton.visibility = View.GONE;

             Tab.INSTANCE.alertCustom("Agradecemos por manter seus dados atualizados!",TypeAlertCustom.OK,3,"Alteração realizada com sucesso!");

        }

        UserConfirmationUpdateFragment.CALL_NO = {

            onReset()

        }


        val array = isValidationUpdate();
        val isCheck1 = false;
        val isCheck2 = false;
        val isToken  = false;
        containerButton.visibility = View.VISIBLE;
        Tab.INSTANCE.nav(UserConfirmationUpdateFragment.newInstance(0,array,isCheck1,isCheck2,isToken))

    }

    /**
     * On click cancel
     */
    fun onClickCancel() {

        Dialogs.addYesNo(context,"Desfazer mudanças?",object:Dialogs.CALL{

            override fun call(value: String?, position: Int) {

                if(position == 1)
                {
                    onResetAll()                }
            }
        })
    }


    /**
     * On Reset fields
     */
    fun onReset()
    {

        txtMoneyFiel.setText(dataValidationMoney.hold);
        txtProfessionalMainFiel.setText(dataValidationProfessionalMain.hold);
        txtProfessionalFiel.setText(dataValidationProfessional.hold);

        dataValidationMoney.current = dataValidationMoney.hold
        dataValidationProfessionalMain.current = dataValidationProfessionalMain.hold
        dataValidationProfessional.current = dataValidationProfessional.hold

        dataValidationMoney.hold = "";
        dataValidationProfessional.hold = "";
        dataValidationProfessionalMain.hold = "";

        containerButton.visibility = View.GONE;

    }

    fun onResetAll()
    {
        dataValidationMoney.hold = "";
        dataValidationProfessional.hold = "";
        dataValidationProfessionalMain.hold = "";
        txtMoneyFiel.setText(dataValidationMoney.current);
        txtProfessionalMainFiel.setText(dataValidationProfessionalMain.current);
        txtProfessionalFiel.setText(dataValidationProfessional.current);
        containerButton.visibility = View.GONE;

    }

    /**
     * Is validation fiels
     */
    fun isValidationButton():Boolean
    {

        var resp = false;

        if(txtMoneyFiel.text.toString().equals("") || txtProfessionalFiel.text.toString().equals("") || txtProfessionalMainFiel.text.toString().equals(""))
        {
            containerButton.visibility = View.GONE;
            resp = false;
        }

          containerButton.visibility = View.GONE;

        var a = txtMoneyFiel.text.toString().replace(".","").replace(",","").replace("R$","").replace(" ","").trim().toDouble()
        var b = dataValidationMoney.current.toString().replace(".","").replace(",","").replace("R$","").replace(" ","").trim().toDouble()

        if(a != b)
        {
            containerButton.visibility = View.VISIBLE;
            resp = true;
        }

        //Log.e("txtMoneyFiel.text",txtMoneyFiel.text.toString());
        //Log.e("dataValidationMoney",dataValidationMoney.current);


         //if(!txtMoneyFiel.text.toString().equals(dataValidationMoney.current))
         //{
            //containerButton.visibility = View.VISIBLE;
            //resp = true;
         //}

        if(!txtProfessionalFiel.text.toString().equals(dataValidationProfessional.current))
        {
            containerButton.visibility = View.VISIBLE;
            resp = true;
        }

        if(!txtProfessionalMainFiel.text.toString().equals(dataValidationProfessionalMain.current))
        {
            containerButton.visibility = View.VISIBLE;
            resp = true;
        }



        return resp;
    }

    fun isValidationUpdate():ArrayList<DataConfirmationUpdate>
    {
        val array = ArrayList<DataConfirmationUpdate>();

        var a = dataValidationMoney.hold.toString().replace(".","").replace(",","").replace("R$","").replace(" ","").trim().toDouble()
        var b = dataValidationMoney.current.toString().replace(".","").replace(",","").replace("R$","").replace(" ","").trim().toDouble()

        if(a != b)
        {
            array.add(dataValidationMoney);
        }

        if(!dataValidationProfessional.hold.equals(dataValidationProfessional.current))
        {
            array.add(dataValidationProfessional);
        }

        if(!dataValidationProfessionalMain.hold.equals(dataValidationProfessionalMain.current))
        {
            array.add(dataValidationProfessionalMain);
        }

        return array;

    }

    override fun onRecyclerItemClick(model: String, position: Int) {

        onClickProfessional()
        txtProfessionalFiel.setText(model)
        isValidationButton();
    }

}//END CLASS
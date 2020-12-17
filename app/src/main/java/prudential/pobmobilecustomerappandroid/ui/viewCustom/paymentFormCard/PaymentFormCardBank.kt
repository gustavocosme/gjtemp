package prudential.pobmobilecustomerappandroid.ui.viewCustom.paymentFormCard

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.payment_form_card_debit_bank.view.*
import prudential.pobmobilecustomerappandroid.App
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.addSaturation
import prudential.pobmobilecustomerappandroid.extensions.removeSaturation
import prudential.pobmobilecustomerappandroid.extensions.showAnime
import prudential.pobmobilecustomerappandroid.extensions.showOrGone
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.adapter.SimpleSelectAdapter
import prudential.pobmobilecustomerappandroid.utils.form.FormField


/**
 * Class build to Menu Slide APP.
 */
class PaymentFormCardBank(
    context: Context, attrs: AttributeSet
): RelativeLayout(context,attrs), OnRecyclerClickListener<String> {

    lateinit var cachedView: View
    var isList = false;
    lateinit var list: RecyclerView;
    lateinit var listCard: CardView;
    private val  selectDayAdapter by lazy { SimpleSelectAdapter(this) }
    lateinit var txtDay: TextView;
    lateinit var b1: ImageView;
    lateinit var b2: ImageView;
    lateinit var b3: ImageView;
    lateinit var b4: ImageView;
    lateinit var b5: ImageView;
    lateinit var b6: ImageView;
    var images =  ArrayList<ImageView>();

    var formBank: FormField = FormField()
    var formAgency: FormField = FormField()
    var formCount: FormField = FormField()

    init {
        inflate();
    }

    /**
     * Inflate View Class.
     */
    private fun inflate() {
        val mInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        cachedView = mInflater.inflate(R.layout.payment_form_card_debit_bank, this, true);
        inicialize();
    }

    /**
     * Inicialie Views and objects.
     */
    fun inicialize() {

        list        = cachedView.findViewById(R.id.list) as RecyclerView;
        listCard    = cachedView.findViewById(R.id.listCard) as CardView;
        txtDay      = cachedView.findViewById(R.id.txtBank) as TextView;

        b1 = cachedView.findViewById(R.id.b1) as ImageView;
        b2 = cachedView.findViewById(R.id.b2) as ImageView;
        b3 = cachedView.findViewById(R.id.b3) as ImageView;
        b4 = cachedView.findViewById(R.id.b4) as ImageView;
        b5 = cachedView.findViewById(R.id.b5) as ImageView;
        b6 = cachedView.findViewById(R.id.b6) as ImageView;
        
        b1.removeSaturation();
        b2.removeSaturation();
        b3.removeSaturation();
        b4.removeSaturation();
        b5.removeSaturation();
        b6.removeSaturation();

        images.add(b1);
        images.add(b2);
        images.add(b3);
        images.add(b4);
        images.add(b5);
        images.add(b6);

        cachedView!!.apply {
            btnNext.setOnClickListener {

                onClickSend();

            }
            btnBack.setOnClickListener {
                PaymentFormCard.INSTANCE!!.showNav(PaymentFormCard.INSTANCE!!.menu);
            }
            btnSelect.setOnClickListener {
                onClickDay();
            }

            formBank.inicialize(context,txtBank,txt22,layoutBank);
            formAgency.inicialize(context,txtAgency,txt33,layoutAgency);
            formCount.inicialize(context,txtCount,txt44,layoutCount);

            if(App.IS_TEST)
            {
                txtBank.setText("TESTE")
                txtAgency.setText("123")
                txtCount.setText("123")
            }

            formAgency.change {

                formAgency.errorClose();
            }

            formCount.change {

                formCount.errorClose();
            }

            formCount.onSendKeyboard {

                onClickSend()

            }



        }

        initRecyclerView();




    }

    /**
     * Init recycler view and adapter.
     */
    private fun initRecyclerView() {
        list.apply {
            layoutManager = LinearLayoutManager(cachedView.context)
            adapter = selectDayAdapter
        }
        val days = mutableListOf<String>()
        days.add("Banco 1");
        days.add("Banco 2");
        days.add("Banco 3");
        days.add("Banco 4");
        days.add("Banco 5");
        days.add("Banco 6");
        selectDayAdapter.submitList(days)
    }

    /**
     * Handle day click.
     */
    fun onClickDay() {
        if (isList) {
            listCard.showOrGone(false);
            isList = false;
        } else {
            listCard.showOrGone(true);
            listCard.showAnime();
            isList = true;
        }
    }

    /**
     * Handle recycler view item click.
     * @param model Item at clicked position.
     * @param position Position of item.
     */
    override fun onRecyclerItemClick(model: String, position: Int) {
        txtDay.text = "$model"
        listCard.showOrGone(false)
        isList = false

        var p = 0;
        for(i in images) {

            if(p != position)
            {
                i.setImageResource(0);
                i.setImageResource(R.drawable.ic_bradesco);
                i.removeSaturation();
            }

            p++;
        }
        formBank.errorClose();

        images.get(position).setImageResource(0);
        images.get(position).setImageResource(R.drawable.ic_bradesco);
        images.get(position).addSaturation();
    }

    /**
     * Set error message if field is not valid.
     */
    fun onClickSend()
    {
        cachedView!!.apply {

            var v = true;

            if(txtBank.text.toString().equals(""))
            {
                formBank.error("Banco inválido")
                v = false;
            }

            if(txtAgency.text.toString().equals(""))
            {
                formAgency.error("Agência inválida")
                v = false;
            }

            if(txtCount.text.toString().equals(""))
            {
                formCount.error("Conta inválida")
                v = false;
            }

            if(v){
                PaymentFormCard.INSTANCE!!.showNav(PaymentFormCard.INSTANCE!!.debitConfirmation);
            }



        }



    }

}//END CLASS


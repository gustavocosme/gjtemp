package prudential.pobmobilecustomerappandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main.*
import kotlinx.android.synthetic.main.menu_slide_footer.view.*
import kotlinx.android.synthetic.main.menu_slide_header.view.*
import kotlinx.android.synthetic.main.menu_slide_item.view.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.extensions.*
import prudential.pobmobilecustomerappandroid.ui.activity.app.Tab
import prudential.pobmobilecustomerappandroid.ui.fragment.ExampleInternaFragment
import prudential.pobmobilecustomerappandroid.ui.fragment.HomeFragment
import prudential.pobmobilecustomerappandroid.ui.fragment.PaymentDataFragment
import prudential.pobmobilecustomerappandroid.utils.Image
import prudential.pobmobilecustomerappandroid.utils.TypeAlertCustom

/**
 * Adapter class to handle menu more.
 */
class MenuSlideAdapter : RecyclerView.Adapter<MenuSlideAdapter.MenuSlideViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_FOOTER = 1
    private val TYPE_ITEM = 2

    companion object{

        fun openNavMenu(name:String)
        {
            //Log.e("Name",name);

            if(name.equals("Alterar dados de pagamento"))
                Tab.INSTANCE.nav(PaymentDataFragment.newInstance(0));
            else if(name.equals("Meus dados"))
                Tab.INSTANCE.bottomBar.setSelectedItemId(R.id.m2);
            else if(name.equals("Extrato"))
                Tab.INSTANCE.bottomBar.setSelectedItemId(R.id.m4);
            else{
                Tab.INSTANCE.alertCustom(name+" em desensolvimento",TypeAlertCustom.ALERT);
            }
        }

    }

    private var array = mutableListOf<Menu>(

        Menu("",mutableListOf(),MenuType.HEADER),
        Menu("Meu perfil",mutableListOf("Meus dados","Meus endereços","Comunicação digital")),
        Menu("Meus seguros",mutableListOf("Pessoas indenizadas","Coberturas","Dados do contrato","Simulação de seguros","Documentos","Manual do segurado")),
        Menu("Meus pagamentos",mutableListOf("Extrato","Alterar dados de pagamento","Informe de rendimentos","Documentos")),
        Menu("Atendimento",mutableListOf("Canais de atendimento","Dúvidas frequentes","Atendimento online")),
        Menu("",mutableListOf(),MenuType.FOOTER)

    )

    /**
     * Bind all data and set to each Layout.
     * @param position Position of each item.
     */
    override fun getItemViewType(position: Int): Int {

        val info = array[position];

        if(info.type == MenuType.HEADER)
        return TYPE_HEADER

        if(info.type == MenuType.FOOTER)
        return TYPE_FOOTER

        return TYPE_ITEM

    }

    /**
     * Bind all data and set to each payment position.
     * @param holder  to handle individual item of list.
     * @param position Position of each item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuSlideViewHolder{

        var v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.menu_slide_item, parent, false)
        var holder:MenuSlideViewHolder =  MenuSlideViewHolderItem(v);

        if(viewType == TYPE_HEADER)
        {
            v =  LayoutInflater.from(parent.context)
                .inflate(R.layout.menu_slide_header, parent, false)
            holder =  MenuSlideViewHolderHeader(v);
        }

        if(viewType == TYPE_FOOTER)
        {
            v =  LayoutInflater.from(parent.context)
                .inflate(R.layout.menu_slide_footer, parent, false)
            holder =  MenuSlideViewHolderFooter(v);
        }

       return holder;

    }

     var holderCurrent:MenuSlideViewHolderItem? = null


    /**
     * Bind all data and set to each payment position.
     * @param handle individual item of list.
     * @param position Position of each item.
     */
    override fun onBindViewHolder(holder: MenuSlideViewHolder, position: Int) {

        val info = array[position]
        holder.bind(info);

        if(info.type == MenuType.ITEM)
        {



            val h = holder as MenuSlideViewHolderItem;
            h.call = object:Call{

                override fun call(holder: MenuSlideViewHolder) {

                    if(holderCurrent != null)
                    {
                        if(!holderCurrent!!.paramInfo.title.equals(info.title))
                        {
                            holderCurrent!!.hideContainer();
                        }
                    }

                    holderCurrent = holder  as MenuSlideViewHolderItem;

                }

            }
        }


    }

    /**
     * Get size of list.
     * @return Size of list.
     */
    override fun getItemCount(): Int = array.size

    /**Class layout generic.
     */
    open class MenuSlideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var call:Call;

        open fun bind(info: Menu){


        }



    }//END CLASS

    /**Class layout Buttom slide.
     */
    class MenuSlideViewHolderItem(itemView: View) : MenuSlideViewHolder(itemView) {

        var isUp = false;
        lateinit var container:LinearLayout;
        lateinit var arrow:ImageView;
        lateinit var paramInfo:Menu;

        override fun bind(info: Menu){

            paramInfo = info;
            container = itemView.findViewById(R.id.container) as LinearLayout;
            arrow = itemView.findViewById(R.id.arrow) as ImageView;

            itemView.apply {

                txt.text = info.title
                for(i in info.itens) {
                    val item = LayoutInflater.from(context).inflate(R.layout.menu_slide_item_item, null, false)
                    item.apply {
                        txt.text = i
                    }

                    item.setOnClickListener {

                        openNavMenu(i);

                    }


                    container.addView(item);
                }
            }

            itemView.findViewById<ConstraintLayout>(R.id.click).setOnClickListener {

                if(isUp)
                {
                    kotlin.run {
                        hideContainer();
                    }


                }else{

                    kotlin.run {
                        showContainer();
                    }
                }

                call.call(this);

            }

        }

        fun showContainer()
        {
            isUp = true
            //container.showOrGone(true);
            container.animeExpand();
            arrow.showAnimeRotation(180f)


        }

        fun hideContainer()
        {
            isUp = false
            //container.showOrGone(false);
            container.animeCollapse();
            arrow.showAnimeRotation(0f)
        }







    }//END CLASS

    /**Class layout Header.
     */
    class MenuSlideViewHolderHeader(itemView: View) : MenuSlideViewHolder(itemView) {

        override fun bind(info: Menu){

            itemView.apply {
                Image.load(foto,"http://gustavocosme.com/api_9873246uyetkjhertiuy49567/foto.png")
                nome.text = "Maura"
                tipo.text = "Segurada"
                anos.text = "Cliente há 10 anos"
            }

        }

    }//END CLASS

    /**Class layout Footer.
     */
    class MenuSlideViewHolderFooter(itemView: View) : MenuSlideViewHolder(itemView) {

        override fun bind(info: Menu){

            itemView.apply {

                btnSolicit.setOnClickListener {

                    openNavMenu("Solicitar indenização");

                }

                btnConfiguration.setOnClickListener {

                    openNavMenu("Configurações");

                }

                btnTerms.setOnClickListener {

                    openNavMenu("Termos de uso");

                }

                btnPrivacity.setOnClickListener {

                    openNavMenu("Política de privacidade");

                }

                val versionName = context.packageManager
                    .getPackageInfo(context.packageName, 0).versionName
                txtVersion.text = "V "+versionName;

            }


        }

    }//END CLASS


    data class Menu(

        val title: String = "",
        val itens: MutableList<String>,
        val type:MenuType = MenuType.ITEM,
        var isOpen:Boolean  = false

    )

    enum class MenuType {
        FOOTER, HEADER,ITEM,SUB
    }

    interface Call {

        fun call(holder: MenuSlideViewHolder)

    }


}//END CLASS

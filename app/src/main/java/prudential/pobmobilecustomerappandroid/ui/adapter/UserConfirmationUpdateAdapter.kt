package prudential.pobmobilecustomerappandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_user_confirmation_update_item.view.*
import prudential.pobmobilecustomerappandroid.R
import prudential.pobmobilecustomerappandroid.interfaces.OnRecyclerClickListener
import prudential.pobmobilecustomerappandroid.model.DataConfirmationUpdate

/**
 * Adapter class to handle menu more.
 */
class UserConfirmationUpdateAdapter(arrayM:ArrayList<DataConfirmationUpdate>,private val onRecyclerClickListener: OnRecyclerClickListener<DataConfirmationUpdate>,var isCheck1:Boolean = true, var isCheck2:Boolean = true): RecyclerView.Adapter<UserConfirmationUpdateAdapter.UsarConfirmationUpdateViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_FOOTER = 1
    private val TYPE_ITEM = 2
    private var array:ArrayList<DataConfirmationUpdate> = ArrayList();

    init {

        array.add(DataConfirmationUpdate(DataConfirmationUpdateType.HEADER))
        for(i in arrayM)
        {
            array.add(i);
        }
        array.add(DataConfirmationUpdate(DataConfirmationUpdateType.FOOTER))
    }

    /**
     * Bind all data and set to each Layout.
     * @param position Position of each item.
     */
    override fun getItemViewType(position: Int): Int {

        val info = array[position];

        if(info.type == DataConfirmationUpdateType.HEADER)
        return TYPE_HEADER

        if(info.type == DataConfirmationUpdateType.FOOTER)
        return TYPE_FOOTER

        return TYPE_ITEM
    }

    /**
     * Bind all data and set to each payment position.
     * @param holder  to handle individual item of list.
     * @param position Position of each item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsarConfirmationUpdateViewHolder{

        var v =  LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_user_confirmation_update_item, parent, false)
        var holder:UsarConfirmationUpdateViewHolder =  UsarConfirmationUpdateViewHolderItem(v);

        if(viewType == TYPE_HEADER)
        {
            v =  LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_user_confirmation_update_header, parent, false)
            holder =  UsarConfirmationUpdateViewHolderHeader(v);
        }

        if(viewType == TYPE_FOOTER)
        {
            v =  LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_user_confirmation_update_footer, parent, false)

            holder =  UsarConfirmationUpdateViewHolderFooter(v,onRecyclerClickListener,isCheck1,isCheck2);
        }

        return holder;

    }

    /**
     * Bind all data and set to each payment position.
     * @param handle individual item of list.
     * @param position Position of each item.
     */
    override fun onBindViewHolder(holder: UsarConfirmationUpdateViewHolder, position: Int) {
        val info = array[position]
        holder.bind(info);
    }

    /**
     * Get size of list.
     * @return Size of list.
     */
    override fun getItemCount(): Int = array.size

    /**Class layout generic.
     */
    open class UsarConfirmationUpdateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(info: DataConfirmationUpdate){

        }

    }//END CLASS

    /**Class layout Buttom slide.
     */
    class UsarConfirmationUpdateViewHolderItem(itemView: View) : UsarConfirmationUpdateViewHolder(itemView) {

        override fun bind(info: DataConfirmationUpdate) {
            itemView.apply {
                l1.setText(info.label1)
                f1.setText(info.hold)
                l2.setText(info.label1 + " atualizado")
                f2.setText(info.current)
            }
        }

    }//END CLASS

    /**Class layout Header.
     */
    class UsarConfirmationUpdateViewHolderHeader(itemView: View) : UsarConfirmationUpdateViewHolder(itemView) {

        override fun bind(info: DataConfirmationUpdate){

        }

    }//END CLASS

    /**Class layout Footer.
     */
    class UsarConfirmationUpdateViewHolderFooter(itemView: View,private val onRecyclerClickListener: OnRecyclerClickListener<DataConfirmationUpdate>,var isCheck1:Boolean = true, var isCheck2:Boolean = true) : UsarConfirmationUpdateViewHolder(itemView) {

        lateinit var check1:AppCompatCheckBox;
        lateinit var check2:AppCompatCheckBox;
        lateinit var btn:Button;



        override fun bind(info: DataConfirmationUpdate){

            check1  = itemView.findViewById(R.id.check1) as AppCompatCheckBox;
            check2  = itemView.findViewById(R.id.check2) as AppCompatCheckBox;
            btn     = itemView.findViewById(R.id.btnNext) as Button;

            if(!isCheck1)
                check1.visibility = View.GONE;


            if(!isCheck2)
                check2.visibility = View.GONE;

            check1.setOnCheckedChangeListener { buttonView, isChecked ->
                isValidation();
            }

            check2.setOnCheckedChangeListener { buttonView, isChecked ->
                isValidation();
            }

            if(!isCheck1 && !isCheck2)
            {
                btn.alpha = 1.0f;
                btn.isClickable = true;

            }else{

                btn.alpha = 0.5f;
                btn.isClickable = false;
            }


            btn.setOnClickListener {
                onRecyclerClickListener.onRecyclerItemClick(info, adapterPosition)
            }

        }

        fun isValidation(){

            if(isCheck1 && !isCheck2)
            {
                if(check1.isChecked)
                {
                    btn.alpha = 1.0f;
                    btn.isClickable = true;
                }else{
                    btn.alpha = 0.5f;
                    btn.isClickable = false;
                }

            }else if(isCheck2 && !isCheck1)
            {
                if(check2.isChecked)
                {
                    btn.alpha = 1.0f;
                    btn.isClickable = true;
                }else{
                    btn.alpha = 0.5f;
                    btn.isClickable = false;
                }

            }
            else if(isCheck1 && isCheck2)
            {
                if(check1.isChecked && check2.isChecked)
                {
                    btn.alpha = 1.0f;
                    btn.isClickable = true;
                }else{
                    btn.alpha = 0.5f;
                    btn.isClickable = false;
                }
            }


        }

    }//END CLASS

    enum class DataConfirmationUpdateType {
        FOOTER, HEADER,ITEM
    }

}//END CLASS

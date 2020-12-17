package prudential.pobmobilecustomerappandroid.utils

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.DialogInterface
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListView
import prudential.pobmobilecustomerappandroid.R
import java.util.*


object Dialogs {
    fun showDate(
        context: Context?, titulo: String?, call: CALL,
        onCancel: DialogInterface.OnDismissListener?
    ) {
        val c = Calendar.getInstance()
        val mYear = c[Calendar.YEAR]
        val mMonth = c[Calendar.MONTH]
        val mDay = c[Calendar.DAY_OF_MONTH]
        val dialog = DatePickerDialog(
            context!!,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val mMonth = monthOfYear + 1
                var aux = ""
                var aux2 = ""
                if (dayOfMonth < 10) aux = "0"
                if (mMonth < 10) aux2 = "0"
                call.call(
                    aux + StringBuilder().append(dayOfMonth).append("/")
                        .append(aux2 + mMonth.toString()).append("/").append(year).append(" ")
                        .toString(), 0
                )
            }, mYear, mMonth, mDay
        )
        dialog.setTitle(titulo)
        dialog.setCancelable(false)
        dialog.setOnDismissListener(onCancel)

        //DatePicker datePicker = dialog.getDatePicker();
        //datePicker.setMaxDate(c.getTimeInMillis());
        dialog.show()
    }

    fun showTime(context: Context?, call: CALL) {
        val mHour: Int
        val mMinute: Int
        val c = Calendar.getInstance()
        mHour = c[Calendar.HOUR_OF_DAY]
        mMinute = c[Calendar.MINUTE]
        val tpd = TimePickerDialog(
            context,
            OnTimeSetListener { view, hourOfDay, minute -> call.call("$hourOfDay:$minute", 0) },
            mHour,
            mMinute,
            false
        )
        tpd.show()
    }

    fun addListA(
        context: Context?,
        title: String?,
        dados: ArrayList<String>,
        call: CALL
    ) {
        val alertDialog = AlertDialog.Builder(context)
        val lv = ListView(alertDialog.context)
        alertDialog.setView(lv)

        alertDialog.setNegativeButton("Cancelar",
            DialogInterface.OnClickListener { dialog, which ->  })


        val adapter =
            ArrayAdapter(context!!, android.R.layout.simple_list_item_1, dados)
        lv.adapter = adapter
        val create = alertDialog.create()
        create.setTitle(title)
        lv.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                call.call(dados[position], position)
                create.dismiss()
            }
        create.show()
    }

    fun addList(
        context: Context?,
        title: String?,
        dados: Array<String>,
        call: CALL
    ) {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.setNegativeButton("Cancelar",
            DialogInterface.OnClickListener { dialog, which ->  })


        val lv = ListView(alertDialog.context)
        alertDialog.setView(lv)
        val adapter =
            ArrayAdapter(context!!, android.R.layout.simple_list_item_1, dados)
        lv.adapter = adapter
        val create = alertDialog.create()
        create.setTitle(title)
        lv.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                call.call(dados[position], position)
                create.dismiss()
            }
        create.show()
    }

    fun addListPosition(
        context: Context?,
        title: String?,
        dados: Array<String>,
        call: CALLP
    ) {
        val alertDialog = AlertDialog.Builder(context)
        val lv = ListView(alertDialog.context)
        alertDialog.setView(lv)
        val adapter =
            ArrayAdapter(context!!, android.R.layout.simple_list_item_1, dados)
        lv.adapter = adapter
        val create = alertDialog.create()
        create.setTitle(title)
        lv.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                call.call(dados[position], position)
                create.dismiss()
            }
        create.show()
    }

    fun addListAdapter(
        context: Context?,
        title: String?,
        adapter: BaseAdapter?,
        call: CallSelect
    ) {
        val alertDialog = AlertDialog.Builder(context)
        val lv = ListView(alertDialog.context)
        alertDialog.setView(lv)
        alertDialog.setTitle(title)
        lv.adapter = adapter
        alertDialog.setCancelable(false)
        alertDialog.setNegativeButton(
            "Cancelar"
        ) { dialog, which ->
            dialog.dismiss()
            call.onCancel()
        }
        val create = alertDialog.create()
        lv.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                create.dismiss()
                call.onSelect(position)
            }
        create.show()
    }

    fun addYesNo(
        context: Context?,
        message: String?,
        call: CALL
    ) {
        val builder = AlertDialog.Builder(context)
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> call.call("1", 1)
                    DialogInterface.BUTTON_NEGATIVE -> {
                        call.call("2", 2)
                        dialog.dismiss()
                    }
                }
            }
        builder.setMessage(message).setPositiveButton("Sim", dialogClickListener).setNegativeButton(
            "Não",
            dialogClickListener
        )
        builder.show()
    }

    fun add(context: Context?, message: String?) {
        val builder = AlertDialog.Builder(context)
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() }
        builder.setMessage(message).setPositiveButton("OK", dialogClickListener)
        builder.show()
    }

    fun addOk(context: Context?, message: String?, call: CALL) {
        val builder = AlertDialog.Builder(context)
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                call.call("", 0)
                dialog.dismiss()
            }
        builder.setMessage(message).setPositiveButton("OK", dialogClickListener)
        builder.show()
    }

    interface CALL {
        fun call(value: String?, position: Int)
    }

    interface CALLP {
        fun call(value: String?, p: Int)
    }

    interface CallSelect {
        fun onSelect(position: Int)
        fun onCancel()
    }
} // END CLASS


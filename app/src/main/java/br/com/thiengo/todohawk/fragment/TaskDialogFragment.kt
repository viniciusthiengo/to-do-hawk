package br.com.thiengo.todohawk.fragment

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import br.com.thiengo.todohawk.MainActivity
import br.com.thiengo.todohawk.R
import br.com.thiengo.todohawk.domain.ToDo
import kotlinx.android.synthetic.main.fragment_dialog_task.*
import java.util.*


class TaskDialogFragment :
        DialogFragment(),
        View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    companion object {
        val KEY = "task_dialog_fragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater!!.inflate(R.layout.fragment_dialog_task, null, false)
    }

    override fun onResume() {
        super.onResume()
        bt_create_task.setOnClickListener(this)
        sp_months.setOnItemSelectedListener(this)
    }

    override fun onClick(p0: View?) {
        val calendar = Calendar.getInstance()
        calendar.set(
            getSelectedYear(),
            sp_months.selectedItemPosition + 1,
            sp_days.selectedItemPosition + 1,
            0,
            0,
            0
        )

        val toDo = ToDo(
            calendar.timeInMillis,
            et_task.text.toString(),
            sp_duration.selectedItemPosition,
            sp_priority.selectedItemPosition
        )

        (activity as MainActivity).addToRecycler( toDo )
        dismiss()
    }

    private fun getSelectedYear() = (sp_years.selectedView as TextView).text.toString().toInt()

    override fun onItemSelected(
            parentView: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long) {

        var arrayDays = getArrayDaysResource( position )
        val adapter = ArrayAdapter.createFromResource(
            activity,
            arrayDays,
            android.R.layout.simple_spinner_item )

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        sp_days.setAdapter(adapter)
    }

    private fun getArrayDaysResource( month: Int )
        = if( month in arrayOf(0,2,4,6,7,9,11) ){
            R.array.days_31
        }
        else if( month in arrayOf(3,5,8,10) ){
            R.array.days_30
        }
        else{
            if( isLeapYear( getSelectedYear() ) ){
                R.array.days_29
            }
            else{
                R.array.days_28
            }
        }

    private fun isLeapYear(year: Int)
        = if (year % 4 == 0) {
            if (year % 100 == 0) {
                year % 400 == 0
            }
            else{
                true
            }
        }
        else{
            false
        }

    override fun onNothingSelected(p0: AdapterView<*>?) {}
}

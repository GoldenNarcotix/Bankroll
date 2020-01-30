package fr.esimed.bankroll

import android.widget.DatePicker
import java.util.*

object AndroidTools {

    fun getDateFromDatePicker(datePicker: DatePicker): Date {
        val day = datePicker.dayOfMonth
        val month = datePicker.month
        val year = datePicker.year
        val calendar = Calendar.getInstance()
        calendar[year, month] = day
        return calendar.time
    }

    fun setDateToDatePicker(datePicker: DatePicker, date: Date) {
        val c = Calendar.getInstance()
        c.time = date
        datePicker.init(
            c[Calendar.YEAR],
            c[Calendar.MONTH],
            c[Calendar.DAY_OF_MONTH],
            null
        )
    }
}
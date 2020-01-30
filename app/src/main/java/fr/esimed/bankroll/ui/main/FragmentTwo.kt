package fr.esimed.bankroll.ui.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.bankroll.Bank.BankDatabase
import fr.esimed.bankroll.Bank.OperationAdapter
import fr.esimed.bankroll.R
import java.text.SimpleDateFormat
import java.util.*

class FragmentTwo: Fragment() {

    private lateinit var pageViewModel: PageViewModel
    var formate = SimpleDateFormat("dd MMM, yyyy",Locale.FRANCE)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_two, container, false)

        //Calendrier

        val now = Calendar.getInstance()
        val datePicker =
            this.context?.let { it1 ->
                DatePickerDialog(
                    it1, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        val selectedDate = Calendar.getInstance()
                        selectedDate.set(Calendar.YEAR,year)
                        selectedDate.set(Calendar.MONTH,month)
                        selectedDate.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                        val date = formate.format(selectedDate.time)
                        root.findViewById<TextView>(R.id.txtDate).text = date
                        val db = BankDatabase.getDatabase(this.context!!)
                        val operationDAO = db.bankDAO()
                        selectedDate.set(Calendar.DAY_OF_MONTH, 1)
                        val dateStart = selectedDate.time
                        selectedDate.add(Calendar.MONTH, 1)
                        selectedDate.set(Calendar.DAY_OF_MONTH, 0)
                        val dateEnd = selectedDate.time
                        root.findViewById<RecyclerView>(R.id.lstOperations).adapter =
                            OperationAdapter(
                                it1,
                                operationDAO,
                                dateStart,
                                dateEnd
                            )
                        somme = operationDAO.getSumByMonth(dateStart, dateEnd)
                        sommeRapro = operationDAO.getRapproSumByMonth(dateStart, dateEnd)
                        sommeNeg = operationDAO.getNegativeSumByMonth(dateStart, dateEnd)
                        sommeNegRappro = operationDAO.getRapproNegativeSumByMonth(dateStart, dateEnd)
                        sommePos = operationDAO.getPositiveSumByMonth(dateStart, dateEnd)
                        sommePosRappro = operationDAO.getRapproPositiveSumByMonth(dateStart, dateEnd)
                    },
                    now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
            }


        root.findViewById<TextView>(R.id.txtDate).text = formate.format(now.time)
        root.findViewById<TextView>(R.id.txtDate).setOnClickListener {
            datePicker?.show()
        }

        val db = BankDatabase.getDatabase(this.context!!)
        val operationDAO = db.bankDAO()

        now.set(Calendar.DAY_OF_MONTH, 1)
        val dateStart = now.time
        now.add(Calendar.MONTH, 1)
        now.set(Calendar.DAY_OF_MONTH, -1)
        val dateEnd = now.time
        somme = operationDAO.getSumByMonth(dateStart, dateEnd)
        sommeNeg = operationDAO.getNegativeSumByMonth(dateStart, dateEnd)
        sommePos = operationDAO.getPositiveSumByMonth(dateStart, dateEnd)

        root.findViewById<RecyclerView>(R.id.lstOperations).layoutManager = LinearLayoutManager(this.context)
        root.findViewById<RecyclerView>(R.id.lstOperations).adapter =
            this.context?.let {
                OperationAdapter(
                    it,
                    operationDAO,
                    dateStart,
                    dateEnd
                )
            }


        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"
        var somme = 0f
        var sommeNeg = 0f
        var sommePos = 0f
        var sommeRapro = 0f
        var sommeNegRappro = 0f
        var sommePosRappro = 0f

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }


}
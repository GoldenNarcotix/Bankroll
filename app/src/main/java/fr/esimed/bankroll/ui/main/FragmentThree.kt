package fr.esimed.bankroll.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.bankroll.Bank.BankDatabase
import fr.esimed.bankroll.Bank.OperationRapproAdapter
import fr.esimed.bankroll.R

class FragmentThree : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var etatRapprochement = false
    var difference:Float = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 3)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_three, container, false)

        val db = BankDatabase.getDatabase(this.context!!)
        val operationDAO = db.bankDAO()

        root.findViewById<Button>(R.id.btnStartRapp).setOnClickListener {
            if(root.findViewById<EditText>(R.id.edtSommeCompte).text.isNotEmpty())
            {
                difference = root.findViewById<EditText>(R.id.edtSommeCompte).text.toString().toFloat() - operationDAO.getRapproSumSince()
                root.findViewById<TextView>(R.id.edtSoldeAtteindre).text = difference.toString()
                root.findViewById<Button>(R.id.btnValidateRapp).visibility = 1
            }
            else
            {
                val toast = Toast.makeText(this.context,"Veuillez entrez votre relevé de compte !", Toast.LENGTH_LONG)
                toast.show()
            }
        }

        root.findViewById<Button>(R.id.btnValidateRapp).setOnClickListener {
            if (!difference.equals(0f))
            {
                val toast = Toast.makeText(this.context,
                    "Impossible de valider le rapprochement tant que la différence n'est pas 0 !",
                    Toast.LENGTH_LONG)
                toast.show()
            }
        }

        root.findViewById<RecyclerView>(R.id.lstOperationsRappro).layoutManager = LinearLayoutManager(this.context)
        root.findViewById<RecyclerView>(R.id.lstOperationsRappro).adapter =
            this.context?.let { OperationRapproAdapter(it, operationDAO, this) }

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

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

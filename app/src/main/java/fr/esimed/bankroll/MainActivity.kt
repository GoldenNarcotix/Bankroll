package fr.esimed.bankroll

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import fr.esimed.bankroll.Bank.BankDatabase
import fr.esimed.bankroll.Bank.Operation
import fr.esimed.bankroll.Bank.OperationAdapter
import fr.esimed.bankroll.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.fragment_two.*
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val db = BankDatabase.getDatabase(this)
        val operationDAO = db.bankDAO()


        fab.setOnClickListener { view ->
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.operation_add)
            dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }

            dialog.findViewById<Button>(R.id.btnValidate).setOnClickListener {
                if(dialog.findViewById<EditText>(R.id.edtAddSomme).text.isEmpty() ||
                    dialog.findViewById<EditText>(R.id.edtAddTiers).text.isEmpty())
                {
                    val toast = Toast.makeText(this,"Veuillez entrez des informations valides !",Toast.LENGTH_LONG)
                    toast.show()
                }
                else
                {
                    val tiersOpe:String = dialog.findViewById<EditText>(R.id.edtAddTiers).text.toString()
                    val sommeTemp:String = dialog.findViewById<EditText>(R.id.edtAddSomme).text.toString()
                    val somme:Float = sommeTemp.toFloat()
                    val date:Date = AndroidTools.getDateFromDatePicker(dialog.findViewById(R.id.datBirth) as DatePicker)
                    val moyenPaiementID:Int = dialog.findViewById<RadioGroup>(R.id.lstChoixMoyPay).checkedRadioButtonId
                    var moyenPaiement = ""

                    when (moyenPaiementID)
                    {
                        R.id.CB -> moyenPaiement = "CB"
                        R.id.Cheque -> moyenPaiement = "Cheque"
                        R.id.Virement -> moyenPaiement = "Virement"
                    }
                    val etatRapprochement:Boolean = dialog.findViewById<Switch>(R.id.choixRappro).isChecked

                    val operation = Operation(
                        somme = somme,
                        rapprocher = etatRapprochement,
                        tiers = tiersOpe,
                        moyen_paiement = moyenPaiement,
                        date = date,
                        tempRappro = false
                    )

                    operationDAO.insert(operation)
                    (lstOperations.adapter as OperationAdapter).notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
            dialog.show()
        }
    }
}
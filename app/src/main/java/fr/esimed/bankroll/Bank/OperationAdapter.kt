package fr.esimed.bankroll.Bank

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.bankroll.AndroidTools
import fr.esimed.bankroll.R
import kotlinx.android.synthetic.main.operation_edit.*
import java.text.SimpleDateFormat
import java.util.*

class OperationAdapter(private val context: Context,
                       private val bankDAO: BankDAO,
                       private var dateStart:Date,
                       private var dateEnd:Date):RecyclerView.Adapter<OperationViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        return OperationViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.operation_cell,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return bankDAO.countByMonth(dateStart, dateEnd)
    }

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {

        val operation = bankDAO.getByPositionByMonth(position, dateStart, dateEnd) ?: return
        val format = SimpleDateFormat.getDateInstance()
        val sommeTotale = bankDAO.getSumSince()
        holder.txtTiers.text = operation.tiers
        holder.txtDateOpe.text = format.format(operation.date)
        holder.txtMoyPay.text = operation.moyen_paiement
        if(operation.rapprocher)
        {
            holder.txtRappro.text = context.getString(R.string.rapprocher)
        }
        else
        {
            holder.txtRappro.text = context.getString(R.string.non_rapprocher)
        }
        holder.txtSomme.text = operation.somme.toString()

        holder.btnDelete.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this.context)
            alertDialog.setTitle("Êtes vous sur de vouloir supprimer l'opération ?")
            alertDialog.setPositiveButton("Supprimer", DialogInterface.OnClickListener { dialog, which ->
                bankDAO.delete(operation)
                notifyDataSetChanged()
            })

            alertDialog.create()
            alertDialog.show()
        }

        holder.btnEdit.setOnClickListener {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.operation_edit)
            dialog.edtEditSomme.setText(operation.somme.toString())
            dialog.edtEditTiers.setText(operation.tiers)
            dialog.choixEditRappro.isChecked = operation.rapprocher
            AndroidTools.setDateToDatePicker(dialog.datBirthEdit, operation.date)
            when(operation.moyen_paiement)
            {
                "CB" -> dialog.lstEditChoixMoyPay.check(R.id.EditCB)
                "Cheque" -> dialog.lstEditChoixMoyPay.check(R.id.EditCheque)
                "Virement" -> dialog.lstEditChoixMoyPay.check(R.id.EditCheque)
            }


            dialog.findViewById<Button>(R.id.btnEditRename).setOnClickListener {
                operation.tiers = dialog.edtEditTiers.text.toString()
                operation.somme = dialog.edtEditSomme.text.toString().toFloat()
                operation.date = AndroidTools.getDateFromDatePicker(
                    dialog.findViewById(R.id.datBirthEdit) as DatePicker
                )
                operation.rapprocher = dialog.choixEditRappro.isChecked
                val moyenPaiementID = dialog.lstEditChoixMoyPay.checkedRadioButtonId
                when (moyenPaiementID)
                {
                    R.id.EditCB -> operation.moyen_paiement = "CB"
                    R.id.EditCheque -> operation.moyen_paiement = "Cheque"
                    R.id.EditVirement -> operation.moyen_paiement = "Virement"

                }

                bankDAO.update(operation)
                notifyDataSetChanged()
                dialog.dismiss()
            }

            dialog.findViewById<Button>(R.id.btnEditCancel).setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }


        if(holder.txtSomme.text.toString().toFloat() > 0)
        {
            holder.txtSomme.setTextColor(Color.parseColor("#72BF47"))
        }
        else
        {
            holder.txtSomme.setTextColor(Color.parseColor("#D85040"))
        }

        holder.txtSomme.text = holder.txtSomme.text as String + "0€"

    }
}
package fr.esimed.bankroll.Bank

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.bankroll.R
import fr.esimed.bankroll.ui.main.FragmentThree
import java.text.SimpleDateFormat

class OperationRapproAdapter(private val context: Context, private val bankDAO: BankDAO,
                             private val fragmentThree: FragmentThree): RecyclerView.Adapter<OperationViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        return OperationViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.operation_rappro_cell,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return bankDAO.countUnRapproched()
    }

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        val db = BankDatabase.getDatabase(this.context!!)
        val operationDAO = db.bankDAO()
        val operation = bankDAO.getByPositionUnRapproched(position) ?: return
        val format = SimpleDateFormat.getDateInstance()
        val sommeTotale = bankDAO.getSumSince()
        var sommeOperation:Float
        holder.txtTiers.text = operation.tiers
        holder.txtTiers.setTextColor(ContextCompat.getColor(context,R.color.textRedColorDesign))
        holder.txtDateOpe.text = format.format(operation.date)
        holder.txtDateOpe.setTextColor(ContextCompat.getColor(context,R.color.textRedColorDesign))
        holder.txtMoyPay.text = operation.moyen_paiement
        holder.txtMoyPay.setTextColor(ContextCompat.getColor(context,R.color.textRedColorDesign))
        holder.txtRappro.text = context.getString(R.string.non_rapprocher)
        holder.txtRappro.setTextColor(ContextCompat.getColor(context,R.color.textRedColorDesign))
        holder.txtSomme.text = operation.somme.toString()

        holder.rapproCell.setOnClickListener {
            if(operation.tempRappro)
            {
                holder.cardViewRappro.setCardBackgroundColor(ContextCompat.getColor(context,R.color.selectedOperation))
                holder.txtTiers.setTextColor(ContextCompat.getColor(context,R.color.textWhiteColor))
                holder.txtDateOpe.setTextColor(ContextCompat.getColor(context,R.color.textWhiteColor))
                holder.txtMoyPay.setTextColor(ContextCompat.getColor(context,R.color.textWhiteColor))
                holder.txtRappro.setTextColor(ContextCompat.getColor(context,R.color.textWhiteColor))
                holder.txtRappro.text = context.getString(R.string.temp_rappro)
                operation.tempRappro = false
                sommeOperation = operationDAO.getSumByPosition(position)
                fragmentThree.difference = fragmentThree.difference - 23
                print(sommeOperation)
                print(fragmentThree.difference)
            }
            else
            {
                holder.cardViewRappro.setCardBackgroundColor(Color.WHITE)
                holder.txtTiers.setTextColor(ContextCompat.getColor(context,R.color.textRedColorDesign))
                holder.txtDateOpe.setTextColor(ContextCompat.getColor(context,R.color.textRedColorDesign))
                holder.txtMoyPay.setTextColor(ContextCompat.getColor(context,R.color.textRedColorDesign))
                holder.txtRappro.setTextColor(ContextCompat.getColor(context,R.color.textRedColorDesign))
                holder.txtRappro.text = context.getString(R.string.non_rapprocher)
                operation.tempRappro = true
            }
            }

        if(holder.txtSomme.text.toString().toFloat() > 0)
        {
            holder.txtSomme.setTextColor(Color.parseColor("#72BF47"))
        }
        else
        {
            holder.txtSomme.setTextColor(Color.parseColor("#EE7C54"))
        }

        holder.txtSomme.text = holder.txtSomme.text as String + "0€"

    }
}
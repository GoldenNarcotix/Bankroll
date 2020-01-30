package fr.esimed.bankroll.Bank

import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.esimed.bankroll.R

class OperationViewHolder(row:View):RecyclerView.ViewHolder(row)
{
    val txtMoyPay = row.findViewById<TextView>(R.id.txtMoyPay)
    val txtTiers = row.findViewById<TextView>(R.id.txtTiersOpe)
    val txtDateOpe= row.findViewById<TextView>(R.id.txtDateOpe)
    val txtRappro = row.findViewById<TextView>(R.id.txtRapprOpe)
    val txtSomme = row.findViewById<TextView>(R.id.txtSommeOpe)
    val btnDelete = row.findViewById<ImageButton>(R.id.btnDelete)
    val btnEdit = row.findViewById<ImageButton>(R.id.btnEdit)
    val rapproCell = row.findViewById<LinearLayout>(R.id.operationRappro)
    val cardViewRappro = row.findViewById<CardView>(R.id.cardViewRappro)
    val cardView = row.findViewById<CardView>(R.id.cardView)
}
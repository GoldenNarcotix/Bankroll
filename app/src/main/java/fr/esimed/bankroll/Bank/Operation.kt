package fr.esimed.bankroll.Bank

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Operation (@PrimaryKey(autoGenerate = true) val op_id:Long? = null,
                      var somme:Float,
                      var rapprocher:Boolean,
                      var date: Date,
                      var moyen_paiement:String,
                      var tempRappro:Boolean,
                      var tiers:String)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Operation

        if (op_id != other.op_id) return false

        return true
    }

    override fun hashCode(): Int {
        return op_id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Operation(op_id=$op_id, somme=$somme, rapprocher=$rapprocher, " +
                "date=$date, moyen_paiement='$moyen_paiement', " +
                "tempRappro='$tempRappro' tiers='$tiers')"
    }


}
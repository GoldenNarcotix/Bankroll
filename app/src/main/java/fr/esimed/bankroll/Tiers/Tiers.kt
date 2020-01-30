package fr.esimed.bankroll.Tiers

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Tiers (@PrimaryKey(autoGenerate = true) val tiers_id:Long? = null, var tiers:String)
{

}
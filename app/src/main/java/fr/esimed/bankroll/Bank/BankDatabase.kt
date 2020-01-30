package fr.esimed.bankroll.Bank

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.esimed.bankroll.DateConverter

@Database(entities = [Operation::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class BankDatabase: RoomDatabase()
{
    abstract fun bankDAO(): BankDAO

    companion object {
        var INSTANCE: BankDatabase? = null
        fun getDatabase(context: Context): BankDatabase { if (INSTANCE == null) {
            INSTANCE = Room
                .databaseBuilder(context, BankDatabase::class.java, "bank.db") .allowMainThreadQueries()
                .build()
        }
            return INSTANCE!! }
    }

}
package fr.esimed.bankroll.Bank

import androidx.room.*
import java.util.*

@Dao
interface BankDAO
{
    @Query("select * from operation order by date")
    fun getAllOperations():List<Operation>

    @Query("select count(*) from operation where date between :dateStart and :dateEnd")
    fun countByMonth(dateStart: Date, dateEnd: Date): Int

    @Query("select count(*) from operation")
    fun count(): Int

    @Query("select count(*) from operation where not rapprocher ")
    fun countUnRapproched(): Int

    @Query("select * from operation order by date desc limit 1 offset :position")
    fun getByPosition(position: Int): Operation?

    @Query("select * from operation where not rapprocher order by date desc limit 1 offset :position")
    fun getByPositionUnRapproched(position: Int): Operation?

    @Query("select * from operation where date between :dateStart and :dateEnd order by date desc limit 1 offset :position")
    fun getByPositionByMonth(position: Int, dateStart: Date, dateEnd: Date): Operation?

    @Query("select somme from operation  limit 1 offset :position")
    fun getSumByPosition(position: Int): Float

    @Query("select sum(somme) from operation")
    fun getSumSince(): Float

    @Query("select sum(somme) from operation where date between :dateStart and :dateEnd")
    fun getSumByMonth(dateStart: Date, dateEnd: Date): Float

    @Query("select sum(somme) from operation where date between :dateStart and :dateEnd and not rapprocher")
    fun getRapproSumByMonth(dateStart: Date, dateEnd: Date): Float

    @Query("select sum(somme) from operation where not rapprocher")
    fun getRapproSumSince(): Float

    @Query("select sum(somme) from operation where date between :dateStart and :dateEnd and somme > 0")
    fun getPositiveSumByMonth(dateStart: Date, dateEnd: Date): Float

    @Query("select sum(somme) from operation where date between :dateStart and :dateEnd and somme > 0 and not rapprocher")
    fun getRapproPositiveSumByMonth(dateStart: Date, dateEnd: Date): Float

    @Query("select sum(somme) from operation where date between :dateStart and :dateEnd and somme < 0")
    fun getNegativeSumByMonth(dateStart: Date, dateEnd: Date): Float

    @Query("select sum(somme) from operation where date between :dateStart and :dateEnd and somme < 0 and not rapprocher")
    fun getRapproNegativeSumByMonth(dateStart: Date, dateEnd: Date): Float

    @Query("select sum(somme) from operation where somme < 0")
    fun getNegativeSumSince(): Float

    @Query("select sum(somme) from operation where somme > 0")
    fun getPositiveSumSince(): Float

    @Query("select * from operation where date between :dateStart and :dateEnd")
    fun getOperationByMonth(dateStart: Date, dateEnd: Date): Float

    @Insert
    fun insert(operation: Operation): Long

    @Update
    fun update(operation: Operation)

    @Delete
    fun delete(operation: Operation)
}
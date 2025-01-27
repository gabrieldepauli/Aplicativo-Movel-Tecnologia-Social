package edu.ifsp.com.br.EcoRec.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter

@Dao
interface RecycleCenterDao {
    @Insert
    suspend fun insertRecycleCenter(center:RecycleCenter)

    @Query ("SELECT * from recycle_center")
    suspend fun getAllRecycleCenters():List<RecycleCenter>

    @Query("SELECT * from recycle_center where id = :id")
    suspend fun getById(id:Int):RecycleCenter

    @Query("DELETE FROM recycle_center WHERE id = :id")
    suspend fun deleteById(id: Int)
}
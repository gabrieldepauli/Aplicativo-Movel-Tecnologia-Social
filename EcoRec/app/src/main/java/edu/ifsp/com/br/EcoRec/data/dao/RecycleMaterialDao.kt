package edu.ifsp.com.br.EcoRec.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial

@Dao
interface RecycleMaterialDao {
    @Insert
    suspend fun insertRecycleMaterial(center: RecycleMaterial): Long

    @Query("SELECT * from recycle_material")
    suspend fun getAllRecycleMaterials():List<RecycleMaterial>

    @Query("SELECT * from recycle_material where id = :id")
    suspend fun getById(id:Int): RecycleMaterial

    @Query("DELETE FROM recycle_material WHERE id = :id")
    suspend fun deleteById(id: Int): Int

    @Query("UPDATE recycle_material SET name = :nome WHERE id = :id")
    suspend fun updateRecycleMaterial(id: Int, nome: String): Int
}
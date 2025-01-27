package edu.ifsp.com.br.EcoRec.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenterMaterial
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial

@Dao
interface RecycleCenterMaterialDao {
    @Insert
    suspend fun insertRelation(recycleCenterMaterial:RecycleCenterMaterial)

    @Query("DELETE FROM recycle_center_material where recycleCenterId = :idCenter and recycleMaterialId = :idMaterial")
    suspend fun deleteRelation(idCenter:Int, idMaterial:Int)

    @Query("SELECT * FROM recycle_center WHERE id IN (SELECT recycleCenterId FROM recycle_center_material WHERE recycleMaterialId = :idMaterial)")
    suspend fun getCenterByMaterial(idMaterial: Int):List<RecycleCenter>


    @Query("SELECT * FROM recycle_center WHERE id IN (SELECT recycleCenterId FROM recycle_center_material WHERE recycleMaterialId IN( :idMaterials))")
    suspend fun getCenterByMaterials(idMaterials:IntArray):List<RecycleCenter>

    @Query("SELECT * FROM RECYCLE_MATERIAL WHERE id IN (SELECT recycleMaterialId FROM recycle_center_material where recycleCenterId = :idCenter)")
    suspend fun getMaterialByCenter(idCenter:Int):List<RecycleMaterial>


}
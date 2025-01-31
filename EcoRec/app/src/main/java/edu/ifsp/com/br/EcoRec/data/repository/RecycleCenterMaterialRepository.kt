package edu.ifsp.com.br.EcoRec.data.repository

import android.content.Context
import edu.ifsp.com.br.EcoRec.data.dao.database.AppDatabase
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenterMaterial
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial

class RecycleCenterMaterialRepository(context: Context) {

    private val database = AppDatabase.getInstance(context)
    private val dao = database.recycleCenterMaterialDao()

    suspend fun insertRelation(relation: RecycleCenterMaterial): Boolean {
        return dao.insertRelation(relation) > 0
    }

    suspend fun deleteRelation(idCenter: Int, idMaterial: Int): Boolean {
        return dao.deleteRelation(idCenter, idMaterial) > 0
    }

    suspend fun getAllRelations(): List<RecycleCenterMaterial>{
        return dao.getAllRelations()
    }

    suspend fun getCentersByMaterial(idMaterial: Int): List<RecycleCenter> {
        return dao.getCenterByMaterial(idMaterial)
    }

    suspend fun getCentersByMaterials(idMaterials: IntArray): List<RecycleCenter> {
        return dao.getCenterByMaterials(idMaterials)
    }

    suspend fun getMaterialsByCenter(idCenter: Int): List<RecycleMaterial> {
        return dao.getMaterialByCenter(idCenter)
    }

}
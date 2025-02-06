package edu.ifsp.com.br.EcoRec.data.repository

import android.content.Context
import edu.ifsp.com.br.EcoRec.data.dao.database.AppDatabase
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial

class RecycleMaterialRepository(context: Context) {

    private val database = AppDatabase.getInstance(context)
    private val dao = database.recycleMaterialDao()

    suspend fun insertRecycleMaterial(material: RecycleMaterial): Boolean {
        return dao.insertRecycleMaterial(material) > 0
    }

    suspend fun getAllRecycleMaterials(): List<RecycleMaterial> {
        return dao.getAllRecycleMaterials()
    }

    suspend fun getRecycleMaterialById(id: Int): RecycleMaterial {
        return dao.getById(id)
    }

    suspend fun deleteRecycleMaterialById(id: Int): Boolean {
        return dao.deleteById(id) > 0
    }

    suspend fun updateRecycleMaterial(id: Int, nome: String): Boolean {
        return dao.updateRecycleMaterial(id, nome) > 0
    }
}
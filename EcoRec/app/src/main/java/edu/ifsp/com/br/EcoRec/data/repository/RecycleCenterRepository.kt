package edu.ifsp.com.br.EcoRec.data.repository

import android.content.Context
import edu.ifsp.com.br.EcoRec.data.dao.database.AppDatabase
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter

class RecycleCenterRepository(context: Context) {

    private val database = AppDatabase.getInstance(context)
    private val dao = database.recycleCenterDao()

    suspend fun insertRecycleCenter(center: RecycleCenter): Boolean {
        return dao.insertRecycleCenter(center) > 0
    }

    suspend fun getAllRecycleCenters(): List<RecycleCenter> {
        return dao.getAllRecycleCenters()
    }

    suspend fun getRecycleCenterById(id: Int): RecycleCenter {
        return dao.getById(id)
    }

    suspend fun deleteRecycleCenterById(id: Int): Boolean {
        return dao.deleteById(id) > 0
    }

}
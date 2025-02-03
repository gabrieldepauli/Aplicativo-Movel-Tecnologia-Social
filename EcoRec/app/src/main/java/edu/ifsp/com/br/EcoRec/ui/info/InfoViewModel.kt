package edu.ifsp.com.br.EcoRec.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter

import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterMaterialRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleMaterialRepository

class InfoViewModel(private val repository: RecycleCenterMaterialRepository, private val centerRepo: RecycleCenterRepository, private val materialRepo: RecycleMaterialRepository) : ViewModel() {

    suspend fun getMateriaisDoCentro(centroId: Int): List<RecycleMaterial> {
        return repository.getMaterialsByCenter(centroId)
    }
    suspend fun getCentros(): List<RecycleCenter>{
        return centerRepo.getAllRecycleCenters()
    }
}
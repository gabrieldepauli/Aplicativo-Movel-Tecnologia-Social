package edu.ifsp.com.br.EcoRec.ui.registerRelation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenterMaterial
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterMaterialRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleMaterialRepository
import kotlinx.coroutines.launch

class RelationViewModel(application: Application) : AndroidViewModel(application) {

    private val centerMaterialRepository = RecycleCenterMaterialRepository(application)
    private val centerRepository = RecycleCenterRepository(application)
    private val materialRepository = RecycleMaterialRepository(application)

    private val _centers = MutableLiveData<List<RecycleCenter>>()
    val centers: LiveData<List<RecycleCenter>> = _centers

    fun loadCenters() {
        viewModelScope.launch {
            _centers.value = centerRepository.getAllRecycleCenters()
        }
    }

    fun getAllMaterials(): LiveData<List<RecycleMaterial>> {
        val materialsLiveData = MutableLiveData<List<RecycleMaterial>>()
        viewModelScope.launch {
            val materials = materialRepository.getAllRecycleMaterials()
            materialsLiveData.postValue(materials)
        }
        return materialsLiveData
    }

    fun getMaterialsForCenter(centerId: Int): LiveData<List<RecycleMaterial>> {
        val materialsLiveData = MutableLiveData<List<RecycleMaterial>>()
        viewModelScope.launch {
            val materials = centerMaterialRepository.getMaterialsByCenter(centerId)
            materialsLiveData.postValue(materials)
        }
        return materialsLiveData
    }

    suspend fun searchByCenterName(centerName: String) {
        val searchedCenters = centerRepository.searchCentersByName(centerName)
        _centers.postValue(searchedCenters)
    }

    suspend fun addRelation(centerId: Int, materialId: Int) {
        val relation = RecycleCenterMaterial(centerId, materialId)
        centerMaterialRepository.insertRelation(relation)
    }

    suspend fun removeRelation(centerId: Int, materialId: Int) {
        centerMaterialRepository.deleteRelation(centerId, materialId)
    }
}
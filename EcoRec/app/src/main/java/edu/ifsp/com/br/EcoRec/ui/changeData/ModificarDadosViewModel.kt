package edu.ifsp.com.br.EcoRec.ui.changeData

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleMaterialRepository
import kotlinx.coroutines.launch

class ModificarDadosViewModel(application: Application) : AndroidViewModel(application) {

    private val centerRepository = RecycleCenterRepository(application)
    private val materialRepository = RecycleMaterialRepository(application)

    private val _centers = MutableLiveData<List<RecycleCenter>>()
    val centers: LiveData<List<RecycleCenter>> get() = _centers

    private val _materials = MutableLiveData<List<RecycleMaterial>>()
    val materials: LiveData<List<RecycleMaterial>> get() = _materials

    private val _deletedItem = MutableLiveData<Boolean>()
    val deletedItem: LiveData<Boolean> get() = _deletedItem

    fun loadCenters() {
        viewModelScope.launch {
            _centers.value = centerRepository.getAllRecycleCenters()
        }
    }

    fun loadMaterials() {
        viewModelScope.launch {
            _materials.value = materialRepository.getAllRecycleMaterials()
        }
    }

    fun deleteMaterial(id: Int) {
        viewModelScope.launch {
            val result = materialRepository.deleteRecycleMaterialById(id)
            _deletedItem.postValue(result)
            loadMaterials()
        }
    }

    fun deleteCenter(id: Int) {
        viewModelScope.launch {
            val result = centerRepository.deleteRecycleCenterById(id)
            _deletedItem.postValue(result)
            loadCenters()
        }
    }
}
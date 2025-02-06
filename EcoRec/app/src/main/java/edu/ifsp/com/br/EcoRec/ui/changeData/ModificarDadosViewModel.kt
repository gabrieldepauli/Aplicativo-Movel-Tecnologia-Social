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

    private var toDeleteCenter: RecycleCenter? = null
    private var toDeleteMaterial: RecycleMaterial? = null

    private val _toDeleteC = MutableLiveData<RecycleCenter>()
    val toDeleteC: LiveData<RecycleCenter> = _toDeleteC

    private val _toDeleteM = MutableLiveData<RecycleMaterial>()
    val toDeleteM: LiveData<RecycleMaterial> = _toDeleteM

    private val _deletedItem = MutableLiveData<Boolean>()
    val deletedItem: LiveData<Boolean> get() = _deletedItem

    private val _centers = MutableLiveData<List<RecycleCenter>>()
    val centers: LiveData<List<RecycleCenter>> get() = _centers

    private val _materials = MutableLiveData<List<RecycleMaterial>>()
    val materials: LiveData<List<RecycleMaterial>> get() = _materials


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

    fun notifyDeleteMaterial(id: Int) {
        viewModelScope.launch {
            toDeleteMaterial = materialRepository.getRecycleMaterialById(id)
            if (toDeleteMaterial != null) {
                _toDeleteM.value = toDeleteMaterial!!
            }
        }
    }

    fun deleteMaterial(id: Int) {
        viewModelScope.launch {
            val result = materialRepository.deleteRecycleMaterialById(id)
            _deletedItem.postValue(result)
            loadMaterials()
        }
    }

    fun notifyDeleteCenter(id: Int) {
        viewModelScope.launch {
            toDeleteCenter = centerRepository.getRecycleCenterById(id)
            if (toDeleteCenter != null) {
                _toDeleteC.value = toDeleteCenter!!
            }
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
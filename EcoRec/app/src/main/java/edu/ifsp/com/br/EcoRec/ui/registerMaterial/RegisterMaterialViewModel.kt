package edu.ifsp.com.br.EcoRec.ui.registerMaterial

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.data.entity.RecycleMaterial
import edu.ifsp.com.br.EcoRec.data.repository.RecycleMaterialRepository
import kotlinx.coroutines.launch

class RegisterMaterialViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RecycleMaterialRepository(application)

    private val _materials = MutableLiveData<List<RecycleMaterial>>()
    val materials: LiveData<List<RecycleMaterial>> = _materials

    private val _insertResult = MutableLiveData<Boolean>()
    val insertResult: LiveData<Boolean> get() = _insertResult

    init {
        viewModelScope.launch {
            val list = repository.getAllRecycleMaterials()
            _materials.postValue(list)
        }
    }

    fun load() {
        viewModelScope.launch {
            _materials.value = repository.getAllRecycleMaterials()
        }
    }

    fun insertMaterial(nome: String) {
        viewModelScope.launch {
            val result = repository.insertRecycleMaterial(RecycleMaterial(name = nome))
            _insertResult.postValue(result)
            load()
        }
    }
}
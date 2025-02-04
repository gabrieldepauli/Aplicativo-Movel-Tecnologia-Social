package edu.ifsp.com.br.EcoRec.ui.registerRelation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenterMaterial
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterMaterialRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterRepository
import edu.ifsp.com.br.EcoRec.data.repository.RecycleMaterialRepository
import kotlinx.coroutines.launch

class RelationViewModel(application: Application) : AndroidViewModel(application) {

    private val centerMaterialRepository = RecycleCenterMaterialRepository(application)
    private val centerRepository = RecycleCenterRepository(application)
    private val materialRepository = RecycleMaterialRepository(application)

    private val _relations = MutableLiveData<List<RecycleCenterMaterial>>()
    val relations: LiveData<List<RecycleCenterMaterial>> = _relations

    private val _insertResult = MutableLiveData<Boolean>()
    val insertResult: LiveData<Boolean> get() = _insertResult

    private val _findCenter = MutableLiveData<Boolean>()
    val findCenter: LiveData<Boolean> get() = _findCenter

    private val _findMaterial = MutableLiveData<Boolean>()
    val findMaterial: LiveData<Boolean> get() = _findMaterial

    init {
        viewModelScope.launch {
            val list = centerMaterialRepository.getAllRelations()
            _relations.postValue(list)
        }
    }

    fun load() {
        viewModelScope.launch {
            _relations.value = centerMaterialRepository.getAllRelations()
        }
    }

    fun insertRelation(idCenter: Int, idMaterial: Int) {
        viewModelScope.launch {
            try {
                val centerExists = centerRepository.getRecycleCenterById(idCenter) != null
                val materialExists = materialRepository.getRecycleMaterialById(idMaterial) != null

                _findCenter.postValue(centerExists)
                _findMaterial.postValue(materialExists)

                if (!centerExists || !materialExists) {
                    _insertResult.postValue(false)
                    return@launch
                }

                val result = centerMaterialRepository.insertRelation(RecycleCenterMaterial(recycleCenterId = idCenter, recycleMaterialId = idMaterial))

                _insertResult.postValue(result)
                load()
            } catch (e: android.database.sqlite.SQLiteConstraintException) {
                _insertResult.postValue(false)
            }
        }
    }

}
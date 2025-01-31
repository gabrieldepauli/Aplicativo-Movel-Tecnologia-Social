package edu.ifsp.com.br.EcoRec.ui.registerCenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import edu.ifsp.com.br.EcoRec.data.entity.RecycleCenter
import edu.ifsp.com.br.EcoRec.data.repository.RecycleCenterRepository
import kotlinx.coroutines.launch

class RegisterCenterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RecycleCenterRepository(application)

    private val _centers = MutableLiveData<List<RecycleCenter>>()
    val centers: LiveData<List<RecycleCenter>> = _centers

    private val _insertResult = MutableLiveData<Boolean>()
    val insertResult: LiveData<Boolean> get() = _insertResult

    init {
        viewModelScope.launch {
            val list = repository.getAllRecycleCenters()
            _centers.postValue(list)
        }
    }

    fun load() {
        viewModelScope.launch {
            _centers.value = repository.getAllRecycleCenters()
        }
    }

    fun insertCenter(nome: String, endereco: String) {
        viewModelScope.launch {
            val result = repository.insertRecycleCenter(RecycleCenter(name = nome, location = endereco))
            _insertResult.postValue(result)
            load()
        }
    }
}
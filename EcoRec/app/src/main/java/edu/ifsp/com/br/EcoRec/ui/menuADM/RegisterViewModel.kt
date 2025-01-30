package edu.ifsp.com.br.EcoRec.ui.menuADM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import edu.ifsp.com.br.EcoRec.data.dataStore.DataStoreRepository
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataStoreRepository(application)

    fun logout(){
        viewModelScope.launch {
            repository.savePreferences("", "", false)
        }
    }

}
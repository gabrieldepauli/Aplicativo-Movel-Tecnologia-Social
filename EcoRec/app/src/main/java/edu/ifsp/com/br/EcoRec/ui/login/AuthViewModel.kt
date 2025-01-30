package edu.ifsp.com.br.EcoRec.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import edu.ifsp.com.br.EcoRec.data.dataStore.DataStoreRepository
import edu.ifsp.com.br.EcoRec.data.entity.Adm
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataStoreRepository(application)

    val stayLoggedPreferences: LiveData<Boolean> = repository.stayLoggedPreference.asLiveData()
    val dataPreferences: LiveData<Pair<String, String>> = repository.dataPreferences.asLiveData()

    private val _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> = _loggedIn

    fun login(username: String, password: String, stayLogged: Boolean) {
        if (Adm.authenticate(username, password)) {
            _loggedIn.value = true
            if (stayLogged)
                savePreferences(username, password, stayLogged)
            else
                savePreferences("", "", stayLogged)
        } else {
            _loggedIn.value = false
        }
    }

    private fun savePreferences(username: String, password: String, stayLogged: Boolean) {
        viewModelScope.launch {
            repository.savePreferences(username, password, stayLogged)
        }
    }
}
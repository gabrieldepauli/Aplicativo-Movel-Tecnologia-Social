package edu.ifsp.com.br.EcoRec.data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import edu.ifsp.com.br.EcoRec.util.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    object PreferencesFile {
        const val FILE_NAME = "adm_preferences"
    }

    private object PreferencesKeys {
        val STAY_LOGGED = booleanPreferencesKey("stay_logged")
        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
    }

    suspend fun savePreferences(username: String = "", password: String = "", stayLogged: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.USERNAME] = username
            preferences[PreferencesKeys.PASSWORD] = password
            preferences[PreferencesKeys.STAY_LOGGED] = stayLogged
        }
    }

    val stayLoggedPreference: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.STAY_LOGGED] ?: false
    }

    val dataPreferences: Flow<Pair<String, String>> = dataStore.data.map { preferences ->
        val username = preferences[PreferencesKeys.USERNAME] ?: ""
        val password = preferences[PreferencesKeys.PASSWORD] ?: ""
        Pair(username, password)
    }
}
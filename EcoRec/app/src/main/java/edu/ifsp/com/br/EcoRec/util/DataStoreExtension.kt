package edu.ifsp.com.br.EcoRec.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import edu.ifsp.com.br.EcoRec.data.dataStore.DataStoreRepository


val Context .dataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreRepository.PreferencesFile.FILE_NAME)
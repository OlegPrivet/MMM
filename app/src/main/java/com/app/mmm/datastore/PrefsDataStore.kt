package com.app.mmm.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PrefsDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    val pushEnableFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PrefsKeys.PUSH_ENABLED] ?: false
    }

    suspend fun pushEnabled(enable: Boolean) {
        dataStore.edit {
            it[PrefsKeys.PUSH_ENABLED] = enable
        }
    }

    private object PrefsKeys {
        val PUSH_ENABLED = booleanPreferencesKey("push_enable")
    }
}

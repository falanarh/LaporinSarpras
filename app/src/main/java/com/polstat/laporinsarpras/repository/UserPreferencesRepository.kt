package com.polstat.laporinsarpras.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


class UserPreferencesRepository (
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val TOKEN = stringPreferencesKey("user_token")
        val NAME = stringPreferencesKey("user_name")
        val EMAIL = stringPreferencesKey("user_email")
        val IS_TEKNISI = booleanPreferencesKey("user_is_teknisi")
        val IS_KOORDINATOR = booleanPreferencesKey("user_is_koordinator")
        val IS_PELAPOR = booleanPreferencesKey("user_is_pelapor")
        const val TAG = "UserPreferencesRepo"
    }

        val user: Flow<UserState> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences:", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            UserState(
                preferences[TOKEN] ?: "",
                preferences[NAME] ?: "",
                preferences[EMAIL] ?: "",
                preferences[IS_TEKNISI] ?: false,
                preferences[IS_KOORDINATOR] ?: false,
                preferences[IS_PELAPOR] ?: false
            )
        }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    suspend fun saveName(name: String) {
        dataStore.edit { preferences ->
            preferences[NAME] = name
        }
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

    suspend fun saveIsTeknisi(isTeknisi: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_TEKNISI] = isTeknisi
        }
    }

    suspend fun saveIsKoordinator(isKoordinator: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_KOORDINATOR] = isKoordinator
        }
    }

    suspend fun saveIsPelapor(isPelapor: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_PELAPOR] = isPelapor
        }
    }
}
data class UserState(
    val token: String,
    val name: String,
    val email: String,
    val isTeknisi: Boolean,
    val isKoordinator: Boolean,
    val isPelapor: Boolean
)
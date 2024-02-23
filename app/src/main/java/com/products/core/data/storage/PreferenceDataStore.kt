package com.products.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class PreferenceDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val PREFS_NAME = "app_prefs"
    }
}
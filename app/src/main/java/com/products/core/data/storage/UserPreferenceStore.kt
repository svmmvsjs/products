package com.products.core.data.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.products.arch.data.SharedPreferenceDataStore
import com.products.core.domain.model.User
import javax.inject.Inject

class UserPreferenceStore @Inject constructor(
    dataStore: DataStore<Preferences>
) : SharedPreferenceDataStore<User>(dataStore, User.serializer())
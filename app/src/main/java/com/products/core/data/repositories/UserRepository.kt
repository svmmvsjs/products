package com.products.core.data.repositories

import com.products.arch.data.Repository
import com.products.arch.extensions.mapSuccess
import com.products.core.data.network.Api
import com.products.core.data.network.dtos.toUser
import com.products.core.data.storage.UserPreferenceStore
import com.products.core.domain.model.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val api: Api,
    private val userPreferenceStore: UserPreferenceStore
) : Repository() {

    suspend fun getUser(): User {
        return api.getUser()
            .mapSuccess {
                it.data.toUser()
            }.also {
                userPreferenceStore.add(it)
            }
    }
}
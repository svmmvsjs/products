package com.products.core.data.network.responses

import com.products.core.data.network.dtos.UserDto
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val data: UserDto
)
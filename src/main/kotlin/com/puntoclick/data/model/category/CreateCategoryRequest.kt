package com.puntoclick.data.model.category

import com.puntoclick.data.model.user.UserResponse
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CreateCategoryRequest(
    val name: String
)
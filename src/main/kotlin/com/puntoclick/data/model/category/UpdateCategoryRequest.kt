package com.puntoclick.data.model.category

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class UpdateCategoryRequest(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String
)
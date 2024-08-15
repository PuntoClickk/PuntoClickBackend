package com.puntoclick.data.model.store

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UpdateStoreRequest(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val location: String
)

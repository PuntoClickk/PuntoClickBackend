package com.puntoclick.data.model

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UUIDAppRequest(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID
)
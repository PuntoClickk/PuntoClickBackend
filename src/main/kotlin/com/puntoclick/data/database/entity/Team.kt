package com.puntoclick.data.database.entity

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Team(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val createdAt: Long,
    val lastUpdate: Long
)
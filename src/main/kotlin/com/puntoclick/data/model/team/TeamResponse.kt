package com.puntoclick.data.model.team

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class TeamResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
)

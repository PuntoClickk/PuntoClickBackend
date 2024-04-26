package com.puntoclick.features.team.model

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UpdateTeamRequest(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String
)
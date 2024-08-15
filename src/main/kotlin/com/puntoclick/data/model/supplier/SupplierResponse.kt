package com.puntoclick.data.model.supplier

import com.puntoclick.data.model.team.TeamResponse
import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class SupplierResponse(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val companyName: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
    //val team: TeamResponse
)
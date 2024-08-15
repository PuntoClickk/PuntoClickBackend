package com.puntoclick.data.model.supplier

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UpdateSupplierRequest(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val company: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
)

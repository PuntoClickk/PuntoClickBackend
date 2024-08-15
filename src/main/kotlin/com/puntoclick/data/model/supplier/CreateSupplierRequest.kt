package com.puntoclick.data.model.supplier

import kotlinx.serialization.Serializable

@Serializable
data class CreateSupplierRequest(
    val company: String,
    val name: String,
    val email: String,
    val phoneNumber: String,
)
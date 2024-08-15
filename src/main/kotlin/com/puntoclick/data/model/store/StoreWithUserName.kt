package com.puntoclick.data.model.store

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class StoreWithUserName(
    @Serializable(with = UUIDSerializer::class)
    val storeId: UUID,
    val storeName: String,
    val location: String,
    val userName: String
)
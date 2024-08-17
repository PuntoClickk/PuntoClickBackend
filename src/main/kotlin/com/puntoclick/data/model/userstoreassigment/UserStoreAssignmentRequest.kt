package com.puntoclick.data.model.userstoreassigment

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserStoreAssignmentRequest(
    @Serializable(with = UUIDSerializer::class)
    val workerId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val storeId: UUID
)


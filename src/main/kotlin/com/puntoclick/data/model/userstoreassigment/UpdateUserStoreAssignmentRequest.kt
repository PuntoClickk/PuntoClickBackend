package com.puntoclick.data.model.userstoreassigment

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UpdateUserStoreAssignmentRequest(
    @Serializable(with = UUIDSerializer::class)
    val assignmentId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val newWorkerId: UUID
)

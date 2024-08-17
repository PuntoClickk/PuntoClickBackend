package com.puntoclick.data.model.userstoreassigment

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserStoreAssignmentWithDetails(
    @Serializable(with = UUIDSerializer::class)
    val assignmentId: UUID,
    val workerName: String,
    val storeName: String,
    val teamName: String
)

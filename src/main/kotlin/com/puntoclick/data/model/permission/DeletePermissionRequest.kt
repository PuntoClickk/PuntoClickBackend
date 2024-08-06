package com.puntoclick.data.model.permission

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class DeletePermissionRequest(
    @Serializable(with = UUIDSerializer::class)
    val permissionId: UUID,
)

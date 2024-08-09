package com.puntoclick.data.model.store

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class CreateStoreRequest(
    val name: String,
    val location: String,
    @Serializable(with = UUIDSerializer::class)
    val teamId: UUID,
    @Serializable(with = UUIDSerializer::class)
    val userId: UUID
)


fun CreateStoreRequest.toStoreData(): StoreData {
    return StoreData(
        name = this.name,
        location = this.location,
        teamId = this.teamId,
        userId = this.userId
    )
}


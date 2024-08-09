package com.puntoclick.data.model.store

import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class CreateStoreRequest(
    val name: String,
    val location: String,
)


fun CreateStoreRequest.toStoreData(teamId: UUID, userId: UUID): StoreData {
    return StoreData(
        name = name,
        location = location,
        teamId = teamId,
        userId = userId
    )
}


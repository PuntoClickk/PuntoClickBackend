package com.puntoclick.data.model.store

import java.util.UUID

data class StoreData(
    val name: String,
    val location: String,
    val teamId: UUID,
    val userId: UUID
)

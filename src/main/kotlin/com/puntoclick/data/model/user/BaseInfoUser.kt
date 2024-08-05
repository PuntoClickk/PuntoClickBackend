package com.puntoclick.data.model.user

import com.puntoclick.data.utils.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
data class BaseInfoUser(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    val name: String,
    val lastName: String,
    val phoneNumber: String,
    val birthday: Long,
)

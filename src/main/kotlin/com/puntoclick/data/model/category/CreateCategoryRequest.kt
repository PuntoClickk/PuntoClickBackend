package com.puntoclick.data.model.category

import kotlinx.serialization.Serializable

@Serializable
data class CreateCategoryRequest(
    val name: String
)
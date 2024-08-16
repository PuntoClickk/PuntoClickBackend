package com.puntoclick.data.model.module

import kotlinx.serialization.Serializable

@Serializable
enum class ModuleType(val type: Int, val moduleName: String) {
    USERS(1,"USERS"),
    PRODUCTS(2,"PRODUCTS"),
    STORES(3,"STORES"),
    POS(3,"POS");
}
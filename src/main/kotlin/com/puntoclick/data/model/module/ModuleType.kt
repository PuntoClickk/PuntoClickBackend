package com.puntoclick.data.model.module

import kotlinx.serialization.Serializable

@Serializable
enum class ModuleType(val type: Int, val moduleName: String) {
    USERS(1,"USERS"),
    PRODUCTS(2,"PRODUCTS"),
    STORES(3,"STORES"),
    POS(3,"POS");

    companion object {

        fun fromType(type: Int): ModuleType {
            return ModuleType.entries.find { it.type == type }
                ?: throw IllegalArgumentException("No ModuleType with type: $type")
        }

        fun fromModuleName(moduleName: String): ModuleType {
            return ModuleType.entries.find { it.moduleName.equals(moduleName, ignoreCase = true) }
                ?: throw IllegalArgumentException("No ModuleType with moduleName: $moduleName")
        }
    }
}
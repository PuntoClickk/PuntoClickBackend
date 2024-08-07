package com.puntoclick.data.model

import com.puntoclick.data.model.module.ModuleType
import java.util.*

object GlobalModuleIds {
    private val moduleIdMap: MutableMap<ModuleType, UUID> = mutableMapOf()

    fun get(moduleType: ModuleType): UUID {
        return moduleIdMap[moduleType] ?: throw Exception("Module ID not found for type: $moduleType")
    }

    fun set(moduleType: ModuleType, id: UUID) {
        moduleIdMap[moduleType] = id
    }
}




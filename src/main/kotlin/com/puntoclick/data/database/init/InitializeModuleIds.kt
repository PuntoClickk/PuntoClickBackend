package com.puntoclick.data.database.init

import com.puntoclick.data.database.module.table.ModuleTable
import com.puntoclick.data.model.GlobalModuleIds
import com.puntoclick.data.model.module.ModuleType
import org.jetbrains.exposed.sql.select

fun initializeModuleIds() {
    ModuleType.entries.forEach { moduleType ->
        val moduleId = ModuleTable
            .slice(ModuleTable.uuid)
            .select { ModuleTable.name eq moduleType.moduleName }
            .map { it[ModuleTable.uuid] }
            .singleOrNull() ?: throw Exception("Module ${moduleType.moduleName} not found")
        GlobalModuleIds.set(moduleType, moduleId)
    }

}
package com.puntoclick.data.database.init

import com.puntoclick.data.database.module.table.ModuleTable
import com.puntoclick.data.model.GlobalModuleIds
import com.puntoclick.data.model.module.ModuleType

fun initializeModuleIds() {
    ModuleType.entries.forEach { moduleType ->
        val moduleId = ModuleTable
            .select(ModuleTable.uuid)
            .where { ModuleTable.name eq moduleType.moduleName }
            .map { it[ModuleTable.uuid] }
            .singleOrNull() ?: throw Exception("Module ${moduleType.moduleName} not found")
        GlobalModuleIds.set(moduleType, moduleId)
    }

}
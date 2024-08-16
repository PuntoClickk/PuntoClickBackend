package com.puntoclick.data.database.module.init

import com.puntoclick.data.database.module.table.ModuleTable
import com.puntoclick.data.model.module.ModuleType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

fun initializeModules()  {
    ModuleType.entries.forEach { module ->
        if (ModuleTable.selectAll().where { ModuleTable.name eq module.moduleName }.singleOrNull() == null) {
            ModuleTable.insert {
                it[name] = module.moduleName
            }
        }
    }
}
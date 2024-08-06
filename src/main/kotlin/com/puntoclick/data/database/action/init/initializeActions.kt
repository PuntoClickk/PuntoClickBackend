package com.puntoclick.data.database.action.init

import com.puntoclick.data.database.action.table.ActionTable
import com.puntoclick.data.model.permission.ActionType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

fun initializeActions() {
    ActionType.entries.forEach { action ->
        if (ActionTable.select { ActionTable.name eq action.name }.singleOrNull() == null) {
            ActionTable.insert {
                it[name] = action.name
            }
        }
    }
}
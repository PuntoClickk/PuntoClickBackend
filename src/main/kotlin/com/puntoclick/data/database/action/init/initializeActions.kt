package com.puntoclick.data.database.action.init

import com.puntoclick.data.database.action.table.ActionTable
import com.puntoclick.data.model.action.ActionType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

fun initializeActions() {
    ActionType.entries.forEach { action ->
        if (ActionTable.selectAll().where { ActionTable.name eq action.name }.singleOrNull() == null) {
            ActionTable.insert {
                it[name] = action.name
            }
        }
    }
}
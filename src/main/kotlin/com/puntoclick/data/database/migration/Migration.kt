package com.puntoclick.data.database.migration

import com.puntoclick.data.database.role.table.RoleTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction


fun addIsActiveColumToRole(){
    transaction {
        val stateMet = SchemaUtils.addMissingColumnsStatements(RoleTable)
        stateMet.forEach {
            exec(it)
        }
    }
}
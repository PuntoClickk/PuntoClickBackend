package com.puntoclick.data.database.migration

import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.database.user.table.UserTable
import org.jetbrains.exposed.sql.BooleanColumnType
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("unused")
fun addIsActiveColumToRole(){
    transaction {
        val stateMet = SchemaUtils.addMissingColumnsStatements(RoleTable)
        stateMet.forEach {
            exec(it)
        }
    }
}

@Suppress("unused")
fun addNewColumnsToUser(){

    transaction {
        val column = Column<Boolean>(UserTable, "isBLocked", BooleanColumnType())
        column.dropStatement().forEach {
            exec(it)
        }
    }

    transaction {

        val stateMet = SchemaUtils.addMissingColumnsStatements(UserTable)

        stateMet.forEach {
            exec(it)
        }
    }
}
package com.puntoclick.data.database

import com.puntoclick.data.database.roledaofacade.RoleTable
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {

    val database = Database.connect(
        url = environment.config.propertyOrNull("ktor.database.url")?.getString() ?: "",
        driver = environment.config.propertyOrNull("ktor.database.driver")?.getString() ?: "",
        user = environment.config.propertyOrNull("ktor.database.user")?.getString() ?: "",
        password = environment.config.propertyOrNull("ktor.database.password")?.getString() ?: ""
    )
    transaction(database) {
        SchemaUtils.create(RoleTable)
    }
}
suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }
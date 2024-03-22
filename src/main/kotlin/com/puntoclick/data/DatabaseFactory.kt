package com.puntoclick.data

import com.puntoclick.features.roles.database.RoleTable
import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun Application.init() {

        val database = Database.connect(
            url = "jdbc:postgresql://localhost:5432/puntoclick",
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
}
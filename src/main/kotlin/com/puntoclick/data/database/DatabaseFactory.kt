package com.puntoclick.data.database

import com.puntoclick.data.database.action.init.initializeActions
import com.puntoclick.data.database.action.table.ActionTable
import com.puntoclick.data.database.category.table.CategoriesTable
import com.puntoclick.data.database.init.initializeActionsIds
import com.puntoclick.data.database.init.initializeModuleIds
import com.puntoclick.data.database.init.initializeRolesIds
import com.puntoclick.data.database.invitation.table.InvitationTable
import com.puntoclick.data.database.module.init.initializeModules
import com.puntoclick.data.database.module.table.ModuleTable
import com.puntoclick.data.database.permission.table.PermissionTable
import com.puntoclick.data.database.registeruser.RegisterUserTable
import com.puntoclick.data.database.role.init.initializeRoles
import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.database.supplier.table.SupplierTable
import com.puntoclick.data.database.store.table.StoreTable
import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.database.user.table.UserTable
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {

    val database = Database.connect(
        url = environment.config.propertyOrNull("database.url")?.getString() ?: "",
        driver = environment.config.propertyOrNull("database.driver")?.getString() ?: "",
        user = environment.config.propertyOrNull("database.user")?.getString() ?: "",
        password = environment.config.propertyOrNull("database.password")?.getString() ?: ""
    )
    transaction(database) {
        SchemaUtils.create(UserTable)
        SchemaUtils.create(RoleTable)
        SchemaUtils.create(TeamTable)
        SchemaUtils.create(RegisterUserTable)
        SchemaUtils.create(InvitationTable)
        SchemaUtils.create(ActionTable)
        SchemaUtils.create(CategoriesTable)
        SchemaUtils.create(ModuleTable)
        SchemaUtils.create(PermissionTable)
        SchemaUtils.create(SupplierTable)
        SchemaUtils.create(StoreTable)
        initializeRoles()
        initializeActions()
        initializeModules()
        initializeModuleIds()
        initializeRolesIds()
        initializeActionsIds()
        //addIsActiveColumToRole()
        //addNewColumnsToUser()
        addLogger(StdOutSqlLogger)

    }
}
suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }



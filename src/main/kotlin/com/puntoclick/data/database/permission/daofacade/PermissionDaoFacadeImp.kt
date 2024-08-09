package com.puntoclick.data.database.permission.daofacade

import com.puntoclick.data.database.action.table.ActionTable
import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.module.table.ModuleTable
import com.puntoclick.data.database.permission.table.PermissionTable
import com.puntoclick.data.database.permission.table.PermissionTable.uuid
import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.model.GlobalActionIds
import com.puntoclick.data.model.GlobalModuleIds
import com.puntoclick.data.model.GlobalRoleIds
import com.puntoclick.data.model.action.ActionType
import com.puntoclick.data.model.module.ModuleType
import com.puntoclick.data.model.permission.AddPermissionResult
import com.puntoclick.data.model.permission.DeletePermissionResult
import com.puntoclick.data.model.permission.PermissionInfo
import com.puntoclick.data.model.permission.UpdatePermissionResult
import com.puntoclick.data.model.role.RoleType
import com.puntoclick.data.model.user.UserType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class PermissionDaoFacadeImp : PermissionDaoFacade {
    override suspend fun initializePermissionsForTeam(teamId: UUID) = dbQuery {

        val modules = ModuleTable.selectAll().map { it[ModuleTable.uuid] }

        RoleType.entries.forEach { role ->

            val allowedActions: List<ActionType> = when (role) {
                RoleType.ADMIN, RoleType.MANAGER -> ActionType.entries
                RoleType.WORKER, RoleType.VIEWER -> listOf(ActionType.READ)
            }

            modules.forEach { moduleId ->
                allowedActions.forEach { actionType ->

                    val roleId = RoleTable.select { RoleTable.name eq role.roleName }.single()[RoleTable.uuid]
                    val actionId =
                        ActionTable.select { ActionTable.name eq actionType.actionName }.single()[ActionTable.uuid]


                    if (PermissionTable.select {
                            (PermissionTable.role eq roleId) and
                                    (PermissionTable.module eq moduleId) and
                                    (PermissionTable.action eq actionId) and
                                    (PermissionTable.team eq teamId)
                        }.singleOrNull() == null) {

                        val result = PermissionTable.insert {
                            it[this.action] = actionId
                            it[this.role] = roleId
                            it[this.module] = moduleId
                            it[this.team] = teamId
                        }.resultedValues?.singleOrNull()
                        println(result)
                    }
                }
            }
        }
    }

    override suspend fun addPermission(
        userType: UserType,
        roleType: RoleType,
        actionType: ActionType,
        moduleType: ModuleType,
        teamId: UUID
    ): AddPermissionResult = dbQuery {
        if (userType != UserType.ADMIN) {
            return@dbQuery AddPermissionResult.UserNotAdmin
        }

        val roleId = GlobalRoleIds.get(roleType)
        val actionId = GlobalActionIds.get(actionType)
        val moduleId = GlobalModuleIds.get(moduleType)

        val exists = PermissionTable.select {
            (PermissionTable.role eq roleId) and
                    (PermissionTable.action eq actionId) and
                    (PermissionTable.module eq moduleId) and
                    (PermissionTable.team eq teamId)
        }.singleOrNull() != null

        if (exists) {
            return@dbQuery AddPermissionResult.AlreadyExists
        }

        val insertResult = PermissionTable.insert {
            it[this.role] = roleId
            it[this.action] = actionId
            it[this.module] = moduleId
            it[this.team] = teamId
        }

        if (insertResult.insertedCount > 0) {
            AddPermissionResult.Success
        } else {
            AddPermissionResult.InsertFailed
        }

    }

    override suspend fun updatePermission(
        userType: UserType,
        permissionId: UUID,
        roleType: RoleType,
        actionType: ActionType,
        moduleType: ModuleType,
        teamId: UUID
    ): UpdatePermissionResult = dbQuery {
        if (userType != UserType.ADMIN) {
            return@dbQuery UpdatePermissionResult.UserNotAdmin
        }

        val roleId = GlobalRoleIds.get(roleType)
        val actionId = GlobalActionIds.get(actionType)
        val moduleId = GlobalModuleIds.get(moduleType)

        val exists = PermissionTable.select {
            (PermissionTable.role eq roleId) and
                    (PermissionTable.action eq actionId) and
                    (PermissionTable.module eq moduleId) and
                    (PermissionTable.team eq teamId)
        }.singleOrNull() != null

        if (exists) {
            return@dbQuery UpdatePermissionResult.AlreadyExists
        }

        val updateResult = PermissionTable.update({ uuid eq permissionId }) {
            it[this.role] = roleId
            it[this.action] = actionId
            it[this.module] = moduleId
            it[this.team] = teamId
            it[updateAt] = System.currentTimeMillis()
        }

        if (updateResult > 0) {
            UpdatePermissionResult.Success
        } else {
            UpdatePermissionResult.UpdateFailed
        }
    }

    override suspend fun getPermissionsByTeam(teamId: UUID): List<PermissionInfo> = dbQuery {
        (PermissionTable innerJoin RoleTable innerJoin ActionTable innerJoin ModuleTable)
            .select { PermissionTable.team eq teamId }
            .map(::resultRowToInvitationData)
    }

    override suspend fun deletePermission(permissionId: UUID, userType: UserType): DeletePermissionResult = dbQuery {
        if (userType != UserType.ADMIN) {
            return@dbQuery DeletePermissionResult.UserNotAdmin
        }

        val exists = PermissionTable.select { uuid eq permissionId }.singleOrNull() != null

        if (!exists) {
            return@dbQuery DeletePermissionResult.PermissionNotFound
        }

        val deleteResult = PermissionTable.deleteWhere { uuid eq permissionId }

        if (deleteResult > 0) {
            DeletePermissionResult.Success
        } else {
            DeletePermissionResult.DeleteFailed
        }
    }

    override suspend fun getPermissionsByRole(roleId: UUID,teamId: UUID): List<PermissionInfo> = dbQuery {
        (PermissionTable innerJoin RoleTable innerJoin ActionTable innerJoin ModuleTable)
            .select { (PermissionTable.role eq roleId) and (PermissionTable.team eq teamId) }
            .map(::resultRowToInvitationData)
    }

    override suspend fun hasPermission(roleId: UUID, actionType: ActionType, moduleType: ModuleType, teamId: UUID): Boolean = dbQuery {
        val actionId = GlobalActionIds.get(actionType)
        val moduleId = GlobalModuleIds.get(moduleType)
        PermissionTable
            .select {
                (PermissionTable.role eq roleId) and
                        (PermissionTable.action eq actionId) and
                        (PermissionTable.module eq moduleId) and
                        (PermissionTable.team eq teamId)
            }
            .singleOrNull() != null
    }

    private fun resultRowToInvitationData(row: ResultRow) = PermissionInfo(
        id = row[uuid],
        roleId = row[RoleTable.uuid],
        roleName = row[RoleTable.name],
        actionId = row[ActionTable.uuid],
        actionName = row[ActionTable.name],
        moduleId = row[ModuleTable.uuid],
        moduleName = row[ModuleTable.name],
        teamId = row[PermissionTable.team]
    )

}
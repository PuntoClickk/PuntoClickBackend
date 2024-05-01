package com.puntoclick.data.database.role.daofacade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.role.table.RoleTable
import com.puntoclick.data.model.role.CreateRoleRequest
import com.puntoclick.data.model.role.RoleResponse
import com.puntoclick.data.model.role.UpdateRoleRequest
import com.puntoclick.features.utils.escapeSingleQuotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class RoleDaoFacadeImp : RoleDaoFacade {
    override suspend fun allRoles(): List<RoleResponse> = dbQuery {
        RoleTable.selectAll().map(::resultRowToRole)
    }

    override suspend fun role(uuid: UUID): RoleResponse? = dbQuery {
        RoleTable.select{
            RoleTable.uuid eq uuid
        }.map(::resultRowToRole).singleOrNull()
    }

    override suspend fun addRole(createRoleRequest: CreateRoleRequest): Boolean = dbQuery {
        RoleTable.insert {
            it[name] = createRoleRequest.name.escapeSingleQuotes()
        }.resultedValues?.singleOrNull() != null
    }

    override suspend fun updateRole(updateRoleRequest: UpdateRoleRequest): Boolean = dbQuery {
        RoleTable.update({
            RoleTable.uuid eq updateRoleRequest.id
        }) {
            it[name] = updateRoleRequest.name.escapeSingleQuotes()
        } > 0
    }

    override suspend fun deleteRole(uuid: UUID): Boolean = dbQuery {
        RoleTable.deleteWhere { RoleTable.uuid eq uuid } > 0
    }

    private fun resultRowToRole(row: ResultRow) = RoleResponse(
        id = row[RoleTable.uuid],
        name = row[RoleTable.name]
    )
}
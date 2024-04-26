package com.puntoclick.data.database.role.daofacade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.entity.Role
import com.puntoclick.data.database.role.table.RoleTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class RoleDaoFacadeImp : RoleDaoFacade {
    override suspend fun allRoles(): List<Role> = dbQuery {
        RoleTable.selectAll().map(::resultRowToRole)
    }

    override suspend fun role(uuid: UUID): Role? = dbQuery {
        RoleTable.select{
            RoleTable.uuid eq uuid
        }.map(::resultRowToRole).singleOrNull()
    }

    override suspend fun addRole(name: String): Boolean = dbQuery {
        RoleTable.insert {
            it[RoleTable.name] = name
        }.resultedValues?.singleOrNull() != null
    }

    override suspend fun updateRole(role: Role): Boolean = dbQuery {
        RoleTable.update({ RoleTable.uuid eq role.id}) { it[name] = role.name } > 0
    }

    override suspend fun deleteRole(uuid: UUID): Boolean = dbQuery {
        RoleTable.deleteWhere { RoleTable.uuid eq uuid } > 0
    }

    private fun resultRowToRole(row: ResultRow) = Role(
        id = row[RoleTable.uuid],
        name = row[RoleTable.name],
        createdAt = row[RoleTable.createAt],
        lastUpdate = row[RoleTable.updateAt]
    )
}
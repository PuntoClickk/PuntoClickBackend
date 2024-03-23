package com.puntoclick.features.roles.database

import com.puntoclick.data.DatabaseFactory.dbQuery
import com.puntoclick.features.roles.entity.Role
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
            it[this.name] = name
        }.resultedValues?.singleOrNull() != null
    }

    override suspend fun updateRole(role: Role): Boolean = dbQuery {
        RoleTable.update {
            uuid eq role.id
            it[name] = role.name
        } > 0
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
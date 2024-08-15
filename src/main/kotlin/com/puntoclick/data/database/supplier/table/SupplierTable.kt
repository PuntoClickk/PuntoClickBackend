package com.puntoclick.data.database.supplier.table

import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.utils.PHONE_NUMBER_LENGTH
import org.jetbrains.exposed.sql.Table

object SupplierTable: Table("Supplier") {
    val uuid = uuid("id").autoGenerate().uniqueIndex()
    val companyName = varchar("company", 20)
    val name = varchar("name", 20)
    val email = varchar("email", 20)
    val phoneNumber = varchar("phoneNumber", PHONE_NUMBER_LENGTH)
    val team = reference("team", TeamTable.uuid)
    val createAt = long("createAt").default(System.currentTimeMillis())
    val updateAt = long("updateAt").default(System.currentTimeMillis())
}

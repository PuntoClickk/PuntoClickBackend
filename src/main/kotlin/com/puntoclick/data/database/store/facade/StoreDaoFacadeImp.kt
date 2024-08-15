package com.puntoclick.data.database.store.facade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.store.table.StoreTable
import com.puntoclick.data.database.user.table.UserTable
import com.puntoclick.data.model.store.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class StoreDaoFacadeImp : StoreDaoFacade {

    override suspend fun createStore(storeData: StoreData): CreateStoreResult = dbQuery {
        val exists = StoreTable.select {
            StoreTable.name eq storeData.name
        }.singleOrNull() != null

        if (exists) {
            return@dbQuery CreateStoreResult.AlreadyExists
        }

        val insertResult = StoreTable.insert {
            it[name] = storeData.name
            it[location] = storeData.location
            it[team] = storeData.teamId
            it[user] = storeData.userId
        }

        if (insertResult.insertedCount > 0) CreateStoreResult.Success
        else CreateStoreResult.InsertFailed
    }

    override suspend fun getStoresWithUserNamesByTeam(teamId: UUID): List<StoreWithUserName> = dbQuery {
        (StoreTable leftJoin UserTable)
            .slice(StoreTable.uuid, StoreTable.name, StoreTable.location, UserTable.name)
            .select { StoreTable.team eq teamId }
            .map {
                StoreWithUserName(
                    storeId = it[StoreTable.uuid],
                    storeName = it[StoreTable.name],
                    location = it[StoreTable.location],
                    userName = it[UserTable.name]
                )
            }
    }

    override suspend fun storeExists(storeName: String, teamId: UUID): Boolean = dbQuery{
        val exists = StoreTable.select {
            (StoreTable.name eq storeName) and (StoreTable.team eq teamId)
        }.singleOrNull() != null
        exists
    }

    override suspend fun updateStore(storeId: UUID, name: String, location: String): UpdateStoreResult = dbQuery {
        val updateStatement = StoreTable.update({ StoreTable.uuid eq storeId }) {
            it[StoreTable.name] = name
            it[StoreTable.location] = location
            it[updatedAt] = System.currentTimeMillis()
        }

        if (updateStatement > 0) {
            UpdateStoreResult.Success
        } else {
            val exists = StoreTable.select { StoreTable.uuid eq storeId }.singleOrNull() != null
            if (exists) {
                UpdateStoreResult.UpdateFailed
            } else {
                UpdateStoreResult.NotFound
            }
        }
    }

    override suspend fun deleteStore(storeId: UUID): DeleteStoreResult = dbQuery {
        val deleteStatement = StoreTable.deleteWhere { uuid eq storeId }
        if (deleteStatement > 0) {
            DeleteStoreResult.Success
        } else {
            val exists = StoreTable.select { StoreTable.uuid eq storeId }.singleOrNull() != null
            if (exists) {
                DeleteStoreResult.DeleteFailed
            } else {
                DeleteStoreResult.NotFound
            }
        }
    }

}

package com.puntoclick.data.database.store.facade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.store.table.StoreTable
import com.puntoclick.data.database.user.table.UserTable
import com.puntoclick.data.model.store.CreateStoreResult
import com.puntoclick.data.model.store.StoreData
import com.puntoclick.data.model.store.StoreWithUserName
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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

    override suspend fun storeExists(storeName: String, teamId: UUID): Boolean {
        val exists = StoreTable.select {
            (StoreTable.name eq storeName) and (StoreTable.team eq teamId)
        }.singleOrNull() != null
        return exists
    }
}

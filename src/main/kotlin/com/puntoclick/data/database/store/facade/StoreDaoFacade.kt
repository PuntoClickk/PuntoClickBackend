package com.puntoclick.data.database.store.facade

import com.puntoclick.data.model.store.*
import java.util.UUID

interface StoreDaoFacade {
    suspend fun createStore(storeData: StoreData): StoreResult
    suspend fun getStoresWithUserNamesByTeam(teamId: UUID): List<StoreWithUserName>
    suspend fun storeExists(storeName: String, teamId: UUID): Boolean
    suspend fun updateStore(storeId: UUID, name: String, location: String): StoreResult
    suspend fun deleteStore(storeId: UUID): StoreResult
}
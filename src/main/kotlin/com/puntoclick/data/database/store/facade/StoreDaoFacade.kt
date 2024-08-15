package com.puntoclick.data.database.store.facade

import com.puntoclick.data.model.store.*
import java.util.UUID

interface StoreDaoFacade {
    suspend fun createStore(storeData: StoreData): CreateStoreResult
    suspend fun getStoresWithUserNamesByTeam(teamId: UUID): List<StoreWithUserName>
    suspend fun storeExists(storeName: String, teamId: UUID): Boolean
    suspend fun updateStore(storeId: UUID, name: String, location: String): UpdateStoreResult
    suspend fun deleteStore(storeId: UUID): DeleteStoreResult
}
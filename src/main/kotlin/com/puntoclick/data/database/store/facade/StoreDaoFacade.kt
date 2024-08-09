package com.puntoclick.data.database.store.facade

import com.puntoclick.data.model.store.CreateStoreResult
import com.puntoclick.data.model.store.StoreData
import com.puntoclick.data.model.store.StoreWithUserName
import java.util.UUID

interface StoreDaoFacade {
    suspend fun createStore(storeData: StoreData): CreateStoreResult
    suspend fun getStoresWithUserNamesByTeam(teamId: UUID):  List<StoreWithUserName>
}
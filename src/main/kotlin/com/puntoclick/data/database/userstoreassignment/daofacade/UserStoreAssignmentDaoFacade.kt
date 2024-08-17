package com.puntoclick.data.database.userstoreassignment.daofacade

import com.puntoclick.data.model.store.StoreWithUserName
import com.puntoclick.data.model.user.BaseInfoUser
import com.puntoclick.data.model.userstoreassigment.UpdateUserStoreAssignmentRequest
import com.puntoclick.data.model.userstoreassigment.UserStoreAssignmentRequest
import com.puntoclick.data.model.userstoreassigment.UserStoreAssignmentResult
import com.puntoclick.data.model.userstoreassigment.UserStoreAssignmentWithDetails
import java.util.*

interface UserStoreAssignmentDaoFacade {
    suspend fun assignUserToStore(userStoreAssignmentData: UserStoreAssignmentRequest, teamId: UUID): UserStoreAssignmentResult
    suspend fun getUserStoreAssignmentsByWorker(workerId: UUID, teamId: UUID): List<UserStoreAssignmentWithDetails>
    suspend fun isUserAssignedToStore(workerId: UUID, storeId: UUID, teamId: UUID): Boolean
    suspend fun removeUserFromStore(assignmentId: UUID): UserStoreAssignmentResult
    suspend fun getUsersByStore(storeId: UUID): List<BaseInfoUser>
    suspend fun getStoresByWorker(workerId: UUID, teamId: UUID): List<StoreWithUserName>
    suspend fun updateUserStoreAssignment(request: UpdateUserStoreAssignmentRequest): UserStoreAssignmentResult
}
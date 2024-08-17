package com.puntoclick.data.database.userstoreassignment.daofacade

import com.puntoclick.data.database.dbQuery
import com.puntoclick.data.database.store.table.StoreTable
import com.puntoclick.data.database.team.table.TeamTable
import com.puntoclick.data.database.user.table.UserTable
import com.puntoclick.data.database.userstoreassignment.table.UserStoreAssignmentTable
import com.puntoclick.data.model.store.StoreWithUserName
import com.puntoclick.data.model.user.BaseInfoUser
import com.puntoclick.data.model.userstoreassigment.UpdateUserStoreAssignmentRequest
import com.puntoclick.data.model.userstoreassigment.UserStoreAssignmentRequest
import com.puntoclick.data.model.userstoreassigment.UserStoreAssignmentResult
import com.puntoclick.data.model.userstoreassigment.UserStoreAssignmentWithDetails
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.util.*

class UserStoreAssignmentDaoFacadeImp : UserStoreAssignmentDaoFacade {

    override suspend fun assignUserToStore(
        userStoreAssignmentData: UserStoreAssignmentRequest,
        teamId: UUID
    ): UserStoreAssignmentResult = dbQuery {
        val exists = UserStoreAssignmentTable.selectAll()
            .where { (UserStoreAssignmentTable.workerId eq userStoreAssignmentData.workerId) and (UserStoreAssignmentTable.storeId eq userStoreAssignmentData.storeId) and (UserStoreAssignmentTable.teamId eq teamId) }
            .singleOrNull() != null

        if (exists) {
            return@dbQuery UserStoreAssignmentResult.AlreadyAssigned
        }

        val insertResult = UserStoreAssignmentTable.insert {
            it[workerId] = userStoreAssignmentData.workerId
            it[storeId] = userStoreAssignmentData.storeId
            it[UserStoreAssignmentTable.teamId] = teamId
        }

        if (insertResult.insertedCount > 0) UserStoreAssignmentResult.Success
        else UserStoreAssignmentResult.AssignmentFailed
    }

    override suspend fun getUserStoreAssignmentsByWorker(workerId: UUID, teamId: UUID): List<UserStoreAssignmentWithDetails> =
        dbQuery {
            val userAlias = UserTable.alias("worker_user")
            val teamAliasForAssignment = TeamTable.alias("assignment_team")

            (UserStoreAssignmentTable leftJoin StoreTable)
                .leftJoin(userAlias, { UserStoreAssignmentTable.workerId }, { userAlias[UserTable.uuid] })
                .leftJoin(teamAliasForAssignment, { UserStoreAssignmentTable.teamId }, { teamAliasForAssignment[TeamTable.uuid] })
                .select(UserStoreAssignmentTable.uuid, userAlias[UserTable.name], StoreTable.name, teamAliasForAssignment[TeamTable.name])
                .where {
                    (UserStoreAssignmentTable.workerId eq workerId) and
                            (UserStoreAssignmentTable.teamId eq teamId)
                }
                .map {
                    UserStoreAssignmentWithDetails(
                        assignmentId = it[UserStoreAssignmentTable.uuid],
                        workerName = it[userAlias[UserTable.name]],
                        storeName = it[StoreTable.name],
                        teamName = it[teamAliasForAssignment[TeamTable.name]]
                    )
                }
        }

    override suspend fun isUserAssignedToStore(workerId: UUID, storeId: UUID, teamId: UUID): Boolean = dbQuery {
        UserStoreAssignmentTable.selectAll()
            .where { (UserStoreAssignmentTable.workerId eq workerId) and (UserStoreAssignmentTable.storeId eq storeId) and (UserStoreAssignmentTable.teamId eq teamId) }
            .singleOrNull() != null
    }

    override suspend fun removeUserFromStore(assignmentId: UUID): UserStoreAssignmentResult = dbQuery {
        val deleteStatement = UserStoreAssignmentTable.deleteWhere { uuid eq assignmentId }
        if (deleteStatement > 0) {
            UserStoreAssignmentResult.Success
        } else {
            UserStoreAssignmentResult.AssignmentFailed
        }
    }

    override suspend fun getUsersByStore(storeId: UUID): List<BaseInfoUser> = dbQuery {
        (UserStoreAssignmentTable leftJoin UserTable)
            .select(UserTable.uuid, UserTable.name, UserTable.lastName, UserTable.phoneNumber, UserTable.birthday)
            .where { UserStoreAssignmentTable.storeId eq storeId }
            .map {
                BaseInfoUser(
                    id = it[UserTable.uuid],
                    name = it[UserTable.name],
                    lastName = it[UserTable.lastName],
                    phoneNumber = it[UserTable.phoneNumber],
                    birthday = it[UserTable.birthday]
                )
            }
    }

    override suspend fun getStoresByWorker(workerId: UUID, teamId: UUID): List<StoreWithUserName> = dbQuery {
        val userAlias = UserTable.alias("worker_user")

        (UserStoreAssignmentTable leftJoin StoreTable)
            .leftJoin(userAlias, { UserStoreAssignmentTable.workerId }, { userAlias[UserTable.uuid] })
            .select(StoreTable.uuid, StoreTable.name, StoreTable.location, userAlias[UserTable.name])
            .where { (UserStoreAssignmentTable.workerId eq workerId) and (UserStoreAssignmentTable.teamId eq teamId) }
            .map {
                StoreWithUserName(
                    storeId = it[StoreTable.uuid],
                    storeName = it[StoreTable.name],
                    location = it[StoreTable.location],
                    userName = it[userAlias[UserTable.name]]
                )
            }
    }

    override suspend fun updateUserStoreAssignment(request: UpdateUserStoreAssignmentRequest): UserStoreAssignmentResult = dbQuery {
        val updateStatement = UserStoreAssignmentTable.update({ UserStoreAssignmentTable.uuid eq request.assignmentId }) {
            it[workerId] = request.newWorkerId
            it[updatedAt] = System.currentTimeMillis()
        }

        if (updateStatement > 0) {
            UserStoreAssignmentResult.Success
        } else {
            UserStoreAssignmentResult.NotFound
        }
    }

}
package com.puntoclick.features.user.controller

import com.puntoclick.data.database.entity.Role
import com.puntoclick.data.database.entity.User
import com.puntoclick.data.database.role.daofacade.RoleDaoFacade
import com.puntoclick.data.database.user.daofacade.UserDaoFacade
import com.puntoclick.data.model.AppResult
import com.puntoclick.features.user.UserFT
import com.puntoclick.features.utils.createError
import io.ktor.http.*
import java.util.*

class UserController(
    private val userDaoFacade: UserDaoFacade,
    val roleDaoFacade: RoleDaoFacade
) {

    /*suspend fun getUser(id: UUID) : UserFT?{

      *//*  val user = userDaoFacade.user(uuid = id)

        val role = roleDaoFacade.role(id)

        return user?.toFeature(role!!)*//*


    }*/

     suspend fun createUSer(user: User): AppResult<Boolean> =
        if (userDaoFacade.addUser(user)) {
            AppResult.Success(
                data = true, appStatus = HttpStatusCode.OK
            )
        } else createError(title = "NError", "Desc Error", HttpStatusCode.BadRequest)


}

/*
fun User.toFeature(role: Role): UserFT{

}*/

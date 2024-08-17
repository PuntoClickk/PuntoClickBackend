package com.puntoclick.plugins

import com.puntoclick.data.model.UUIDAppRequest
import com.puntoclick.data.model.auth.CreateAdminRequest
import com.puntoclick.data.model.auth.CreateUserRequest
import com.puntoclick.data.model.auth.LoginRequest
import com.puntoclick.data.model.auth.ValidateEmailRequest
import com.puntoclick.data.model.category.CreateCategoryRequest
import com.puntoclick.data.model.category.UpdateCategoryRequest
import com.puntoclick.data.model.invitation.AcceptInvitationRequest
import com.puntoclick.data.model.supplier.CreateSupplierRequest
import com.puntoclick.data.model.supplier.UpdateSupplierRequest
import com.puntoclick.data.model.role.CreateRoleRequest
import com.puntoclick.data.model.store.CreateStoreRequest
import com.puntoclick.data.model.store.UpdateStoreRequest
import com.puntoclick.data.model.team.CreateTeamRequest
import com.puntoclick.features.auth.validation.validateEmailRequest
import com.puntoclick.features.auth.validation.validateLoginRequest
import com.puntoclick.features.category.validation.validateCreateCategoryRequest
import com.puntoclick.features.invitation.validation.validateAcceptInvitationRequest
import com.puntoclick.features.supplier.validation.validateCreateSupplierRequest
import com.puntoclick.features.supplier.validation.validateUpdateCategoryRequest
import com.puntoclick.features.roles.validation.validateRoleRequest
import com.puntoclick.features.store.validation.validateCreteStoreRequest
import com.puntoclick.features.team.validation.validateTeamRequest
import com.puntoclick.features.user.validation.validateCreateUserRequest
import com.puntoclick.security.validateUUIDAppRequest
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*


fun Application.configureRequestValidation() {

    install(RequestValidation) {
        validate<UUIDAppRequest> { uuidAppRequest ->
            uuidAppRequest.validateUUIDAppRequest()
        }
        validate<CreateAdminRequest> { createUserRequest ->
            createUserRequest.validateCreateUserRequest()
        }

        validate<CreateTeamRequest> { createTeamRequest ->
            createTeamRequest.validateTeamRequest()
        }

        validate<CreateRoleRequest> { createRoleRequest ->
            createRoleRequest.validateRoleRequest()
        }

        validate<CreateUserRequest> { createAdminRequest ->
            createAdminRequest.validateCreateUserRequest()
        }

        validate<LoginRequest> { loginRequest ->
            loginRequest.validateLoginRequest()
        }

        validate<ValidateEmailRequest> { validateEmailRequest ->
            validateEmailRequest.validateEmailRequest()
        }

        validate<AcceptInvitationRequest> { acceptInvitationRequest ->
            acceptInvitationRequest.validateAcceptInvitationRequest()
        }

        validate<CreateCategoryRequest> { createCategoryRequest ->
            createCategoryRequest.validateCreateCategoryRequest()
        }

        validate<UpdateCategoryRequest> { updateCategoryRequest ->
            updateCategoryRequest.validateCreateCategoryRequest()
        }

        validate<CreateSupplierRequest> { createSupplierRequest ->
            createSupplierRequest.validateCreateSupplierRequest()

        }

        validate<UpdateSupplierRequest> { updateSupplierRequest ->
            updateSupplierRequest.validateUpdateCategoryRequest()
        }

        validate<CreateStoreRequest> { createStoreRequest ->
            createStoreRequest.validateCreteStoreRequest()
        }

        validate<UpdateStoreRequest> { createStoreRequest ->
            createStoreRequest.validateCreteStoreRequest()
        }
    }
}

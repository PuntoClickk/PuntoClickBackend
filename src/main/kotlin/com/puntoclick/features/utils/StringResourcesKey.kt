package com.puntoclick.features.utils


enum class StringResourcesKey(val value: String) {
    LOGIN_MESSAGE_ERROR_KEY("login_message_error"),
    LOGIN_USER_BLOCKED_MESSAGE_ERROR_KEY("login_user_blocked_message_error"),
    LOGIN_USER_INACTIVE_MESSAGE_ERROR_KEY("login_user_inactive_message_error"),
    GENERIC_TITLE_ERROR_KEY("generic_title_error"),
    GENERIC_DESCRIPTION_ERROR_KEY("generic_description_error"),
    USER_NOT_CREATED_ERROR_KEY("create_user_message_error"),
    EMAIL_MESSAGE_ERROR_KEY("email_message_error"),
    CODE_GENERATION_FAILED_ERROR_KEY("code_generation_failed_error"),
    CODE_NOT_FOUND_OR_EXPIRED_ERROR_KEY("code_not_found_or_expired_error"),
    USER_NOT_UPDATED_ERROR_KEY("user_not_updated_error"),
    NO_USER_FOUND_ERROR_KEY("no_user_found_error"),
    USER_NOT_DELETED_ERROR_KEY("user_not_deleted_error"),
    PERMISSION_ALREADY_EXISTS_ERROR_KEY("permission_already_exists_error"),
    PERMISSION_INSERT_FAILED_ERROR_KEY("permission_insert_failed_error"),
    PERMISSION_SUCCESS_MESSAGE_KEY("permission_success_message"),
    PERMISSION_USER_NOT_ADMIN_ERROR_KEY("permission_user_not_admin_error"),
    PERMISSION_UPDATE_SUCCESS_MESSAGE_KEY("permission_update_success_message"),
    PERMISSION_UPDATE_ALREADY_EXISTS_ERROR_KEY("permission_update_already_exists_error"),
    PERMISSION_UPDATE_USER_NOT_ADMIN_ERROR_KEY("permission_update_user_not_admin_error"),
    PERMISSION_UPDATE_FAILED_ERROR_KEY("permission_update_failed_error"),
    PERMISSION_DELETE_SUCCESS_MESSAGE_KEY("permission_delete_success_message"),
    PERMISSION_DELETE_NOT_FOUND_ERROR_KEY("permission_delete_not_found_error"),
    PERMISSION_DELETE_USER_NOT_ADMIN_ERROR_KEY("permission_delete_user_not_admin_error"),
    PERMISSION_DELETE_FAILED_ERROR_KEY("permission_delete_failed_error"),
    INVALID_EMAIL_ERROR_KEY("invalid_email_error"),
    INVALID_PASSWORD_ERROR_KEY("invalid_password_error"),
    INVALID_NAME_ERROR_KEY("invalid_name_error"),
    INVALID_LAST_NAME_ERROR_KEY("invalid_last_name_error"),
    INVALID_PHONE_ERROR_KEY("invalid_phone_error"),
    ACTION_PERMISSION_DENIED_ERROR_KEY("action_permission_denied_error"),
    //CATEGORIES
    CATEGORY_OPERATION_SUCCESS_MESSAGE_KEY("category_operation_success_message"),
    CATEGORY_ALREADY_EXISTS_ERROR_KEY("category_already_exists_error"),
    CATEGORY_INSERT_FAILED_ERROR_KEY("category_insert_failed_error"),
    CATEGORY_UPDATE_FAILED_ERROR_KEY("category_update_failed_error"),
    CATEGORY_DELETE_FAILED_ERROR_KEY("category_delete_failed_error"),
    CATEGORY_NOT_FOUND_ERROR_KEY("category_not_found_error"),
    VALID_CATEGORY_NAME_ERROR_KEY("valid_category_name_error"),
    //SUPPLIERS
    SUPPLIER_OPERATION_SUCCESS_MESSAGE_KEY("supplier_operation_success_message"),
    SUPPLIER_ALREADY_EXISTS_ERROR_KEY("supplier_already_exists_error"),
    SUPPLIER_INSERT_FAILED_ERROR_KEY("supplier_insert_failed_error"),
    SUPPLIER_UPDATE_FAILED_ERROR_KEY("supplier_update_failed_error"),
    SUPPLIER_DELETE_FAILED_ERROR_KEY("supplier_delete_failed_error"),
    SUPPLIER_NOT_FOUND_ERROR_KEY("supplier_not_found_error"),
    SUPPLIER_INVALID_DATA_ERROR_KEY("supplier_invalid_data_error"),
    SUPPLIER_PERMISSION_DENIED_ERROR_KEY("supplier_permission_denied_error"),
    SUPPLIER_OPERATION_FAILED_ERROR_KEY("supplier_operation_failed_error"),
    // STORES
    STORE_OPERATION_SUCCESS_MESSAGE_KEY("store_operation_success_message"),
    STORE_NAME_ALREADY_EXISTS_ERROR_KEY("store_name_already_exists_error"),
    STORE_DELETE_FAILED_ERROR_KEY("store_delete_failed_error"),
    STORE_INSERT_FAILED_ERROR_KEY("store_insert_failed_error"),
    STORE_NOT_FOUND_ERROR_KEY("store_not_found_error"),
    STORE_UPDATE_FAILED_ERROR_KEY("store_update_failed_error"),
    STORE_NAME_INVALID_ERROR_KEY("store_name_invalid_error"),
    STORE_LOCATION_NAME_INVALID_ERROR_KEY("location_name_invalid_error"),
    // RATE LIMIT
    TOO_MANY_REQUESTS_ERROR_KEY("too_many_requests_error"),
    // USER ASSIGNMENT
    USER_STORE_ASSIGNMENT_OPERATION_SUCCESS_MESSAGE_KEY("user_store_assignment_operation_success_message"),
    USER_ALREADY_ASSIGNED_ERROR_KEY("user_already_assigned_error"),
    USER_STORE_ASSIGNMENT_FAILED_ERROR_KEY("user_store_assignment_failed_error"),
    USER_STORE_ASSIGNMENT_NOT_FOUND_ERROR_KEY("user_store_assignment_not_found_error");

}

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
    USER_NOT_DELETED_ERROR_KEY("user_not_deleted_error");
}

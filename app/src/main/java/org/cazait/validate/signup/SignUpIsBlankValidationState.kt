package org.cazait.validate.signup

enum class SignUpIsBlankValidationState {
    ACCOUNT_NAME_BLANK,
    PASSWORD_BLANK,
    CONFIRM_PASSWORD_BLANK,
    PHONE_NUMBER_BLANK,
    NICKNAME_BLANK,
    SUCCESS_ALL,
}

enum class SignUpPasswordMatchState { NOT_MATCHED, MATCHED }

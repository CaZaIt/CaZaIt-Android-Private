package org.cazait.validate.signup

class SignUpLogicValidator(
    private val accountName: String,
    private val password: String,
    private val confirmPassword: String,
    private val phoneNumber: String,
    private val nickname: String,
) {
    fun validateIsNotBlank(): SignUpIsBlankValidationState {
        if (accountName.isBlank()) return SignUpIsBlankValidationState.ACCOUNT_NAME_BLANK
        if (password.isBlank()) return SignUpIsBlankValidationState.PASSWORD_BLANK
        if (confirmPassword.isBlank()) return SignUpIsBlankValidationState.CONFIRM_PASSWORD_BLANK
        if (phoneNumber.isBlank()) return SignUpIsBlankValidationState.PHONE_NUMBER_BLANK
        if (nickname.isBlank()) return SignUpIsBlankValidationState.NICKNAME_BLANK
        return SignUpIsBlankValidationState.SUCCESS_ALL
    }

    fun validatePasswordMatches(): SignUpPasswordMatchState {
        if (password != confirmPassword) return SignUpPasswordMatchState.NOT_MATCHED
        return SignUpPasswordMatchState.MATCHED
    }
}

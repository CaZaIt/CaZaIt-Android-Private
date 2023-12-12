package org.cazait.validate.check

class PasswordChangingLogicValidator(
    private val password: String,
    private val confirmPassword: String,
) {
    fun validate(): PasswordChangingValidationState {
        password.ifBlank { return PasswordChangingValidationState.PASSWORD_BLANK }
        confirmPassword.ifBlank { return PasswordChangingValidationState.CONFIRM_PASSWORD_BLANK }
        if (password != confirmPassword) return PasswordChangingValidationState.IS_NOT_EQUAL
        return PasswordChangingValidationState.PASS
    }
}

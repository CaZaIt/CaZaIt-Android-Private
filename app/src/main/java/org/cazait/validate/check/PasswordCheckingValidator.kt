package org.cazait.validate.check

class PasswordCheckingValidator(
    private val password: String,
) {
    fun validateIsBlank(): PasswordCheckingValidationState {
        password.ifBlank { return PasswordCheckingValidationState.IS_BLANK }
        return PasswordCheckingValidationState.PASS
    }
}

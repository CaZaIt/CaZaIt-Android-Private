package org.cazait.validate.check

import org.cazait.core.domain.model.user.Nickname

class NicknameFormatValidator(
    private val nickname: String,
) {
    fun validate(): NicknameFormatValidationState {
        nickname.ifBlank { return NicknameFormatValidationState.IS_BLANK }
        if (nickname.length < Nickname.NICKNAME_LENGTH_MIN) return NicknameFormatValidationState.TOO_SHORT
        if (nickname.length > Nickname.NICKNAME_LENGTH_MAX) return NicknameFormatValidationState.TOO_LONG
        return NicknameFormatValidationState.PASS
    }
}

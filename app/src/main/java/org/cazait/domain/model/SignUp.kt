package org.cazait.domain.model

data class SignUp(
    val isSignUp: Boolean,
    val userInfo: UserInfo
)

data class EmailDup(
    val isDup: Boolean = false,
    val message: String,
)

data class NicknameDup(
    val isDup: Boolean = false,
    val message: String,
)
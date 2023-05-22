package org.cazait.domain.model.mapper

import org.cazait.data.dto.response.IsEmailDupRes
import org.cazait.data.dto.response.IsNicknameDupRes
import org.cazait.data.dto.response.SignUpRes
import org.cazait.data.model.CafeImage
import org.cazait.domain.model.Cafe
import org.cazait.domain.model.EmailDup
import org.cazait.domain.model.FavoriteCafe
import org.cazait.domain.model.NicknameDup
import org.cazait.domain.model.SignUp
import org.cazait.domain.model.UserInfo

object DomainMapper {
    fun FavoriteCafe.toCafe() = Cafe(
        cafeId = cafeId,
        name = name,
        address = address,
        distance = 0,
        status = congestionStatus,
        images = imageUrl.map { CafeImage(0L, it) },
        latitude = latitude,
        longitude = longitude
    )

    fun Cafe.toFavoriteCafe() = FavoriteCafe(
        favoritesId = cafeId,
        cafeId = cafeId,
        name = name,
        address = address,
        latitude = latitude ?: "0",
        longitude = longitude ?: "0",
        congestionStatus = status,
        imageUrl = images.map { it.imageUrl }
    )

    fun IsEmailDupRes.toEmailDup() = EmailDup(
        message = message
    )

    fun IsNicknameDupRes.toNicknameDup() = NicknameDup(
        message = data
    )

    fun SignUpRes.toSignUp() = SignUp(
        isSignUp = true,    // 일단 true
        userInfo = UserInfo(
            email = userInfo.email,
            password = userInfo.password,
            nickname = userInfo.nickname,
        )
    )
}
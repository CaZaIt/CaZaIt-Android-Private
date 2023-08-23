package org.bmsk.data.model

import org.cazait.database.model.entity.FavoriteCafeEntity
import org.cazait.database.model.entity.RecentlyViewedCafeEntity
import org.cazait.model.Cafe
import org.cazait.model.CafeMenu
import org.cazait.model.CafeReviews
import org.cazait.model.Check
import org.cazait.model.FavoriteCafe
import org.cazait.model.FindPassUserData
import org.cazait.model.VerificationCode
import org.cazait.model.RecentlyViewedCafe
import org.cazait.model.SignInInfo
import org.cazait.model.SignUpInfo
import org.cazait.model.UserAccount
import org.cazait.model.UserPassword
import org.cazait.model.VerifyCode
import org.cazait.network.model.dto.CafeDTO
import org.cazait.network.model.dto.response.CafeMenuDTO
import org.cazait.network.model.dto.response.CheckRes
import org.cazait.network.model.dto.response.CheckUserDataRes
import org.cazait.network.model.dto.response.FavoriteCafeDTO
import org.cazait.network.model.dto.response.FindUserIdDTO
import org.cazait.network.model.dto.response.VerificationCodeRes
import org.cazait.network.model.dto.response.ResetPasswordDTO
import org.cazait.network.model.dto.response.ResetPasswordRes
import org.cazait.network.model.dto.response.ReviewDTO
import org.cazait.network.model.dto.response.SignInInfoDTO
import org.cazait.network.model.dto.response.SignUpInfoDTO
import org.cazait.network.model.dto.response.VerifyCodeRes
import java.util.Date

fun Cafe.toFavoriteCafeEntity() = FavoriteCafeEntity(
    favoritesId = 0L,
    cafeId = cafeId,
    name = name,
    address = address,
    latitude = latitude ?: "0",
    longitude = longitude ?: "0",
    congestionStatus = status,
    imageUrl = images,
    createdDate = Date()
)

fun FavoriteCafe.toFavoriteCafeEntity() = FavoriteCafeEntity(
    favoritesId = favoritesId,
    cafeId = cafeId,
    name = name,
    address = address,
    latitude = latitude,
    longitude = longitude,
    congestionStatus = congestionStatus,
    imageUrl = imageUrl,
    createdDate = Date()
)

fun FavoriteCafeEntity.toFavoriteCafe() = FavoriteCafe(
    favoritesId = favoritesId,
    cafeId = cafeId,
    name = name,
    address = address,
    latitude = latitude,
    longitude = longitude,
    congestionStatus = congestionStatus,
    imageUrl = imageUrl,
)

fun Cafe.toRecentlyViewedCafeEntity() = RecentlyViewedCafeEntity(
    cafeId = cafeId,
    timestamp = timestamp
)

fun RecentlyViewedCafe.toRecentlyViewedCafeEntity() = RecentlyViewedCafeEntity(
    cafeId = cafeId,
    timestamp = timestamp
)

fun RecentlyViewedCafeEntity.toRecently() = RecentlyViewedCafe(
    cafeId = cafeId,
    timestamp = timestamp
)

fun CafeDTO.toCafe() = Cafe(
    cafeId = cafeId,
    name = name,
    address = address,
    distance = distance,
    status = congestionStatus,
    images = cafesImages,
    latitude = latitude,
    longitude = longitude,
    timestamp = System.currentTimeMillis(),
    isFavorite = favorite == "ACTIVE",
)

fun FavoriteCafeDTO.toFavoriteCafe() = FavoriteCafe(
    favoritesId = favoritesId,
    cafeId = cafeId,
    name = name,
    address = address,
    latitude = latitude,
    longitude = longitude,
    congestionStatus = congestionStatus,
    imageUrl = imageUrl
)

fun CafeMenuDTO.toCafeMenu() = CafeMenu(
    menuId = menuId,
    menuName = menuName,
    menuDesc = menuDesc,
    menuPrice = menuPrice,
    image = image,
)

fun ReviewDTO.toCafeReviews() = CafeReviews(
    reviews = reviews,
    total = total,
    nextCursor = nextCursor,
)

fun SignInInfoDTO.toSignInInfo() = SignInInfo(
    userId = userId,
    uuid,
    accessToken = accessToken,
    refreshToken = refreshToken,
    role = role,
)

fun SignUpInfoDTO.toSignUpInfo() = SignUpInfo(
    uuid = uuid,
    userId = userId,
    phoneNumber,
    nickname = nickname
)

fun VerificationCodeRes.toMessage() = VerificationCode(
    verify = false,
    message = message
)

fun VerifyCodeRes.toVerify() = VerifyCode(
    verify = false,
    message = message
)

fun FindUserIdDTO.toFindUserId() = UserAccount(
    userId = userId
)

fun CheckRes.toCheck() = Check(
    message = message,
    data = data
)

fun CheckUserDataRes.toUser() = FindPassUserData(
    message = message,
    userId = data.userId
)

fun ResetPasswordRes.toResetPassword() = UserPassword(
    message = message
)
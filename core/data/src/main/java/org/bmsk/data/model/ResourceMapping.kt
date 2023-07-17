package org.bmsk.data.model

import org.cazait.database.model.entity.FavoriteCafeEntity
import org.cazait.database.model.entity.RecentlyViewedCafeEntity
import org.cazait.model.Cafe
import org.cazait.model.CafeMenu
import org.cazait.model.CafeReviews
import org.cazait.model.IdDup
import org.cazait.model.FavoriteCafe
import org.cazait.model.NicknameDup
import org.cazait.model.RecentlyViewedCafe
import org.cazait.model.SignInInfo
import org.cazait.model.SignUpInfo
import org.cazait.network.model.dto.CafeDTO
import org.cazait.network.model.dto.response.CafeMenuDTO
import org.cazait.network.model.dto.response.FavoriteCafeDTO
import org.cazait.network.model.dto.response.IsIdNumberDupRes
import org.cazait.network.model.dto.response.IsNicknameDupRes
import org.cazait.network.model.dto.response.ReviewDTO
import org.cazait.network.model.dto.response.SignInInfoDTO
import org.cazait.network.model.dto.response.SignUpInfoDTO
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
    isFavorite = favorite,
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
    email = email,
    id = id,
    accessToken = accessToken,
    refreshToken = refreshToken,
    role = role,
)

fun SignUpInfoDTO.toSignUpInfo() = SignUpInfo(
    id = id,
    idNumber = idNumber,
    password = password,
    nickname = nickname,
)

fun IsNicknameDupRes.toNicknameDup() = NicknameDup(
    message = message,
    isDup = data == null,
)

fun IsIdNumberDupRes.toIdNumberDup() = IdDup(
    message = message,
    isDup = data == null,
)
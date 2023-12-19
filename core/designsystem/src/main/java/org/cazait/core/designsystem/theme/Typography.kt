package org.cazait.core.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class Typography(
    val regular10: TextStyle = TextStyle(),
    val medium10: TextStyle = TextStyle(),
    val semiBold10: TextStyle = TextStyle(),
    val bold10: TextStyle = TextStyle(),
    val extraBold10: TextStyle = TextStyle(),

    val regular12: TextStyle = TextStyle(),
    val medium12: TextStyle = TextStyle(),
    val semiBold12: TextStyle = TextStyle(),
    val bold12: TextStyle = TextStyle(),
    val extraBold12: TextStyle = TextStyle(),

    val regular14: TextStyle = TextStyle(),
    val medium14: TextStyle = TextStyle(),
    val semiBold14: TextStyle = TextStyle(),
    val bold14: TextStyle = TextStyle(),
    val extraBold14: TextStyle = TextStyle(),

    val regular16: TextStyle = TextStyle(),
    val medium16: TextStyle = TextStyle(),
    val semiBold16: TextStyle = TextStyle(),
    val bold16: TextStyle = TextStyle(),
    val extraBold16: TextStyle = TextStyle(),

    val regular17: TextStyle = TextStyle(),
    val medium17: TextStyle = TextStyle(),
    val semiBold17: TextStyle = TextStyle(),
    val bold17: TextStyle = TextStyle(),
    val extraBold17: TextStyle = TextStyle(),

    val regular18: TextStyle = TextStyle(),
    val medium18: TextStyle = TextStyle(),
    val semiBold18: TextStyle = TextStyle(),
    val bold18: TextStyle = TextStyle(),
    val extraBold18: TextStyle = TextStyle(),

    val regular20: TextStyle = TextStyle(),
    val medium20: TextStyle = TextStyle(),
    val semiBold20: TextStyle = TextStyle(),
    val bold20: TextStyle = TextStyle(),
    val extraBold20: TextStyle = TextStyle(),

    val regular24: TextStyle = TextStyle(),
    val medium24: TextStyle = TextStyle(),
    val semiBold24: TextStyle = TextStyle(),
    val bold24: TextStyle = TextStyle(),
    val extraBold24: TextStyle = TextStyle(),

    val regular26: TextStyle = TextStyle(),
    val medium26: TextStyle = TextStyle(),
    val semiBold26: TextStyle = TextStyle(),
    val bold26: TextStyle = TextStyle(),
    val extraBold26: TextStyle = TextStyle(),

    val regular28: TextStyle = TextStyle(),
    val medium28: TextStyle = TextStyle(),
    val semiBold28: TextStyle = TextStyle(),
    val bold28: TextStyle = TextStyle(),
    val extraBold28: TextStyle = TextStyle(),

    val regular30: TextStyle = TextStyle(),
    val medium30: TextStyle = TextStyle(),
    val semiBold30: TextStyle = TextStyle(),
    val bold30: TextStyle = TextStyle(),
    val extraBold30: TextStyle = TextStyle(),
)

val CazaitTypography = Typography(
    regular10 = TextStyle(fontWeight = FontWeight.W400, fontSize = 10.sp),
    medium10 = TextStyle(fontWeight = FontWeight.W500, fontSize = 10.sp),
    semiBold10 = TextStyle(fontWeight = FontWeight.W600, fontSize = 10.sp),
    bold10 = TextStyle(fontWeight = FontWeight.W700, fontSize = 10.sp),
    extraBold10 = TextStyle(fontWeight = FontWeight.W800, fontSize = 10.sp),

    regular12 = TextStyle(fontWeight = FontWeight.W400, fontSize = 12.sp),
    medium12 = TextStyle(fontWeight = FontWeight.W500, fontSize = 12.sp),
    semiBold12 = TextStyle(fontWeight = FontWeight.W600, fontSize = 12.sp),
    bold12 = TextStyle(fontWeight = FontWeight.W700, fontSize = 12.sp),
    extraBold12 = TextStyle(fontWeight = FontWeight.W800, fontSize = 12.sp),

    regular14 = TextStyle(fontWeight = FontWeight.W400, fontSize = 14.sp),
    medium14 = TextStyle(fontWeight = FontWeight.W500, fontSize = 14.sp),
    semiBold14 = TextStyle(fontWeight = FontWeight.W600, fontSize = 14.sp),
    bold14 = TextStyle(fontWeight = FontWeight.W700, fontSize = 14.sp),
    extraBold14 = TextStyle(fontWeight = FontWeight.W800, fontSize = 14.sp),

    regular16 = TextStyle(fontWeight = FontWeight.W400, fontSize = 16.sp),
    medium16 = TextStyle(fontWeight = FontWeight.W500, fontSize = 16.sp),
    semiBold16 = TextStyle(fontWeight = FontWeight.W600, fontSize = 16.sp),
    bold16 = TextStyle(fontWeight = FontWeight.W700, fontSize = 16.sp),
    extraBold16 = TextStyle(fontWeight = FontWeight.W800, fontSize = 16.sp),

    regular17 = TextStyle(fontWeight = FontWeight.W400, fontSize = 17.sp),
    medium17 = TextStyle(fontWeight = FontWeight.W500, fontSize = 17.sp),
    semiBold17 = TextStyle(fontWeight = FontWeight.W600, fontSize = 17.sp),
    bold17 = TextStyle(fontWeight = FontWeight.W700, fontSize = 17.sp),
    extraBold17 = TextStyle(fontWeight = FontWeight.W800, fontSize = 17.sp),

    regular18 = TextStyle(fontWeight = FontWeight.W400, fontSize = 18.sp),
    medium18 = TextStyle(fontWeight = FontWeight.W500, fontSize = 18.sp),
    semiBold18 = TextStyle(fontWeight = FontWeight.W600, fontSize = 18.sp),
    bold18 = TextStyle(fontWeight = FontWeight.W700, fontSize = 18.sp),
    extraBold18 = TextStyle(fontWeight = FontWeight.W800, fontSize = 18.sp),

    regular20 = TextStyle(fontWeight = FontWeight.W400, fontSize = 20.sp),
    medium20 = TextStyle(fontWeight = FontWeight.W500, fontSize = 20.sp),
    semiBold20 = TextStyle(fontWeight = FontWeight.W600, fontSize = 20.sp),
    bold20 = TextStyle(fontWeight = FontWeight.W700, fontSize = 20.sp),
    extraBold20 = TextStyle(fontWeight = FontWeight.W800, fontSize = 20.sp),

    regular24 = TextStyle(fontWeight = FontWeight.W400, fontSize = 24.sp),
    medium24 = TextStyle(fontWeight = FontWeight.W500, fontSize = 24.sp),
    semiBold24 = TextStyle(fontWeight = FontWeight.W600, fontSize = 24.sp),
    bold24 = TextStyle(fontWeight = FontWeight.W700, fontSize = 24.sp),
    extraBold24 = TextStyle(fontWeight = FontWeight.W800, fontSize = 24.sp),

    regular26 = TextStyle(fontWeight = FontWeight.W400, fontSize = 26.sp),
    medium26 = TextStyle(fontWeight = FontWeight.W500, fontSize = 26.sp),
    semiBold26 = TextStyle(fontWeight = FontWeight.W600, fontSize = 26.sp),
    bold26 = TextStyle(fontWeight = FontWeight.W700, fontSize = 26.sp),
    extraBold26 = TextStyle(fontWeight = FontWeight.W800, fontSize = 26.sp),

    regular28 = TextStyle(fontWeight = FontWeight.W400, fontSize = 28.sp),
    medium28 = TextStyle(fontWeight = FontWeight.W500, fontSize = 28.sp),
    semiBold28 = TextStyle(fontWeight = FontWeight.W600, fontSize = 28.sp),
    bold28 = TextStyle(fontWeight = FontWeight.W700, fontSize = 28.sp),
    extraBold28 = TextStyle(fontWeight = FontWeight.W800, fontSize = 28.sp),

    regular30 = TextStyle(fontWeight = FontWeight.W400, fontSize = 30.sp),
    medium30 = TextStyle(fontWeight = FontWeight.W500, fontSize = 30.sp),
    semiBold30 = TextStyle(fontWeight = FontWeight.W600, fontSize = 30.sp),
    bold30 = TextStyle(fontWeight = FontWeight.W700, fontSize = 30.sp),
    extraBold30 = TextStyle(fontWeight = FontWeight.W800, fontSize = 30.sp),
)

package org.cazait.model

data class CafeMenus(
    val menus: List<CafeMenu>,
)

data class CafeMenu(
    val menuId: Long,
    val menuName: String,
    val menuDesc: String?,
    val menuPrice: Int,
    val image: String
) {
    fun getStringPrice() = menuPrice.toString()
}
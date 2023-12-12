package org.cazait.core.data.mapper

import org.cazait.core.data.datasource.response.CafeMenuDTO
import org.cazait.core.data.datasource.response.CafeMenuResponse
import org.cazait.core.model.cafe.CafeMenu
import org.cazait.core.model.cafe.CafeMenus

internal fun CafeMenuResponse.toData(): CafeMenus = CafeMenus(
    menus = this.menus.map(CafeMenuDTO::toData),
)

internal fun CafeMenuDTO.toData(): CafeMenu = CafeMenu(
    menuId = this.menuId,
    menuName = this.menuName,
    menuDesc = this.menuDesc,
    menuPrice = this.menuPrice,
    image = this.image,
)

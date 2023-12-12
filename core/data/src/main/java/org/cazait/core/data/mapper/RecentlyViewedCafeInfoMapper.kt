package org.cazait.core.data.mapper

import org.cazait.core.model.cafe.RecentlyViewedCafeInfo
import org.cazait.database.model.entity.RecentlyViewedCafeEntity

internal fun RecentlyViewedCafeEntity.toData(): RecentlyViewedCafeInfo = RecentlyViewedCafeInfo(
    cafeId = this.cafeId,
    timestamp = this.timestamp,
)

internal fun RecentlyViewedCafeInfo.toEntity(): RecentlyViewedCafeEntity = RecentlyViewedCafeEntity(
    cafeId = this.cafeId,
    timestamp = this.timestamp,
)

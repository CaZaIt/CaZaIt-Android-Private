package org.cazait.core.data.mapper

import org.cazait.core.data.datasource.response.ExistenceResponse
import org.cazait.core.model.ExistenceStatus

internal fun ExistenceResponse.toData(): ExistenceStatus = ExistenceStatus(
    message = this.message,
)

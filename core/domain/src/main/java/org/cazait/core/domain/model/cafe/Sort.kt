package org.cazait.core.domain.model.cafe

enum class Sort {
    DISTANCE, NAME, CONGESTION,
    ;

    override fun toString(): String = when (this) {
        DISTANCE -> "distance"
        NAME -> "name"
        CONGESTION -> "congestion"
    }
}

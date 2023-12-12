package org.cazait.core.model.congestion

enum class CafeStatus {
    FREE,
    NORMAL,
    CLOSE,
    CROWDED,
    VERY_CROWDED,
    NONE,

    ;

    companion object {
        fun fromString(value: String): CafeStatus {
            return try {
                valueOf(value)
            } catch (e: IllegalArgumentException) {
                NONE
            }
        }

        fun toString(cafeStatus: CafeStatus): String {
            return when (cafeStatus) {
                FREE -> "FREE"
                NORMAL -> "NORMAL"
                CLOSE -> "CLOSE"
                CROWDED -> "CROWDED"
                VERY_CROWDED -> "VERY_CROWDED"
                NONE -> "NONE"
            }
        }
    }
}

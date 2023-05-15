package org.cazait.data.model

enum class CafeStatus {
    FREE,
    NORMAL,
    CLOSE,
    CROWDED,
    VERY_CROWDED,

    NONE;

    companion object {
        fun fromString(value: String): CafeStatus {
            return try {
                valueOf(value)
            } catch (e: IllegalArgumentException) {
                NONE
            }
        }
    }
}
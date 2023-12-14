package org.cazait.core.domain.model.location

import kotlin.math.abs

@JvmInline
value class Longitude(private val longitude: Double) {
    init {
        check(abs(longitude) <= 180) { "경도 절대값은 180 이하이어야 한다." }
    }

    fun toDouble(): Double = longitude

    override fun toString(): String = longitude.toString()
}

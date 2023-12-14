package org.cazait.core.domain.model.location

import kotlin.math.abs

@JvmInline
value class Latitude(private val latitude: Double) {
    init {
        check(abs(latitude) <= 90) { "위도 절대값은 90 이하이어야 한다." }
    }

    fun toDouble(): Double = latitude

    override fun toString(): String = latitude.toString()
}

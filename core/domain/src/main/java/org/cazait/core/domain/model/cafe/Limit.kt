package org.cazait.core.domain.model.cafe

@JvmInline
value class Limit(private val limit: Int) {
    override fun toString(): String = limit.toString()
}

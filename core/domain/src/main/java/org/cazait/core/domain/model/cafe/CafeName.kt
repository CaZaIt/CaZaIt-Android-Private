package org.cazait.core.domain.model.cafe

@JvmInline
value class CafeName(private val cafeName: String) {
    override fun toString(): String {
        return cafeName
    }
}
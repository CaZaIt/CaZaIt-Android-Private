package org.cazait.core.domain.model

@JvmInline
value class Message(private val message: String) {
    override fun toString(): String = message
}

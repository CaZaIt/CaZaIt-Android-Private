package org.cazait.core.domain.model.network

@JvmInline
value class Message(private val message: String) {
    override fun toString(): String = message
}

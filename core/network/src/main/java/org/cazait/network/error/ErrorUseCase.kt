package org.cazait.network.error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}

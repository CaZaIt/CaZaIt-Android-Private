package org.cazait.data.error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}

package com.github.kiolk.overlayview.domain.useCases

import com.github.kiolk.overlayview.domain.model.Category
import com.github.kiolk.overlayview.domain.repository.OverlayRepository
import logcat.asLog
import logcat.logcat

interface GetOverlaysUseCase {

    suspend operator fun invoke(): List<Category>
}

class GetOverlaysUseCaseImpl(private val repository: OverlayRepository) : GetOverlaysUseCase {

    override suspend fun invoke(): List<Category> {
        return try {
            repository.getOverlays()
        } catch (e: Exception) {
            // TODO Implement centralized error handling logic
            logcat { e.asLog() }
            emptyList()
        }
    }
}
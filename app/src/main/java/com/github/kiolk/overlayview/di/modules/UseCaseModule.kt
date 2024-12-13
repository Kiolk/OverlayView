package com.github.kiolk.overlayview.di.modules

import com.github.kiolk.overlayview.domain.useCases.GetOverlaysUseCase
import com.github.kiolk.overlayview.domain.useCases.GetOverlaysUseCaseImpl
import com.github.kiolk.overlayview.domain.useCases.SaveImageUseCase
import com.github.kiolk.overlayview.domain.useCases.SaveImageUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<GetOverlaysUseCase> { GetOverlaysUseCaseImpl(get()) }
    single<SaveImageUseCase> { SaveImageUseCaseImpl(get()) }
}
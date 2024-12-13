package com.github.kiolk.overlayview.di.modules

import com.github.kiolk.overlayview.data.datasources.LocalOverlayDataSource
import com.github.kiolk.overlayview.data.datasources.OverlayDataSource
import com.github.kiolk.overlayview.data.datasources.RemoteOverlayDataSource
import com.github.kiolk.overlayview.data.repository.OverlayRepositoryImpl
import com.github.kiolk.overlayview.domain.repository.OverlayRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val REMOTE = "remote"
const val LOCAL = "local"

val dataModule = module {
    single<OverlayRepository> { OverlayRepositoryImpl(get(named(REMOTE)), get(named(LOCAL))) }
    single<OverlayDataSource>(named(REMOTE)) { RemoteOverlayDataSource(get()) }
    single<OverlayDataSource>(named(LOCAL)) { LocalOverlayDataSource() }
}
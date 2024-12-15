package com.github.kiolk.overlayview.di.modules

import com.github.kiolk.overlayview.ui.screens.compasable.components.OViewModel
import com.github.kiolk.overlayview.ui.screens.view.OverlayViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { OverlayViewModel(get(), get()) }
    viewModel { OViewModel() }
}
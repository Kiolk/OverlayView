package com.github.kiolk.overlayview.ui.screens.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.kiolk.overlayview.domain.model.Overlay
import com.github.kiolk.overlayview.domain.useCases.GetOverlaysUseCase
import com.github.kiolk.overlayview.domain.useCases.SaveImageUseCase
import com.github.kiolk.overlayview.ui.mappers.toCategoryUi
import com.github.kiolk.overlayview.ui.model.CategoryUi
import com.github.kiolk.overlayview.ui.model.OverlayUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import kotlin.random.Random

class OverlayViewModel(
    private val getOverlaysUseCase: GetOverlaysUseCase,
    private val saveImageUseCase: SaveImageUseCase,
) : ViewModel() {

    private val _categories = MutableStateFlow(emptyList<CategoryUi>())
    val categories = _categories.asStateFlow()

    private val _selectedCategory = MutableStateFlow<CategoryUi?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _path = MutableSharedFlow<File>()
    val path = _path.asSharedFlow()

    private val _overlayUi = MutableSharedFlow<OverlayUi>()
    val overlayUi = _overlayUi.asSharedFlow()

    init {
        viewModelScope.launch {
            val result = getOverlaysUseCase().mapIndexed { index, category ->
                category.toCategoryUi(index == DEFAULT_CATEGORY_INDEX)
            }
            _categories.value = result
            result.firstOrNull()?.let {
                _selectedCategory.value = it
            }
        }
    }

    fun onCategoryClick(category: CategoryUi) {
        _selectedCategory.value = category
        val index = _categories.value.indexOf(category)
        val newList = _categories.value.mapIndexed { i, item ->
            item.copy(isSelected = i == index)
        }
        _categories.value = newList
    }

    fun onOverlayClick(overlay: Overlay) {
        viewModelScope.launch(Dispatchers.IO) {
            val path = saveImageUseCase(overlay.sourceUrl)
            path?.let {
                _path.emit(it)
                _overlayUi.emit(OverlayUi(id = Random.nextInt(), overlay, path))
            }
        }
    }

    private companion object {
        const val DEFAULT_CATEGORY_INDEX = 0
    }
}
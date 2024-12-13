package com.github.kiolk.overlayview.ui.screens.view.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.kiolk.overlayview.R
import com.github.kiolk.overlayview.ui.screens.view.OverlayViewModel
import com.github.kiolk.overlayview.ui.screens.view.view.categories.CategoriesAdapter
import com.github.kiolk.overlayview.ui.screens.view.view.categories.DefaultHorizontalItemDecorator
import com.github.kiolk.overlayview.ui.screens.view.view.categories.OverlaysAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class OverlaysBottomSheet(private val onFileSelected: (File) -> Unit) :
    BottomSheetDialogFragment() {

    private val overlayViewModel: OverlayViewModel by viewModel()
    private val categoriesAdapter = CategoriesAdapter {
        overlayViewModel.onCategoryClick(it)
    }
    private val overlaysAdapter = OverlaysAdapter {
        overlayViewModel.onOverlayClick(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            overlayViewModel.categories.collect { categories ->
                categoriesAdapter.addCategories(categories)
            }
        }

        lifecycleScope.launch {
            overlayViewModel.selectedCategory.collect { category ->
                category?.items?.let { overlaysAdapter.addOverlays(it) }
            }
        }

        lifecycleScope.launch {
            overlayViewModel.path.collect {
                onFileSelected(it)
                dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_overlay_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rwCategories = view.findViewById<RecyclerView>(R.id.rw_categories)
        rwCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rwCategories.addItemDecoration(DefaultHorizontalItemDecorator(20))
        rwCategories.adapter = categoriesAdapter
        val rwOverlays = view.findViewById<RecyclerView>(R.id.rw_overlays)
        rwOverlays.layoutManager =
            GridLayoutManager(this.context, 4, GridLayoutManager.VERTICAL, false)
        rwOverlays.adapter = overlaysAdapter
    }

    override fun onStart() {
        super.onStart()

        val dialog = dialog
        if (dialog != null) {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height =
                (resources.displayMetrics.heightPixels * 2 / 3)
        }
    }
}
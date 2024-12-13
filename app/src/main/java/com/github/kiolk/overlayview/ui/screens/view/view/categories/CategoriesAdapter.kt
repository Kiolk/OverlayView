package com.github.kiolk.overlayview.ui.screens.view.view.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.kiolk.overlayview.R
import com.github.kiolk.overlayview.ui.model.CategoryUi

class CategoriesAdapter(
    private val onCategoryClick: (CategoryUi) -> Unit
) :
    RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {
    private val categories = mutableListOf<CategoryUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_layout, parent, false)
        return CategoryViewHolder(view, onCategoryClick)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    fun addCategories(categories: List<CategoryUi>) {
        this.categories.clear()
        this.categories.addAll(categories)
        // TODO Add logic for using payload
        notifyDataSetChanged()
    }

    class CategoryViewHolder(itemView: View, private val onCategoryClick: (CategoryUi) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.iv_category)
        private val categoryName = itemView.findViewById<TextView>(R.id.tv_category)
        private val container = itemView.findViewById<ConstraintLayout>(R.id.cl_category_container)

        fun bind(category: CategoryUi) {
            categoryName.text = category.title
            Glide.with(itemView.context)
                .load(category.thumbnailUrl)
                .into(imageView)

            itemView.setOnClickListener {
                onCategoryClick(category)
            }

            if (category.isSelected) {
                container.background =
                    ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.light_rounded_background,
                        null
                    )
            } else {
                ResourcesCompat.getDrawable(itemView.resources, R.drawable.rounded_background, null)
            }
        }
    }
}
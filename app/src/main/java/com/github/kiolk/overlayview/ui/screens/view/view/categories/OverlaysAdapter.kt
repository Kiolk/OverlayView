package com.github.kiolk.overlayview.ui.screens.view.view.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.kiolk.overlayview.R
import com.github.kiolk.overlayview.domain.model.Overlay

class OverlaysAdapter(private val onOverlayClick: (Overlay) -> Unit) :
    RecyclerView.Adapter<OverlaysAdapter.OverlayViewHolder>() {
    private var overlays = mutableListOf<Overlay>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OverlayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.overlay_layout, parent, false)
        return OverlayViewHolder(view, onOverlayClick)
    }

    override fun getItemCount(): Int {
        return overlays.size
    }

    override fun onBindViewHolder(holder: OverlayViewHolder, position: Int) {
        holder.onBind(overlays[position])
        holder.itemView.isNestedScrollingEnabled = false
    }

    fun addOverlays(overlays: List<Overlay>) {
        this.overlays.clear()
        this.overlays.addAll(overlays)
        // TODO Add logic for calculation differences between lists
        notifyDataSetChanged()
    }

    class OverlayViewHolder(itemView: View, private val onOverlayClick: (Overlay) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.iv_overlay)

        fun onBind(overlay: Overlay) {
            Glide.with(itemView.context)
                .load(overlay.sourceUrl)
                .into(imageView)

            itemView.setOnClickListener {
                onOverlayClick(overlay)
            }
        }
    }
}
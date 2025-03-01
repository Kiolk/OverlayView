package com.github.kiolk.overlayview.ui.screens.view

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.kiolk.overlayview.R
import com.github.kiolk.overlayview.ui.screens.view.view.OverlayView
import com.github.kiolk.overlayview.ui.screens.view.view.OverlaysBottomSheet

class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val overlayView = findViewById<OverlayView>(R.id.overlay_view)

        findViewById<ImageButton>(R.id.btn_add).setOnClickListener {
            val bottomSheet = OverlaysBottomSheet {
                overlayView.addImageFromFile(it)
            }
            bottomSheet.show(supportFragmentManager, BOTTOM_SHEET_TAG)
        }
    }

    private companion object {
        const val BOTTOM_SHEET_TAG = "OverlaysBottomSheet"
    }
}
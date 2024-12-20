package com.plantapphubx.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalSpaceItemDecoration(private val context: Context, private val space: Int) : RecyclerView.ItemDecoration() {
    val metrics = context.resources.displayMetrics

    private var spacingHorizontalPx: Int = 0
    init {
        spacingHorizontalPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, space.toFloat(), metrics
        ).toInt()
    }
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position == 0) {
            outRect.left = spacingHorizontalPx
        }
        outRect.right = spacingHorizontalPx
    }
}
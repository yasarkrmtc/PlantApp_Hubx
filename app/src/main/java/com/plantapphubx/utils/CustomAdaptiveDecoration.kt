package com.plantapphubx.utils

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CustomAdaptiveDecoration(
    context: Context,
    private val spanCount: Int = 1,
    private var spacingHorizontal: Int = 0,
    private var spacingVertical: Int = 0,
    private val includeEdge: Boolean = true
) : RecyclerView.ItemDecoration() {

    private val spacingHorizontalPx: Int
    private val spacingVerticalPx: Int

    init {
        val metrics = context.resources.displayMetrics
        spacingHorizontalPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, spacingHorizontal.toFloat(), metrics
        ).toInt()
        spacingVerticalPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, spacingVertical.toFloat(), metrics
        ).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = spacingHorizontalPx - column * spacingHorizontalPx / spanCount
            outRect.right = (column + 1) * spacingHorizontalPx / spanCount
            if (position < spanCount) { // top edge
                outRect.top = spacingVerticalPx
            }
            outRect.bottom = spacingVerticalPx // item bottom
        } else {
            outRect.left = column * spacingHorizontalPx / spanCount
            outRect.right = spacingHorizontalPx - (column + 1) * spacingHorizontalPx / spanCount
            if (position >= spanCount) {
                outRect.top = spacingVerticalPx // item top
            }
        }
    }
}

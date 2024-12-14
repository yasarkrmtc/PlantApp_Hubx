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
    private var spacingHorizontal: Int? = 0,
    private var endPointSpacing: Int = 0,
    private val spacingVertical: Int,
    private var includeEdge: Boolean = true,
    private var orientation: Int = LinearLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {
    private var spacingHorizontalPx: Int = 0
    private var spacingVerticalPx: Int = 0

    init {
        val metrics = context.resources.displayMetrics
        spacingHorizontalPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, spacingHorizontal!!.toFloat(), metrics
        ).toInt()
        spacingVerticalPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, spacingVertical.toFloat(), metrics
        ).toInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        val itemCount = parent.adapter!!.itemCount

        if (spanCount == 1) {
            when (orientation) {
                LinearLayoutManager.VERTICAL -> {
                    if (position == itemCount - 1) {
                        outRect.bottom =
                            if (endPointSpacing != 0) endPointSpacing else spacingVerticalPx
                    }
                    if (position == 0) {
                        outRect.top =
                            if (endPointSpacing != 0) endPointSpacing else spacingHorizontalPx
                    } else {
                        outRect.top = spacingHorizontalPx
                    }
                    outRect.top = if (endPointSpacing != 0) endPointSpacing else spacingVerticalPx
                    outRect.left = spacingHorizontalPx
                    outRect.right = spacingHorizontalPx
                }

                LinearLayoutManager.HORIZONTAL -> {
                    if (position == itemCount - 1) {
                        outRect.right =
                            if (endPointSpacing != 0) endPointSpacing else spacingHorizontalPx
                    }
                    if (position == 0) {
                        outRect.left =
                            if (endPointSpacing != 0) endPointSpacing else spacingHorizontalPx
                    } else {
                        outRect.left = spacingHorizontalPx
                    }
                    outRect.top = spacingVerticalPx

                }
            }

        } else {
            if (includeEdge) {
                outRect.left = spacingHorizontalPx - column * spacingHorizontalPx / spanCount
                outRect.right = (column + 1) * spacingHorizontalPx / spanCount

                if (position < spanCount) {
                    outRect.top = if (endPointSpacing != 0) endPointSpacing else spacingVerticalPx
                }
                outRect.bottom = if (endPointSpacing != 0) endPointSpacing else spacingVerticalPx
            } else {
                outRect.left = column * spacingHorizontalPx / spanCount
                outRect.right = spacingHorizontalPx - (column + 1) * spacingHorizontalPx / spanCount
                if (position >= spanCount) {
                    outRect.top = spacingVerticalPx
                }
            }
        }
    }
}
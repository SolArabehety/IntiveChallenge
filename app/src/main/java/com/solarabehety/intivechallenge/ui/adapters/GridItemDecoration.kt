package com.solarabehety.intivechallenge.ui.adapters

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Sol Arabehety on 6/17/2018.
 */
class GridItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    val COLUMN_COUNT = 2

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {

        if (parent.getChildLayoutPosition(view) % COLUMN_COUNT == 0) {

            outRect.right = space / 2
            outRect.left = space
        } else {
            outRect.left = space / 2
            outRect.right = space
        }

        outRect.bottom = space

        if (parent.getChildLayoutPosition(view) < COLUMN_COUNT) {
            outRect.top = space
        } else {
            outRect.top = 0
        }
    }
}
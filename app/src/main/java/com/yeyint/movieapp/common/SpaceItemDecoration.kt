package com.yeyint.movieapp.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import java.lang.IllegalStateException


class SpaceItemDecoration @JvmOverloads constructor(
    private val space: Int,
    private val gridCount : Int,
    private val addSpaceAboveFirstItem: Boolean = DEFAULT_ADD_SPACE_ABOVE_FIRST_ITEM,
    private val addSpaceBelowLastItem: Boolean = DEFAULT_ADD_SPACE_BELOW_LAST_ITEM
) :
    ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) < gridCount) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }

    private fun getTotalItemCount(parent: RecyclerView): Int {
        return parent.adapter!!.itemCount
    }

    private fun getOrientation(parent: RecyclerView): Int {
        return if (parent.layoutManager is LinearLayoutManager) {
            (parent.layoutManager as LinearLayoutManager?)!!.orientation
        } else {
            throw IllegalStateException(
                "SpaceItemDecoration can only be used with a LinearLayoutManager."
            )
        }
    }

    companion object {
        private const val DEFAULT_ADD_SPACE_ABOVE_FIRST_ITEM = false
        private const val DEFAULT_ADD_SPACE_BELOW_LAST_ITEM = false
    }
}
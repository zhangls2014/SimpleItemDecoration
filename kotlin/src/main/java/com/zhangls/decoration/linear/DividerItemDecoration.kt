package com.zhangls.decoration.linear

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Adds interior dividers to a RecyclerView with a LinearLayoutManager or its
 * subclass.
 *
 *
 * 支持设置 margin 属性
 *
 * @author zhangls
 */
class DividerItemDecoration(private val divider: Drawable, private val margin: Int = 0) : RecyclerView.ItemDecoration() {
    private var orientation: Int = LinearLayoutManager.HORIZONTAL

    /**
     * Determines the size and location of offsets between items in the parent
     * RecyclerView.
     *
     * @param outRect The [Rect] of offsets to be added around the child
     * view
     * @param view    The child view to be decorated with an offset
     * @param parent  The RecyclerView onto which dividers are being added
     * @param state   The current RecyclerView.State of the RecyclerView
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) == 0) {
            return
        }

        if (parent.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = parent.layoutManager as LinearLayoutManager
            orientation = linearLayoutManager.orientation
        } else {
            throw UnsupportedOperationException("DividerItemDecoration 只适用于LinearLayoutManager")
        }

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.left = divider.intrinsicWidth
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.top = divider.intrinsicHeight
        }
    }

    /**
     * Draws horizontal or vertical dividers onto the parent RecyclerView.
     *
     * @param canvas The [Canvas] onto which dividers will be drawn
     * @param parent The RecyclerView onto which dividers are being added
     * @param state  The current RecyclerView.State of the RecyclerView
     */
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawHorizontalDividers(canvas, parent)
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            drawVerticalDividers(canvas, parent)
        }
    }

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager or its
     * subclass oriented horizontally.
     *
     * @param canvas The [Canvas] onto which horizontal dividers will be
     * drawn
     * @param parent The RecyclerView onto which horizontal dividers are being
     * added
     */
    private fun drawHorizontalDividers(canvas: Canvas, parent: RecyclerView) {
        val parentTop = parent.paddingTop + margin
        val parentBottom = parent.height - parent.paddingBottom - margin

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val parentLeft = child.right + params.rightMargin
            val parentRight = parentLeft + divider.intrinsicWidth

            divider.setBounds(parentLeft, parentTop, parentRight, parentBottom)
            divider.draw(canvas)
        }
    }

    /**
     * Adds dividers to a RecyclerView with a LinearLayoutManager or its
     * subclass oriented vertically.
     *
     * @param canvas The [Canvas] onto which vertical dividers will be
     * drawn
     * @param parent The RecyclerView onto which vertical dividers are being
     * added
     */
    private fun drawVerticalDividers(canvas: Canvas, parent: RecyclerView) {
        val parentLeft = parent.paddingLeft + margin
        val parentRight = parent.width - parent.paddingRight - margin

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val parentTop = child.bottom + params.bottomMargin
            val parentBottom = parentTop + divider.intrinsicHeight

            divider.setBounds(parentLeft, parentTop, parentRight, parentBottom)
            divider.draw(canvas)
        }
    }
}

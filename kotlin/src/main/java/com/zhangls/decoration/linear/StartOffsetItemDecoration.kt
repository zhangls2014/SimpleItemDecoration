package com.zhangls.decoration.linear

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Adds an offset to the start of a RecyclerView using a LinearLayoutManager or
 * its subclass.
 *
 *
 * If the RecyclerView.LayoutManager is oriented vertically, the offset will be
 * added to the top of the RecyclerView. If the LayoutManager is oriented
 * horizontally, the offset will be added to the left of the RecyclerView.
 *
 *
 * 支持设置 margin 属性
 *
 * @author zhangls
 */
class StartOffsetItemDecoration(private val divider: Drawable, private val margin: Int = 0) : RecyclerView.ItemDecoration() {
    private var orientation: Int = LinearLayoutManager.HORIZONTAL

    /**
     * Determines the size and location of the offset to be added to the start
     * of the RecyclerView.
     *
     * @param outRect The [Rect] of offsets to be added around the child view
     * @param view    The child view to be decorated with an offset
     * @param parent  The RecyclerView onto which dividers are being added
     * @param state   The current RecyclerView.State of the RecyclerView
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) > 0) {
            return
        }

        if (parent.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = parent.layoutManager as LinearLayoutManager
            orientation = linearLayoutManager.orientation
        } else {
            throw UnsupportedOperationException("StartOffsetItemDecoration 只适用于LinearLayoutManager")
        }

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.left = divider.intrinsicWidth
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.top = divider.intrinsicHeight
        }
    }

    /**
     * Draws horizontal or vertical offset onto the start of the parent
     * RecyclerView.
     *
     * @param c      The [Canvas] onto which an offset will be drawn
     * @param parent The RecyclerView onto which an offset is being added
     * @param state  The current RecyclerView.State of the RecyclerView
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            drawOffsetHorizontal(c, parent)
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            drawOffsetVertical(c, parent)
        }
    }


    private fun drawOffsetHorizontal(canvas: Canvas, parent: RecyclerView) {
        val parentTop = parent.paddingTop + margin
        val parentBottom = parent.height - parent.paddingBottom - margin
        val parentLeft = parent.paddingLeft
        val offsetDrawableRight = parentLeft + divider.intrinsicWidth

        divider.setBounds(parentLeft, parentTop, offsetDrawableRight, parentBottom)
        divider.draw(canvas)
    }

    private fun drawOffsetVertical(canvas: Canvas, parent: RecyclerView) {
        val parentLeft = parent.paddingLeft + margin
        val parentRight = parent.width - parent.paddingRight - margin
        val parentTop = parent.paddingTop
        val offsetDrawableBottom = parentTop + divider.intrinsicHeight

        divider.setBounds(parentLeft, parentTop, parentRight, offsetDrawableBottom)
        divider.draw(canvas)
    }
}

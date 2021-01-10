package com.zhangls.decoration.linear

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Adds an offset to the end of a RecyclerView using a LinearLayoutManager or
 * its subclass.
 *
 *
 * If the RecyclerView.LayoutManager is oriented vertically, the offset will be
 * added to the bottom of the RecyclerView. If the LayoutManager is oriented
 * horizontally, the offset will be added to the right of the RecyclerView.
 *
 *
 * 支持设置 margin 属性
 *
 * @author zhangls
 */
class EndOffsetItemDecoration(private val divider: Drawable, private val margin: Int = 0) : RecyclerView.ItemDecoration() {
    private var orientation: Int = LinearLayoutManager.HORIZONTAL

    /**
     * Determines the size and location of the offset to be added to the end
     * of the RecyclerView.
     *
     * @param outRect The [Rect] of offsets to be added around the child view
     * @param view    The child view to be decorated with an offset
     * @param parent  The RecyclerView onto which dividers are being added
     * @param state   The current RecyclerView.State of the RecyclerView
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val itemCount = state.itemCount
        if (parent.getChildAdapterPosition(view) != itemCount - 1) {
            return
        }

        if (parent.layoutManager is LinearLayoutManager) {
            val linearLayoutManager = parent.layoutManager as LinearLayoutManager
            orientation = linearLayoutManager.orientation
        } else {
            throw UnsupportedOperationException("DividerItemDecoration 只适用于LinearLayoutManager")
        }
        
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            outRect.right = divider.intrinsicWidth
        } else if (orientation == LinearLayoutManager.VERTICAL) {
            outRect.bottom = divider.intrinsicHeight
        }
    }

    /**
     * Draws horizontal or vertical offset onto the end of the parent
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

        val lastChild = parent.getChildAt(parent.childCount - 1)
        val lastChildLayoutParams = lastChild.layoutParams as RecyclerView.LayoutParams
        val offsetDrawableLeft = lastChild.right + lastChildLayoutParams.rightMargin
        val offsetDrawableRight = offsetDrawableLeft + divider.intrinsicWidth

        divider.setBounds(offsetDrawableLeft, parentTop, offsetDrawableRight, parentBottom)
        divider.draw(canvas)
    }

    private fun drawOffsetVertical(canvas: Canvas, parent: RecyclerView) {
        val parentLeft = parent.paddingLeft + margin
        val parentRight = parent.width - parent.paddingRight - margin

        val lastChild = parent.getChildAt(parent.childCount - 1)
        val lastChildLayoutParams = lastChild.layoutParams as RecyclerView.LayoutParams
        val offsetDrawableTop = lastChild.bottom + lastChildLayoutParams.bottomMargin
        val offsetDrawableBottom = offsetDrawableTop + divider.intrinsicHeight

        divider.setBounds(parentLeft, offsetDrawableTop, parentRight, offsetDrawableBottom)
        divider.draw(canvas)
    }
}

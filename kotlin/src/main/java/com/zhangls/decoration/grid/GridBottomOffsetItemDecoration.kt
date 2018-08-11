package com.zhangls.decoration.grid

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View

import androidx.recyclerview.widget.RecyclerView

/**
 * Adds an offset to the bottom of a RecyclerView with a GridLayoutManager or
 * its subclass.
 *
 *
 * 支持设置 margin 属性
 *
 * @author zhangls
 */
class GridBottomOffsetItemDecoration : RecyclerView.ItemDecoration {

    private var mOffsetDrawable: Drawable? = null
    private var mNumColumns: Int = 0

    /**
     * Constructor that takes in the size of the offset to be added to the
     * bottom of the RecyclerView.
     *
     * @param numColumns The number of columns in the grid of the RecyclerView
     */
    constructor(numColumns: Int) {
        mNumColumns = numColumns
    }

    /**
     * Constructor that takes in a [Drawable] to be drawn at the bottom
     * of the RecyclerView.
     *
     * @param offsetDrawable The `Drawable` to be added to the bottom of
     * the RecyclerView
     * @param numColumns     The number of columns in the grid of the RecyclerView
     */
    constructor(offsetDrawable: Drawable, numColumns: Int) {
        mOffsetDrawable = offsetDrawable
        mNumColumns = numColumns
    }

    /**
     * Determines the size and the location of the offset to be added to the
     * bottom of the RecyclerView.
     *
     * @param outRect The [Rect] of offsets to be added around the child view
     * @param view    The child view to be decorated with an offset
     * @param parent  The RecyclerView onto which dividers are being added
     * @param state   The current RecyclerView.State of the RecyclerView
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val childCount = state.itemCount
        val lastRowChildCount = getLastRowChildCount(childCount)

        val childIsInBottomRow = parent.getChildAdapterPosition(view) >= childCount - lastRowChildCount
        if (childIsInBottomRow) {
            outRect.bottom = mOffsetDrawable!!.intrinsicHeight
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (mOffsetDrawable == null) {
            return
        }

        val childCount = state.itemCount
        val lastRowChildCount = getLastRowChildCount(childCount)

        val parentLeft = parent.paddingLeft
        val parentRight = parent.width - parent.paddingRight
        var offsetDrawableTop = 0
        var offsetDrawableBottom = 0

        for (i in childCount - lastRowChildCount until childCount) {
            val child = parent.getChildAt(i)
            offsetDrawableTop = child.bottom
            offsetDrawableBottom = offsetDrawableTop + mOffsetDrawable!!.intrinsicHeight
        }

        mOffsetDrawable!!.setBounds(parentLeft, offsetDrawableTop, parentRight, offsetDrawableBottom)
        mOffsetDrawable!!.draw(c)
    }

    private fun getLastRowChildCount(itemCount: Int): Int {
        var lastRowChildCount = itemCount % mNumColumns
        if (lastRowChildCount == 0) {
            lastRowChildCount = mNumColumns
        }

        return lastRowChildCount
    }
}

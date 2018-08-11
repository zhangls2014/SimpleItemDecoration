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
class EndOffsetItemDecoration : RecyclerView.ItemDecoration {

    private var mOffsetDrawable: Drawable? = null
    private var mOrientation: Int = 0
    /**
     * 边距
     */
    private var mMargin: Int = 0

    /**
     * Constructor that takes in a [Drawable] to be drawn at the end of
     * the RecyclerView.
     *
     * @param offsetDrawable The `Drawable` to be added to the end of the
     * RecyclerView
     */
    constructor(offsetDrawable: Drawable) {
        mOffsetDrawable = offsetDrawable
    }

    /**
     * Constructor that takes in a [Drawable] to be drawn at the end of
     * the RecyclerView.
     *
     * @param offsetDrawable The `Drawable` to be added to the end of the
     * RecyclerView
     * @param margin         边距
     */
    constructor(offsetDrawable: Drawable, margin: Int) {
        mOffsetDrawable = offsetDrawable
        mMargin = margin
    }

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

        mOrientation = (parent.layoutManager as LinearLayoutManager).orientation
        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            if (mOffsetDrawable != null) {
                outRect.right = mOffsetDrawable!!.intrinsicWidth
            }
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            if (mOffsetDrawable != null) {
                outRect.bottom = mOffsetDrawable!!.intrinsicHeight
            }
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
        super.onDraw(c, parent, state)
        if (mOffsetDrawable == null) {
            return
        }

        if (mOrientation == LinearLayoutManager.HORIZONTAL) {
            drawOffsetHorizontal(c, parent)
        } else if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawOffsetVertical(c, parent)
        }
    }

    private fun drawOffsetHorizontal(canvas: Canvas, parent: RecyclerView) {
        val parentTop = parent.paddingTop + mMargin
        val parentBottom = parent.height - parent.paddingBottom - mMargin

        val lastChild = parent.getChildAt(parent.childCount - 1)
        val lastChildLayoutParams = lastChild.layoutParams as RecyclerView.LayoutParams
        val offsetDrawableLeft = lastChild.right + lastChildLayoutParams.rightMargin
        val offsetDrawableRight = offsetDrawableLeft + mOffsetDrawable!!.intrinsicWidth

        mOffsetDrawable!!.setBounds(offsetDrawableLeft, parentTop, offsetDrawableRight, parentBottom)
        mOffsetDrawable!!.draw(canvas)
    }

    private fun drawOffsetVertical(canvas: Canvas, parent: RecyclerView) {
        val parentLeft = parent.paddingLeft + mMargin
        val parentRight = parent.width - parent.paddingRight - mMargin

        val lastChild = parent.getChildAt(parent.childCount - 1)
        val lastChildLayoutParams = lastChild.layoutParams as RecyclerView.LayoutParams
        val offsetDrawableTop = lastChild.bottom + lastChildLayoutParams.bottomMargin
        val offsetDrawableBottom = offsetDrawableTop + mOffsetDrawable!!.intrinsicHeight

        mOffsetDrawable!!.setBounds(parentLeft, offsetDrawableTop, parentRight, offsetDrawableBottom)
        mOffsetDrawable!!.draw(canvas)
    }
}

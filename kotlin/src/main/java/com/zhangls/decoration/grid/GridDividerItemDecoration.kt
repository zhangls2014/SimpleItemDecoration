package com.zhangls.decoration.grid

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Adds interior dividers to a RecyclerView with a GridLayoutManager.
 *
 *
 * 支持设置 margin 属性
 *
 * @author zhangls
 */
class GridDividerItemDecoration : RecyclerView.ItemDecoration {

    private var mHorizontalDivider: Drawable? = null
    private var mVerticalDivider: Drawable? = null
    private var mNumColumns: Int = 0
    /**
     * 边距
     */
    private var mMarginHorizontal: Int = 0
    /**
     * 边距
     */
    private var mMarginVertical: Int = 0

    /**
     * Sole constructor. Takes in [Drawable] objects to be used as
     * horizontal and vertical dividers.
     *
     * @param horizontalDivider A divider `Drawable` to be drawn on the
     * rows of the grid of the RecyclerView
     * @param verticalDivider   A divider `Drawable` to be drawn on the
     * columns of the grid of the RecyclerView
     * @param numColumns        The number of columns in the grid of the RecyclerView
     */
    constructor(horizontalDivider: Drawable, verticalDivider: Drawable, numColumns: Int) {
        mHorizontalDivider = horizontalDivider
        mVerticalDivider = verticalDivider
        mNumColumns = numColumns
    }

    /**
     * Sole constructor. Takes in [Drawable] objects to be used as
     * horizontal and vertical dividers.
     *
     * @param horizontalDivider A divider `Drawable` to be drawn on the
     * rows of the grid of the RecyclerView
     * @param verticalDivider   A divider `Drawable` to be drawn on the
     * columns of the grid of the RecyclerView
     * @param marginHorizontal  水平分割线的左右边距
     * @param marginVertical    垂直分割线的上下边距
     * @param numColumns        The number of columns in the grid of the RecyclerView
     */
    constructor(horizontalDivider: Drawable, verticalDivider: Drawable,
                marginHorizontal: Int, marginVertical: Int, numColumns: Int) {
        mHorizontalDivider = horizontalDivider
        mVerticalDivider = verticalDivider
        mMarginHorizontal = marginHorizontal
        mMarginVertical = marginVertical
        mNumColumns = numColumns
    }

    /**
     * Draws horizontal and/or vertical dividers onto the parent RecyclerView.
     *
     * @param canvas The [Canvas] onto which dividers will be drawn
     * @param parent The RecyclerView onto which dividers are being added
     * @param state  The current RecyclerView.State of the RecyclerView
     */
    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawHorizontalDividers(canvas, parent)
        drawVerticalDividers(canvas, parent)
    }

    /**
     * Determines the size and location of offsets between items in the parent
     * RecyclerView.
     *
     * @param outRect The [Rect] of offsets to be added around the child view
     * @param view    The child view to be decorated with an offset
     * @param parent  The RecyclerView onto which dividers are being added
     * @param state   The current RecyclerView.State of the RecyclerView
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        // item 的 Position
        val index = parent.getChildAdapterPosition(view)

        // 添加纵向偏移量
        // item 的列号
        val row = index % mNumColumns
        if (row != mNumColumns - 1) {
            outRect.right = mVerticalDivider!!.intrinsicWidth
        }

        // 添加横向偏移量
        // item 的行号
        val column = index / mNumColumns
        if (column > 0) {
            outRect.top = mHorizontalDivider!!.intrinsicHeight
        }
    }

    /**
     * Adds horizontal dividers to a RecyclerView with a GridLayoutManager or its
     * subclass.
     *
     * @param canvas The [Canvas] onto which dividers will be drawn
     * @param parent The RecyclerView onto which dividers are being added
     */
    private fun drawHorizontalDividers(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        val numChildrenOnLastRow = childCount % mNumColumns

        val overNum: Int
        overNum = if (numChildrenOnLastRow == 0) {
            mNumColumns
        } else {
            numChildrenOnLastRow
        }

        for (i in 0 until childCount - overNum) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + mHorizontalDivider!!.intrinsicHeight

            val left = child.left + mMarginHorizontal
            val right = child.right - mMarginHorizontal

            mHorizontalDivider!!.setBounds(left, top, right, bottom)
            mHorizontalDivider!!.draw(canvas)
        }
    }

    /**
     * Adds vertical dividers to a RecyclerView with a GridLayoutManager or its
     * subclass.
     *
     * @param canvas The [Canvas] onto which dividers will be drawn
     * @param parent The RecyclerView onto which dividers are being added
     */
    private fun drawVerticalDividers(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        val numChildrenOnLastRow = childCount % mNumColumns
        val rowCount: Int
        rowCount = if (numChildrenOnLastRow == 0) {
            childCount / mNumColumns
        } else {
            childCount / mNumColumns + 1
        }

        // 行号遍历
        for (i in 0..rowCount) {
            // 列号遍历
            for (j in 0 until mNumColumns - 1) {
                val index = i * mNumColumns + j
                if (index + 1 >= childCount) {
                    continue
                }
                val child = parent.getChildAt(index)
                val params = child.layoutParams as RecyclerView.LayoutParams

                val left = child.right + params.rightMargin
                val right = left + mVerticalDivider!!.intrinsicWidth

                val top = child.top + mMarginVertical
                val bottom = child.bottom - mMarginVertical

                mVerticalDivider!!.setBounds(left, top, right, bottom)
                mVerticalDivider!!.draw(canvas)
            }
        }
    }
}

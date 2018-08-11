package cn.zhangls.decoration.grid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adds interior dividers to a RecyclerView with a GridLayoutManager.
 * <p>
 * 支持设置 margin 属性
 *
 * @author zhangls
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

  private Drawable mHorizontalDivider;
  private Drawable mVerticalDivider;
  private int mNumColumns;
  /**
   * 边距
   */
  private int mMarginHorizontal;
  /**
   * 边距
   */
  private int mMarginVertical;

  /**
   * Sole constructor. Takes in {@link Drawable} objects to be used as
   * horizontal and vertical dividers.
   *
   * @param horizontalDivider A divider {@code Drawable} to be drawn on the
   *                          rows of the grid of the RecyclerView
   * @param verticalDivider   A divider {@code Drawable} to be drawn on the
   *                          columns of the grid of the RecyclerView
   * @param numColumns        The number of columns in the grid of the RecyclerView
   */
  public GridDividerItemDecoration(Drawable horizontalDivider, Drawable verticalDivider, int numColumns) {
    mHorizontalDivider = horizontalDivider;
    mVerticalDivider = verticalDivider;
    mNumColumns = numColumns;
  }

  /**
   * Sole constructor. Takes in {@link Drawable} objects to be used as
   * horizontal and vertical dividers.
   *
   * @param horizontalDivider A divider {@code Drawable} to be drawn on the
   *                          rows of the grid of the RecyclerView
   * @param verticalDivider   A divider {@code Drawable} to be drawn on the
   *                          columns of the grid of the RecyclerView
   * @param marginHorizontal  水平分割线的左右边距
   * @param marginVertical    垂直分割线的上下边距
   * @param numColumns        The number of columns in the grid of the RecyclerView
   */
  public GridDividerItemDecoration(Drawable horizontalDivider, Drawable verticalDivider,
                                   int marginHorizontal, int marginVertical, int numColumns) {
    mHorizontalDivider = horizontalDivider;
    mVerticalDivider = verticalDivider;
    mMarginHorizontal = marginHorizontal;
    mMarginVertical = marginVertical;
    mNumColumns = numColumns;
  }

  /**
   * Draws horizontal and/or vertical dividers onto the parent RecyclerView.
   *
   * @param canvas The {@link Canvas} onto which dividers will be drawn
   * @param parent The RecyclerView onto which dividers are being added
   * @param state  The current RecyclerView.State of the RecyclerView
   */
  @Override
  public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent,
                     @NonNull RecyclerView.State state) {
    drawHorizontalDividers(canvas, parent);
    drawVerticalDividers(canvas, parent);
  }

  /**
   * Determines the size and location of offsets between items in the parent
   * RecyclerView.
   *
   * @param outRect The {@link Rect} of offsets to be added around the child view
   * @param view    The child view to be decorated with an offset
   * @param parent  The RecyclerView onto which dividers are being added
   * @param state   The current RecyclerView.State of the RecyclerView
   */
  @Override
  public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                             @NonNull RecyclerView parent,
                             @NonNull RecyclerView.State state) {
    super.getItemOffsets(outRect, view, parent, state);
    // item 的 Position
    int index = parent.getChildAdapterPosition(view);

    // 添加纵向偏移量
    // item 的列号
    int row = (index) % mNumColumns;

    if (row != mNumColumns - 1) {
      outRect.right = mVerticalDivider.getIntrinsicWidth();
    }

    // 添加横向偏移量
    // item 的行号
    int column = index / mNumColumns;
    if (column > 0) {
      outRect.top = mHorizontalDivider.getIntrinsicHeight();
    }
  }

  /**
   * Adds horizontal dividers to a RecyclerView with a GridLayoutManager or its
   * subclass.
   *
   * @param canvas The {@link Canvas} onto which dividers will be drawn
   * @param parent The RecyclerView onto which dividers are being added
   */
  private void drawHorizontalDividers(Canvas canvas, RecyclerView parent) {
    int childCount = parent.getChildCount();
    int numChildrenOnLastRow = childCount % mNumColumns;

    int overNum;
    if (numChildrenOnLastRow == 0) {
      overNum = mNumColumns;
    } else {
      overNum = numChildrenOnLastRow;
    }

    for (int i = 0; i < childCount - overNum; i++) {
      View child = parent.getChildAt(i);
      RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

      int top = child.getBottom() + params.bottomMargin;
      int bottom = top + mHorizontalDivider.getIntrinsicHeight();

      int left = child.getLeft() + mMarginHorizontal;
      int right = child.getRight() - mMarginHorizontal;

      mHorizontalDivider.setBounds(left, top, right, bottom);
      mHorizontalDivider.draw(canvas);
    }
  }

  /**
   * Adds vertical dividers to a RecyclerView with a GridLayoutManager or its
   * subclass.
   *
   * @param canvas The {@link Canvas} onto which dividers will be drawn
   * @param parent The RecyclerView onto which dividers are being added
   */
  private void drawVerticalDividers(Canvas canvas, RecyclerView parent) {
    int childCount = parent.getChildCount();
    int numChildrenOnLastRow = childCount % mNumColumns;
    int rowCount;
    if (numChildrenOnLastRow == 0) {
      rowCount = childCount / mNumColumns;
    } else {
      rowCount = childCount / mNumColumns + 1;
    }

    // 行号遍历
    for (int i = 0; i <= rowCount; i++) {
      // 列号遍历
      for (int j = 0; j < mNumColumns - 1; j++) {
        int index = i * mNumColumns + j;
        if (index + 1 >= childCount) {
          continue;
        }
        View child = parent.getChildAt(index);
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

        int left = child.getRight() + params.rightMargin;
        int right = left + mVerticalDivider.getIntrinsicWidth();

        int top = child.getTop() + mMarginVertical;
        int bottom = child.getBottom() - mMarginVertical;

        mVerticalDivider.setBounds(left, top, right, bottom);
        mVerticalDivider.draw(canvas);
      }
    }
  }
}

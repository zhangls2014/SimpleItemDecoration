package cn.zhangls.decoration.grid;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adds an offset to the top of a RecyclerView with a GridLayoutManager or its
 * subclass.
 */
public class GridTopOffsetItemDecoration extends RecyclerView.ItemDecoration {

  private Drawable mOffsetDrawable;
  private int mNumColumns;

  /**
   * Constructor that takes in the size of the offset to be added to the top
   * of the RecyclerView.
   *
   * @param numColumns The number of columns in the grid of the RecyclerView
   */
  public GridTopOffsetItemDecoration(int numColumns) {
    mNumColumns = numColumns;
  }

  /**
   * Constructor that takes in a {@link Drawable} to be drawn at the top of
   * the RecyclerView.
   *
   * @param offsetDrawable The {@code Drawable} to be added to the top of the
   *                       RecyclerView
   * @param numColumns     The number of columns in the grid of the RecyclerView
   */
  public GridTopOffsetItemDecoration(Drawable offsetDrawable, int numColumns) {
    mOffsetDrawable = offsetDrawable;
    mNumColumns = numColumns;
  }

  /**
   * Determines the size and the location of the offset to be added to the
   * top of the RecyclerView.
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

    boolean childIsInTopRow = parent.getChildAdapterPosition(view) < mNumColumns;
    if (childIsInTopRow) {
      if (mOffsetDrawable != null) {
        outRect.top = mOffsetDrawable.getIntrinsicHeight();
      }
    }
  }

  @Override
  public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
                     @NonNull RecyclerView.State state) {
    super.onDraw(c, parent, state);
    if (mOffsetDrawable == null) {
      return;
    }

    int parentLeft = parent.getPaddingLeft();
    int parentRight = parent.getWidth() - parent.getPaddingRight();
    int parentTop = parent.getPaddingTop();
    int offsetDrawableBottom = parentTop + mOffsetDrawable.getIntrinsicHeight();

    mOffsetDrawable.setBounds(parentLeft, parentTop, parentRight, offsetDrawableBottom);
    mOffsetDrawable.draw(c);
  }
}
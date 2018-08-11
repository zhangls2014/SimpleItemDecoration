package cn.zhangls.decoration.linear;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adds an offset to the start of a RecyclerView using a LinearLayoutManager or
 * its subclass.
 * <p>
 * If the RecyclerView.LayoutManager is oriented vertically, the offset will be
 * added to the top of the RecyclerView. If the LayoutManager is oriented
 * horizontally, the offset will be added to the left of the RecyclerView.
 * <p>
 * 支持设置 margin 属性
 *
 * @author zhangls
 */
public class StartOffsetItemDecoration extends RecyclerView.ItemDecoration {

  private Drawable mOffsetDrawable;
  private int mOrientation;
  /**
   * 边距
   */
  private int mMargin;

  /**
   * Constructor that takes in a {@link Drawable} to be drawn at the start of
   * the RecyclerView.
   *
   * @param offsetDrawable The {@code Drawable} to be added to the start of
   *                       the RecyclerView
   */
  public StartOffsetItemDecoration(Drawable offsetDrawable) {
    mOffsetDrawable = offsetDrawable;
  }

  /**
   * Constructor that takes in a {@link Drawable} to be drawn at the start of
   * the RecyclerView.
   *
   * @param offsetDrawable The {@code Drawable} to be added to the start of
   *                       the RecyclerView
   * @param margin         边距
   */
  public StartOffsetItemDecoration(Drawable offsetDrawable, int margin) {
    mOffsetDrawable = offsetDrawable;
    mMargin = margin;
  }

  /**
   * Determines the size and location of the offset to be added to the start
   * of the RecyclerView.
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
    if (parent.getChildAdapterPosition(view) > 0) {
      return;
    }

    if (parent.getLayoutManager() instanceof LinearLayoutManager) {
      mOrientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
      if (mOrientation == LinearLayoutManager.HORIZONTAL) {
        if (mOffsetDrawable != null) {
          outRect.left = mOffsetDrawable.getIntrinsicWidth();
        }
      } else if (mOrientation == LinearLayoutManager.VERTICAL) {
        if (mOffsetDrawable != null) {
          outRect.top = mOffsetDrawable.getIntrinsicHeight();
        }
      }
    }
  }

  /**
   * Draws horizontal or vertical offset onto the start of the parent
   * RecyclerView.
   *
   * @param c      The {@link Canvas} onto which an offset will be drawn
   * @param parent The RecyclerView onto which an offset is being added
   * @param state  The current RecyclerView.State of the RecyclerView
   */
  @Override
  public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent,
                     @NonNull RecyclerView.State state) {
    super.onDraw(c, parent, state);
    if (mOffsetDrawable == null) {
      return;
    }

    if (mOrientation == LinearLayoutManager.HORIZONTAL) {
      drawOffsetHorizontal(c, parent);
    } else if (mOrientation == LinearLayoutManager.VERTICAL) {
      drawOffsetVertical(c, parent);
    }
  }


  private void drawOffsetHorizontal(Canvas canvas, RecyclerView parent) {
    int parentTop = parent.getPaddingTop() + mMargin;
    int parentBottom = parent.getHeight() - parent.getPaddingBottom() - mMargin;
    int parentLeft = parent.getPaddingLeft();
    int offsetDrawableRight = parentLeft + mOffsetDrawable.getIntrinsicWidth();

    mOffsetDrawable.setBounds(parentLeft, parentTop, offsetDrawableRight, parentBottom);
    mOffsetDrawable.draw(canvas);
  }

  private void drawOffsetVertical(Canvas canvas, RecyclerView parent) {
    int parentLeft = parent.getPaddingLeft() + mMargin;
    int parentRight = parent.getWidth() - parent.getPaddingRight() - mMargin;
    int parentTop = parent.getPaddingTop();
    int offsetDrawableBottom = parentTop + mOffsetDrawable.getIntrinsicHeight();

    mOffsetDrawable.setBounds(parentLeft, parentTop, parentRight, offsetDrawableBottom);
    mOffsetDrawable.draw(canvas);
  }
}
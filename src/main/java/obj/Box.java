package obj;

import gui.core.BoxRegion;
import gui.core.GuiUtil;
import java.util.Objects;

public class Box {

  private final Dimension dimension;
  private int top;
  private int left;
  private int right;
  private int bottom;
  private boolean changed;

  public Box() {
    dimension = new Dimension();
  }

  public Box(Dimension dimension) {
    this.dimension = dimension;
  }

  public Box(Dimension dimension, int top, int left, int right, int bottom) {
    this.dimension = dimension;
    this.top = top;
    this.left = left;
    this.right = right;
    this.bottom = bottom;
  }

  // Methods

  public void apply(Box box) {
    if (box == null || this.equals(box)) {
      return;
    }
    setDimension(box.getDimension());
    setTop(box.getTop());
    setLeft(box.getLeft());
    setRight(box.getRight());
    setBottom(box.getBottom());
  }

  public BoxRegion getRegion(int x, int y) {
    int widthSection, heightSection;

    if (GuiUtil.isInsideRange(x, 0, left)) {
      widthSection = 0;
    } else if (GuiUtil.isInsideRange(x, left, getWidth() - right)) {
      widthSection = 1;
    } else if (GuiUtil.isInsideRange(x, getWidth() - right, getWidth())) {
      widthSection = 2;
    } else {
      widthSection = -1;
    }

    if (GuiUtil.isInsideRange(y, 0, top)) {
      heightSection = 0;
    } else if (GuiUtil.isInsideRange(y, top, getHeight() - bottom)) {
      heightSection = 1;
    } else if (GuiUtil.isInsideRange(y, getHeight() - bottom, getHeight())) {
      heightSection = 2;
    } else {
      heightSection = -1;
    }

    if (widthSection != -1 && heightSection != -1) {
      int section = heightSection * 3 + widthSection;
      return switch (section) {
        case 0 -> BoxRegion.TOP_LEFT;
        case 1 -> BoxRegion.TOP;
        case 2 -> BoxRegion.TOP_RIGHT;
        case 3 -> BoxRegion.LEFT;
        case 4 -> BoxRegion.CENTER;
        case 5 -> BoxRegion.RIGHT;
        case 6 -> BoxRegion.BOTTOM_LEFT;
        case 7 -> BoxRegion.BOTTOM;
        case 8 -> BoxRegion.BOTTOM_RIGHT;
        default -> null;
      };
    }
    return null;
  }

  public void expandFor(Box box) {
    setDimension(
        getLeft() + getRight() + box.getWidth(),
        getTop() + getBottom() + box.getHeight()
    );
  }

  public void shrinkFor(Box box) {
    setDimension(
        getWidth() - box.getLeft() + box.getRight(),
        getHeight() - box.getTop() + box.getBottom()
    );
  }

  public void setThickness(int thickness) {
    setTopAndBottom(thickness);
    setLeftAndRight(thickness);
  }

  public int getTopAndBottom() {
    return top + bottom;
  }

  public void setTopAndBottom(int thickness) {
    setTop(thickness);
    setBottom(thickness);
  }

  public int getLeftAndRight() {
    return left + right;
  }

  public void setLeftAndRight(int thickness) {
    setLeft(thickness);
    setRight(thickness);
  }

  public boolean isChanged() {
    return changed || dimension.isChanged();
  }

  public void clearChangedFlag() {
    changed = false;
    dimension.clearChangedFlag();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Box box)) {
      return false;
    }
    return top == box.top && left == box.left && right == box.right && bottom == box.bottom
        && Objects.equals(dimension, box.dimension);
  }

  @Override
  public int hashCode() {
    return Objects.hash(dimension, top, left, right, bottom);
  }

  @Override
  public String toString() {
    return "Box{" +
        "dimension=" + dimension +
        ", top=" + top +
        ", left=" + left +
        ", right=" + right +
        ", bottom=" + bottom +
        '}';
  }

  // Getters/Setters

  public int getWidth() {
    return dimension.getWidth();
  }

  public int getHeight() {
    return dimension.getHeight();
  }

  public Dimension getDimension() {
    return dimension;
  }

  public void setDimension(Dimension dimension) {
    setDimension(dimension.getWidth(), dimension.getHeight());
  }

  public void setDimension(int width, int height) {
    dimension.setDimension(width, height);
  }

  public int getTop() {
    return top;
  }

  public void setTop(int top) {
    if (this.top != top) {
      this.top = top;
      if (getTopAndBottom() > getHeight()) {
        dimension.setHeight(getTopAndBottom());
      }
      changed = true;
    }
  }

  public int getLeft() {
    return left;
  }

  public void setLeft(int left) {
    if (this.left != left) {
      this.left = left;
      if (getLeftAndRight() > getWidth()) {
        dimension.setWidth(getLeftAndRight());
      }
      changed = true;
    }
  }

  public int getRight() {
    return right;
  }

  public void setRight(int right) {
    if (this.right != right) {
      this.right = right;
      if (getLeftAndRight() > getWidth()) {
        dimension.setWidth(getLeftAndRight());
      }
      changed = true;
    }
  }

  public int getBottom() {
    return bottom;
  }

  public void setBottom(int bottom) {
    if (this.bottom != bottom) {
      this.bottom = bottom;
      if (getTopAndBottom() > getHeight()) {
        dimension.setHeight(getTopAndBottom());
      }
      changed = true;
    }
  }
}

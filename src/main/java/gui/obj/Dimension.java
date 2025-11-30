package gui.obj;

import java.util.Objects;

public class Dimension {

  private int width;
  private int height;
  private boolean changed;

  public Dimension() {
    this(0, 0);
  }

  public Dimension(int width, int height) {
    this.width = width;
    this.height = height;
  }

  // Methods

  public void setDimension(int width, int height) {
    setWidth(width);
    setHeight(height);
  }

  public int getArea() {
    return width * height;
  }

  public void clearChangedFlag() {
    changed = false;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Dimension dimension)) {
      return false;
    }
    return width == dimension.width && height == dimension.height;
  }

  @Override
  public int hashCode() {
    return Objects.hash(width, height);
  }

  @Override
  public String toString() {
    return "Dimension{" +
        "width=" + width +
        ", height=" + height +
        '}';
  }

  // Getters/Setters

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    if (this.width != width) {
      this.width = width;
      changed = true;
    }
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    if (this.height != height) {
      this.height = height;
      changed = true;
    }
  }

  public boolean isChanged() {
    return changed;
  }
}

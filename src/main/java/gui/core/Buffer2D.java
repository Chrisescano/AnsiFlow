package gui.core;

import java.util.Arrays;
import gui.obj.Dimension;

public class Buffer2D {

  private final Dimension dimension;
  private final char[] buffer;

  private Buffer2D() {
    this(new Dimension());
  }

  private Buffer2D(Dimension dimension) {
    this.dimension = dimension;
    this.buffer = new char[dimension.getArea()];
  }

  public Buffer2D(Dimension dimension, char[] buffer) {
    this.dimension = dimension;
    this.buffer = buffer;
  }

  // Methods

  public int getWidth() {
    return dimension.getWidth();
  }

  public int getHeight() {
    return dimension.getHeight();
  }

  public int getSize() {
    return dimension.getArea();
  }

  public static Buffer2D create(Dimension dimension) {
    if (dimension == null || dimension.getArea() < 0) {
      return null;
    }
    return new Buffer2D(dimension);
  }

  public static Buffer2D create(int width, int height) {
    return create(new Dimension(width, height));
  }

  public char read(int x, int y) throws IndexOutOfBoundsException {
    int index = toIndex(x, y);
    if (index != Integer.MIN_VALUE) {
      return buffer[index];
    }
    throw new IndexOutOfBoundsException("Cannot read outside of buffer bounds");
  }

  public char[] read(int x, int y, int length) throws IndexOutOfBoundsException {
    int index = toIndex(x, y);
    int end = index + length;
    if (index != Integer.MIN_VALUE && end <= buffer.length) {
      return Arrays.copyOfRange(buffer, index, end);
    }
    throw new IndexOutOfBoundsException("Cannot read outside of buffer bounds");
  }

  public boolean write(int x, int y, char data) {
    int index = toIndex(x, y);
    if (index != Integer.MIN_VALUE) {
      buffer[index] = data;
      return true;
    }
    return false;
  }

  public boolean write(int x, int y, char[] data) {
    if (data == null) {
      return false;
    }
    int index = toIndex(x, y);
    int area = dimension.getArea();
    if (index != Integer.MIN_VALUE) {
      int length = Math.min(area, index + data.length);
      System.arraycopy(data, 0, buffer, 0, length);
      return true;
    }
    return false;
  }

  public Buffer2D extract(int xOffset, int yOffset, int xCutoff, int yCutoff) {
    int offsetIndex = toIndex(xOffset, yOffset);
    int cutoffIndex = toIndex(xCutoff, yCutoff);
    if (offsetIndex != Integer.MIN_VALUE && cutoffIndex != Integer.MIN_VALUE && offsetIndex <= cutoffIndex) {
      int width = xCutoff - xOffset;
      int height = yCutoff - yOffset;
      Buffer2D buffer = new Buffer2D(new Dimension(width, height));
      for (int row = 0; row < height; row++) {
        try {
          char[] rowData = read(xOffset, row + yOffset, width);
          buffer.write(0, row, rowData);
        } catch (IndexOutOfBoundsException ignored) {
        }
      }
      return buffer;
    }
    return null;
  }

  public void layer(Buffer2D buffer, int xOffset, int yOffset) {
    layer(buffer, xOffset, yOffset, buffer.getWidth(), buffer.getHeight());
  }

  public void layer(Buffer2D buffer, int xOffset, int yOffset, int xCutoff, int yCutoff) {
    if (buffer == null) {
      return;
    }
    int width = Math.min(xCutoff, this.dimension.getWidth());
    int height = Math.min(yCutoff, this.dimension.getHeight());
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        try {
          char data = buffer.read(x, y);
          write(x + xOffset, y + yOffset, data);
        } catch (IndexOutOfBoundsException ignored) {
        }
      }
    }
  }

  private int toIndex(int x, int y) {
    if (GuiUtil.isInsideRange(x, 0, dimension.getWidth())
        && GuiUtil.isInsideRange(y, 0, dimension.getHeight())) {
      return y * dimension.getWidth() + x;
    }
    return Integer.MIN_VALUE;
  }
}

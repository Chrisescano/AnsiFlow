package com.christian.apps.core;

import java.util.Arrays;
import java.util.Objects;

public class Buffer2D {

  private final int width;
  private final int height;
  private final char[][] buffer;

  private Buffer2D(int width, int height) {
    this(width, height, new char[height][width]);
  }

  private Buffer2D(int width, int height, char[][] buffer) {
    this.width = width;
    this.height = height;
    this.buffer = buffer;
  }

  /*-- Methods --*/

  public static Buffer2D create(int width, int height) {
    if (width < 0 || height < 0) {
      return new Buffer2D(0, 0);
    }
    return null;
  }

  public static Buffer2D wrap(char[][] data) {
    if (data == null || data.length == 0) {
      return new Buffer2D(0, 0);
    }
    return new Buffer2D(data[0].length, data.length, data);
  }

  public char readChar(int x, int y) {
    if (isInsideBuffer(x, y)) {
      return get(x, y);
    }
    return Character.MIN_VALUE;
  }

  public boolean writeChar(char data, int x, int y) {
    if (isInsideBuffer(x, y)) {
      set(data, x, y);
      return true;
    }
    return false;
  }

  public char[] readLine(int y) {
    if (isInsideBufferHeight(y)) {
      return get(y);
    }
    return null;
  }

  public boolean writeLine(char[] data, int y) {
    if (data != null && isInsideBufferHeight(y)) {
      char[] bufferRow = get(y);
      System.arraycopy(data, 0, bufferRow, 0, Math.min(data.length, bufferRow.length));
      return true;
    }
    return false;
  }

  public void layer(Buffer2D buffer) {
    layer(buffer, 0, 0, buffer.width, buffer.height);
  }

  public void layer(Buffer2D buffer, int xOffset, int yOffset) {
    layer(buffer, xOffset, yOffset, buffer.width, buffer.height);
  }

  public void layer(Buffer2D buffer, int xOffset, int yOffset, int width, int height) {
    for (int row = 0; row < Math.min(this.height, height); row++) {
      for (int col = 0; col < Math.min(this.width, width); col++) {
        writeChar(buffer.readChar(col, row), col + xOffset, row + yOffset);
      }
    }
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Buffer2D buffer2D)) {
      return false;
    }
    return width == buffer2D.width && height == buffer2D.height && Arrays.deepEquals(
        buffer, buffer2D.buffer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(width, height, Arrays.deepHashCode(buffer));
  }

  @Override
  public String toString() {
    return "Buffer2D{" +
        "width=" + width +
        ", height=" + height +
        ", buffer=" + toPrettyString() +
        '}';
  }

  /*-- Helper Methods --*/

  private char get(int x, int y) {
    return buffer[y][x];
  }

  private char[] get(int y) {
    return buffer[y];
  }

  private void set(char data, int x, int y) {
    buffer[y][x] = data;
  }

  private boolean isInsideBufferWidth(int x) {
    return x >= 0 && x < width;
  }

  private boolean isInsideBufferHeight(int y) {
    return y >= 0 && y < height;
  }

  private boolean isInsideBuffer(int x, int y) {
    return isInsideBufferWidth(x) && isInsideBufferHeight(y);
  }

  private String toPrettyString() {
    StringBuilder builder = new StringBuilder();
    for (char[] row : buffer) {
      builder.append('[').append(row).append(']');
    }
    return builder.toString();
  }

  /*-- Getters/Setters --*/

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}

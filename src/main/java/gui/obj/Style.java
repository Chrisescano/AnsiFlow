package gui.obj;

import gui.enums.BoxRegion;

public class Style {

  private char topLeft;
  private char top;
  private char topRight;
  private char left;
  private char center;
  private char right;
  private char bottomLeft;
  private char bottom;
  private char bottomRight;

  public Style() {
  }

  public Style(char topLeft, char top, char topRight, char left, char center, char right,
      char bottomLeft, char bottom, char bottomRight) {
    this.topLeft = topLeft;
    this.top = top;
    this.topRight = topRight;
    this.left = left;
    this.center = center;
    this.right = right;
    this.bottomLeft = bottomLeft;
    this.bottom = bottom;
    this.bottomRight = bottomRight;
  }

  // Methods

  public void apply(Style style) {
    setTopLeft(style.topLeft);
    setTop(style.top);
    setTopRight(style.topRight);
    setLeft(style.left);
    setCenter(style.center);
    setRight(style.right);
    setBottomLeft(style.bottomLeft);
    setBottom(style.bottom);
    setBottomRight(style.bottomRight);
  }

  public Character getChar(BoxRegion boxRegion) {
    if (boxRegion == null) {
      return null;
    }
    return switch (boxRegion) {
      case TOP_LEFT -> topLeft;
      case TOP -> top;
      case TOP_RIGHT -> topRight;
      case LEFT -> left;
      case CENTER -> center;
      case RIGHT -> right;
      case BOTTOM_LEFT -> bottomLeft;
      case BOTTOM -> bottom;
      case BOTTOM_RIGHT -> bottomRight;
    };
  }

  public void setCorners(char style) {
    setTopLeft(style);
    setTopRight(style);
    setBottomLeft(style);
    setBottomRight(style);
  }

  public void setEdges(char style) {
    setTopAndBottom(style);
    setLeftAndRight(style);
  }

  public void setTopAndBottom(char style) {
    setTop(style);
    setBottom(style);
  }

  public void setLeftAndRight(char style) {
    setLeft(style);
    setRight(style);
  }

  // Getters/Setters

  public char getTopLeft() {
    return topLeft;
  }

  public void setTopLeft(char topLeft) {
    this.topLeft = topLeft;
  }

  public char getTop() {
    return top;
  }

  public void setTop(char top) {
    this.top = top;
  }

  public char getTopRight() {
    return topRight;
  }

  public void setTopRight(char topRight) {
    this.topRight = topRight;
  }

  public char getLeft() {
    return left;
  }

  public void setLeft(char left) {
    this.left = left;
  }

  public char getCenter() {
    return center;
  }

  public void setCenter(char center) {
    this.center = center;
  }

  public char getRight() {
    return right;
  }

  public void setRight(char right) {
    this.right = right;
  }

  public char getBottomLeft() {
    return bottomLeft;
  }

  public void setBottomLeft(char bottomLeft) {
    this.bottomLeft = bottomLeft;
  }

  public char getBottom() {
    return bottom;
  }

  public void setBottom(char bottom) {
    this.bottom = bottom;
  }

  public char getBottomRight() {
    return bottomRight;
  }

  public void setBottomRight(char bottomRight) {
    this.bottomRight = bottomRight;
  }
}

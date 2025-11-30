package gui.core;

import gui.obj.Box;
import gui.obj.Style;

public class BoxModel {

  private final Box margin = new Box();
  private final Box border = new Box();
  private final Box padding = new Box();
  private final Box content = new Box();
  private final Style marginStyle = new Style();
  private final Style borderStyle = new Style();
  private final Style paddingStyle = new Style();
  private BoxSizing boxSizing = BoxSizing.CONTENT;
  private boolean updateSizing = false;

  // Methods

  /**
   * Updates all boxes inside the BoxModel if they need updating
   */
  public void update() {
    switch (boxSizing) {
      case CONTENT -> updateContent();
      case BORDER -> updateBorder();
    }
  }

  /**
   * Updates all boxes with respect to the Content. Starts at the Content layer
   * and propagates changes to the other layers.
   */
  private void updateContent() {
    if (updateSizing) {
      content.setDimension(border.getWidth(), border.getHeight());
      updateSizing = false;
    }

    if (content.isChanged()) {
      padding.expandFor(content);
      content.clearChangedFlag();
    }
    if (padding.isChanged()) {
      border.expandFor(padding);
      padding.clearChangedFlag();
    }
    if (border.isChanged()) {
      margin.expandFor(border);
      border.clearChangedFlag();
    }
  }

  /**
   * Updates all boxes with respect to the Border. Starts at the Border layer
   * and propagates changes to the other layers.
   */
  private void updateBorder() {
    if (updateSizing) {
      border.setDimension(content.getWidth(), content.getHeight());
      updateSizing = false;
    }

    if (border.isChanged()) {
      margin.expandFor(border);
      padding.shrinkFor(border);
      border.clearChangedFlag();
    }
    if (padding.isChanged()) {
      content.shrinkFor(padding);
      padding.clearChangedFlag();
      content.clearChangedFlag();
    }
  }

  // Getters/Setters

  public Box getMargin() {
    return margin;
  }

  public void setMargin(Box margin) {
    this.margin.apply(margin);
  }

  public Box getBorder() {
    return border;
  }

  public void setBorder(Box border) {
    this.border.apply(border);
  }

  public Box getPadding() {
    return padding;
  }

  public void setPadding(Box padding) {
    this.padding.apply(padding);
  }

  public Box getContent() {
    return content;
  }

  public void setContent(Box content) {
    this.content.apply(content);
  }

  public Style getMarginStyle() {
    return marginStyle;
  }

  public void setMarginStyle(Style marginStyle) {
    this.marginStyle.apply(marginStyle);
  }

  public Style getBorderStyle() {
    return borderStyle;
  }

  public void setBorderStyle(Style borderStyle) {
    this.borderStyle.apply(borderStyle);
  }

  public Style getPaddingStyle() {
    return paddingStyle;
  }

  public void setPaddingStyle(Style paddingStyle) {
    this.paddingStyle.apply(paddingStyle);
  }

  public void setDimension(int width, int height) {
    switch (boxSizing) {
      case CONTENT -> content.setDimension(width, height);
      case BORDER -> border.setDimension(width, height);
    }
  }

  public BoxSizing getBoxSizing() {
    return boxSizing;
  }

  public void setBoxSizing(BoxSizing boxSizing) {
    if (this.boxSizing != boxSizing) {
      this.boxSizing = boxSizing;
      updateSizing = true;
    }
  }
}

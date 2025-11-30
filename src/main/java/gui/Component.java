package gui;

import gui.core.BoxRegion;
import gui.core.BoxSizing;
import gui.core.Buffer2D;
import obj.Box;
import obj.Dimension;
import obj.Style;

public abstract class Component {

  /*
  Potential improvement:
  - if we know the 'size' of the content, there should be a method that will update sizing
  - might be useful to have a pre-render step to update sizing, dimensions, and do all calculations
   */

  private Box margin = new Box();
  private Box border = new Box();
  private Box padding = new Box();
  private Box content = new Box();
  private Style marginStyle = new Style();
  private Style borderStyle = new Style();
  private Style paddingStyle = new Style();
  private BoxSizing boxSizing = BoxSizing.CONTENT;
  private boolean updateSizing = false;

  // Methods

  protected abstract Buffer2D renderContent();

  public void setDimension(Dimension dimension) {
    switch (boxSizing) {
      case CONTENT -> content.setDimension(dimension);
      case BORDER -> border.setDimension(dimension);
    }
  }

  public void update() {
    switch (boxSizing) {
      case CONTENT -> updateContent();
      case BORDER -> updateBorder();
    }
  }

  public Buffer2D render() {
    Buffer2D marginBuffer = renderBox(margin, marginStyle);
    Buffer2D borderBuffer = renderBox(border, borderStyle);
    Buffer2D paddingBuffer = renderBox(padding, paddingStyle);
    Buffer2D contentBuffer = renderContent();

    if (paddingBuffer.getSize() != 0) {
      paddingBuffer.layer(contentBuffer, padding.getTop(), padding.getLeft());
    }
    if (borderBuffer.getSize() != 0) {
      borderBuffer.layer(paddingBuffer, border.getTop(), border.getLeft());
    }
    if (marginBuffer.getSize() != 0) {
      marginBuffer.layer(borderBuffer, margin.getTop(), margin.getLeft());
    }

    return marginBuffer;
  }

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

  private Buffer2D renderBox(Box box, Style style) {
    Buffer2D buffer = Buffer2D.create(box.getDimension());
    if (buffer != null) {
      for (int y = 0; y < buffer.getHeight(); y++) {
        for (int x = 0; x < buffer.getWidth(); x++) {
          BoxRegion region = box.getRegion(x, y);
          Character data = style.getChar(region);
          if (data != null && data != Character.MIN_VALUE) {
            buffer.write(x, y, data);
          }
        }
      }
    }
    return buffer;
  }

  // Getters/Setters

  public void setMargin(Box margin) {
    this.margin.apply(margin);
  }

  public void setBorder(Box border) {
    this.border.apply(border);
  }

  public void setPadding(Box padding) {
    this.padding.apply(padding);
  }

  public void setContent(Box content) {
    this.content.apply(content);
  }

  public void setMarginStyle(Style marginStyle) {
    this.marginStyle = marginStyle;
  }

  public void setBorderStyle(Style borderStyle) {
    this.borderStyle = borderStyle;
  }

  public void setPaddingStyle(Style paddingStyle) {
    this.paddingStyle = paddingStyle;
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

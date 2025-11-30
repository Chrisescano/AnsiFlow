package gui;

import gui.core.BoxModel;
import gui.core.BoxRegion;
import gui.core.Buffer2D;
import gui.obj.Box;
import gui.obj.Style;

public abstract class Component {

  /*
  Potential improvement:
  - if we know the 'size' of the content, there should be a method that will update sizing
  - might be useful to have a pre-render step to update sizing, dimensions, and do all calculations
   */

  private final BoxModel boxModel = new BoxModel();

  // Methods

  protected abstract Buffer2D renderContent();

  public Buffer2D render() {
    Box margin = boxModel.getMargin();
    Box border = boxModel.getBorder();
    Box padding = boxModel.getPadding();

    Buffer2D marginBuffer = renderBox(margin, boxModel.getMarginStyle());
    Buffer2D borderBuffer = renderBox(border, boxModel.getBorderStyle());
    Buffer2D paddingBuffer = renderBox(padding, boxModel.getPaddingStyle());
    Buffer2D contentBuffer = renderContent();

    if (paddingBuffer.getSize() > 0) {
      paddingBuffer.layer(contentBuffer, padding.getTop(), padding.getLeft());
    }
    if (borderBuffer.getSize() > 0) {
      borderBuffer.layer(paddingBuffer, border.getTop(), border.getLeft());
    }
    if (marginBuffer.getSize() > 0) {
      marginBuffer.layer(borderBuffer, margin.getTop(), margin.getLeft());
    }

    return marginBuffer;
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

  public BoxModel getBoxModel() {
    return boxModel;
  }
}

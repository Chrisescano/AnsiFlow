package gui.component;

import gui.core.Buffer2D;
import gui.obj.Box;
import gui.obj.Dimension;

public class Label extends Component {

  private String text;

  public Label() {

  }

  public Label(String text) {
    this.text = text;
  }

  // Methods

  @Override
  protected Buffer2D renderContent() {
    int length = text == null ? 0 : text.length();
    Buffer2D buffer = Buffer2D.create(length, 1);
    buffer.write(0, 0, text.toCharArray());
    return buffer;
  }

  @Override
  public void preRender() {
    int length = text == null ? 0 : text.length();
    Dimension dimension = new Dimension(length, 1);
    Box content = new Box(dimension);
    boxModel.setContent(content);
    super.preRender();
  }

  // Getters/Setters

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}

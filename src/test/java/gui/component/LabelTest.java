package gui.component;

import gui.core.Buffer2D;
import gui.obj.Box;
import gui.obj.Style;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LabelTest {

  private Label label;

  @Test
  public void testRenderingLabelTextOnly() {
    // Given
    label = new Label("Hello");

    // When
    label.preRender();
    Buffer2D buffer = label.render();

    // Then
    Assert.assertNotNull(buffer);
    Assert.assertEquals(buffer.getSize(), 5);
    Assert.assertEquals(buffer.read(0, 0, 5), new char[]{'H', 'e', 'l', 'l', 'o'});
  }

  @Test
  public void testRenderingLabelTextAndBorder() {
    // Given
    label = new Label("Hello");

    Box border = new Box(0, 1, 1, 0);
    label.getBoxModel().setBorder(border);

    Style borderStyle = new Style();
    borderStyle.setLeft('[');
    borderStyle.setRight(']');
    label.getBoxModel().setBorderStyle(borderStyle);

    // When
    label.preRender();
    Buffer2D buffer = label.render();

    // Then
    Assert.assertNotNull(buffer);
    Assert.assertEquals(buffer.getSize(), 7);
    Assert.assertEquals(buffer.read(0, 0, 7), new char[]{'[', 'H', 'e', 'l', 'l', 'o', ']'});
  }

  @Test
  public void testRenderingLabelPaddingBorder() {
    // Given
    label = new Label("Hello");
    label.getBoxModel().setPadding(new Box(0, 1, 1, 0));
    label.getBoxModel().setBorder(new Box(0, 1, 1, 0));

    Style paddingStyle = new Style();
    paddingStyle.setLeftAndRight(' ');
    label.getBoxModel().setPaddingStyle(paddingStyle);

    Style borderStyle = new Style();
    borderStyle.setLeft('[');
    borderStyle.setRight(']');
    label.getBoxModel().setBorderStyle(borderStyle);

    // When
    label.preRender();
    Buffer2D buffer = label.render();

    // Then
    Assert.assertNotNull(buffer);
    Assert.assertEquals(buffer.getSize(), 9);
    Assert.assertEquals(buffer.read(0, 0, 9),
        new char[]{'[', ' ', 'H', 'e', 'l', 'l', 'o', ' ', ']'});
  }
}
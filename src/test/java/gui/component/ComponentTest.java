package gui.component;

import gui.core.BoxModel;
import gui.core.Buffer2D;
import gui.obj.Box;
import gui.obj.Dimension;
import gui.obj.Style;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ComponentTest {

  private static class TestComponent extends Component {

    private final Buffer2D buffer;

    public TestComponent() {
      buffer = Buffer2D.create(new Dimension(0, 0));
    }

    public TestComponent(Buffer2D buffer) {
      this.buffer = buffer;
    }

    @Override
    protected Buffer2D renderContent() {
      return buffer;
    }
  }

  @Test
  public void testRenderingDefault() {
    // Given
    TestComponent component = new TestComponent();
    BoxModel boxModel = component.getBoxModel();

    // When
    boxModel.update();
    Buffer2D componentBuffer = component.render();

    // Then
    Assert.assertNotNull(componentBuffer);
    Assert.assertEquals(componentBuffer.getWidth(), 0);
    Assert.assertEquals(componentBuffer.getHeight(), 0);
  }

  @Test
  public void testRenderingContentOnly() {
    // Given
    Buffer2D contentBuff = Buffer2D.create(new Dimension(2, 2));
    contentBuff.write(0, 0, new char[]{'x', 'x', 'x', 'x'});
    TestComponent component = new TestComponent(contentBuff);
    BoxModel boxModel = component.getBoxModel();
    boxModel.setDimension(contentBuff.getWidth(), contentBuff.getHeight());

    // When
    boxModel.update();
    Buffer2D componentBuffer = component.render();

    // Then
    Assert.assertNotNull(componentBuffer);
    Assert.assertEquals(componentBuffer.getHeight(), 2);
    Assert.assertEquals(componentBuffer.getWidth(), 2);
    Assert.assertEquals(componentBuffer.read(0, 0, 4), new char[]{'x', 'x', 'x', 'x'});
  }

  @Test
  public void testRenderingBorderOnly() {
    // Given
    TestComponent component = new TestComponent();
    BoxModel boxModel = component.getBoxModel();

    Box border = new Box();
    border.setThickness(1);
    boxModel.setBorder(border);

    Style borderStyle = new Style();
    borderStyle.setCorners('+');
    boxModel.setBorderStyle(borderStyle);

    // When
    boxModel.update();
    Buffer2D componentBuffer = component.render();

    // Then
    Assert.assertNotNull(componentBuffer);
    Assert.assertEquals(componentBuffer.getHeight(), 2);
    Assert.assertEquals(componentBuffer.getWidth(), 2);
    Assert.assertEquals(componentBuffer.read(0, 0, 4), new char[]{'+', '+', '+', '+'});
  }

  @Test
  public void testRenderingContentAndBorderOnly() {
    // Given
    Buffer2D contentBuff = Buffer2D.create(new Dimension(2, 2));
    contentBuff.write(0, 0, new char[]{'x', 'x', 'x', 'x'});
    TestComponent component = new TestComponent(contentBuff);
    BoxModel boxModel = component.getBoxModel();

    boxModel.setDimension(contentBuff.getWidth(), contentBuff.getHeight());

    Box border = new Box();
    border.setThickness(1);
    boxModel.setBorder(border);

    Style borderStyle = new Style();
    borderStyle.setCorners('+');
    borderStyle.setEdges('-');
    boxModel.setBorderStyle(borderStyle);

    // When
    boxModel.update();
    Buffer2D componentBuffer = component.render();

    // Then
    Assert.assertNotNull(componentBuffer);
    Assert.assertEquals(componentBuffer.getHeight(), 4);
    Assert.assertEquals(componentBuffer.getWidth(), 4);
    Assert.assertEquals(componentBuffer.read(0, 0, 8),
        new char[]{'+', '-', '-', '+', '-', 'x', 'x', '-'});
  }
}
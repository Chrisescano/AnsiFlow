package gui.core;

import gui.component.Component;
import gui.enums.Layout;
import java.util.ArrayList;
import java.util.List;

public class Container {

  private final List<Component> components = new ArrayList<>();
  private Layout layout = Layout.HORIZONTAL;

  // Methods

  public void add(Component component) {
    components.add(component);
  }

  public void preRender() {
    for (Component component : components) {
      component.preRender();
    }
  }

  public Buffer2D render() {
    return switch (layout) {
      case HORIZONTAL -> renderHorizontal();
      case VERTICAL -> renderVertical();
      case GRID -> renderGrid();
    };
  }

  private Buffer2D renderHorizontal() {
    List<Buffer2D> componentBuffers = renderComponentBuffers();

    int width = 0, height = 0;
    for (Buffer2D buffer : componentBuffers) {
      width += buffer.getWidth();
      height = Math.max(buffer.getHeight(), height);
    }

    Buffer2D containerBuffer = Buffer2D.create(width, height);
    int offset = 0;
    for (Buffer2D buffer : componentBuffers) {
      containerBuffer.layer(buffer, offset, 0);
      offset += buffer.getWidth();
    }

    return containerBuffer;
  }

  private Buffer2D renderVertical() {
    List<Buffer2D> componentBuffers = renderComponentBuffers();

    int width = 0, height = 0;
    for (Buffer2D buffer : componentBuffers) {
      height += buffer.getHeight();
      width = Math.max(buffer.getWidth(), width);
    }

    Buffer2D containerBuffer = Buffer2D.create(width, height);
    int offset = 0;
    for (Buffer2D buffer : componentBuffers) {
      containerBuffer.layer(buffer, 0, offset);
      offset += buffer.getHeight();
    }

    return containerBuffer;
  }

  private Buffer2D renderGrid() {
    return null;
  }

  private List<Buffer2D> renderComponentBuffers() {
    List<Buffer2D> componentBuffers = new ArrayList<>();
    for (Component component : components) {
      componentBuffers.add(component.render());
    }
    return componentBuffers;
  }

  // Getters/Setters

  public Layout getLayout() {
    return layout;
  }

  public void setLayout(Layout layout) {
    this.layout = layout;
  }
}

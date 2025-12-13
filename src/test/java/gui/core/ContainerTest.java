package gui.core;

import gui.component.Component;
import gui.component.Label;
import gui.enums.Layout;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContainerTest {

  @Test
  public void testContainerLayHorizontal() {
    Component compA = new Label("Hello");
    Component compB = new Label("World");

    Container container = new Container();
    container.add(compA);
    container.add(compB);
    container.preRender();
    Buffer2D containerBuff = container.render();

    Assert.assertNotNull(containerBuff);
    Assert.assertEquals(containerBuff.getSize(), 10);
    Assert.assertEquals(containerBuff.getWidth(), 10);
    Assert.assertEquals(containerBuff.getHeight(), 1);
    Assert.assertEquals(containerBuff.read(0, 0, 10),
        new char[]{'H', 'e', 'l', 'l', 'o', 'W', 'o', 'r', 'l', 'd'});
  }

  @Test
  public void testContainerLayVertical() {
    Component compA = new Label("Hello");
    Component compB = new Label("World");

    Container container = new Container();
    container.setLayout(Layout.VERTICAL);
    container.add(compA);
    container.add(compB);
    container.preRender();
    Buffer2D containerBuff = container.render();

    Assert.assertNotNull(containerBuff);
    Assert.assertEquals(containerBuff.getSize(), 10);
    Assert.assertEquals(containerBuff.getWidth(), 5);
    Assert.assertEquals(containerBuff.getHeight(), 2);
    Assert.assertEquals(containerBuff.read(0, 0, 10),
        new char[]{'H', 'e', 'l', 'l', 'o', 'W', 'o', 'r', 'l', 'd'});
  }

}
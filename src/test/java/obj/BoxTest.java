package obj;

import gui.core.BoxRegion;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BoxTest {

  private Box box;

  @BeforeClass
  public void setUp() {
    box = new Box(new Dimension(3, 3), 1, 1, 1, 1);
  }

  @Test
  public void testGetRegionTopLeft() {
    BoxRegion region = box.getRegion(0, 0);
    Assert.assertEquals(region, BoxRegion.TOP_LEFT);
  }

  @Test
  public void testGetRegionTop() {
    BoxRegion region = box.getRegion(1, 0);
    Assert.assertEquals(region, BoxRegion.TOP);
  }

  @Test
  public void testGetRegionTopRight() {
    BoxRegion region = box.getRegion(2, 0);
    Assert.assertEquals(region, BoxRegion.TOP_RIGHT);
  }

  @Test
  public void testGetRegionLeft() {
    BoxRegion region = box.getRegion(0, 1);
    Assert.assertEquals(region, BoxRegion.LEFT);
  }

  @Test
  public void testGetRegionCenter() {
    BoxRegion region = box.getRegion(1, 1);
    Assert.assertEquals(region, BoxRegion.CENTER);
  }

  @Test
  public void testGetRegionRight() {
    BoxRegion region = box.getRegion(2, 1);
    Assert.assertEquals(region, BoxRegion.RIGHT);
  }

  @Test
  public void testGetRegionBottomLeft() {
    BoxRegion region = box.getRegion(0, 2);
    Assert.assertEquals(region, BoxRegion.BOTTOM_LEFT);
  }

  @Test
  public void testGetRegionBottom() {
    BoxRegion region = box.getRegion(1, 2);
    Assert.assertEquals(region, BoxRegion.BOTTOM);
  }

  @Test
  public void testGetRegionBottomRight() {
    BoxRegion region = box.getRegion(2, 2);
    Assert.assertEquals(region, BoxRegion.BOTTOM_RIGHT);
  }
}
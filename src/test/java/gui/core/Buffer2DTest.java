package gui.core;

import gui.obj.Dimension;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Buffer2DTest {

  private Buffer2D buffer;

  @BeforeMethod
  public void setUp() {
    buffer = Buffer2D.create(new Dimension(4, 4));
    buffer.write(0, 0, new char[]{'x', 'x', 'x', 'x'});
  }

  // Reading Char

  @Test
  public void testReadingChar() {
    char data = buffer.read(0, 0);
    Assert.assertEquals(data, 'x');
  }

  @Test(expectedExceptions = {IndexOutOfBoundsException.class},
      expectedExceptionsMessageRegExp = "Cannot read outside of buffer bounds")
  public void testReadingCharOutsideBuffer() {
    buffer.read(-1, 0);
  }

  @Test(expectedExceptions = {IndexOutOfBoundsException.class},
      expectedExceptionsMessageRegExp = "Cannot read outside of buffer bounds")
  public void testReadingCharOutsideBuffer2() {
    buffer.read(4, 0);
  }

  // Reading Char[]

  @Test
  public void testReadingCharArray() {
    char[] data = buffer.read(0, 0, 3);
    Assert.assertNotNull(data);
    Assert.assertEquals(data.length, 3);
    Assert.assertEquals(data, new char[]{'x', 'x', 'x'});
  }

  @Test(expectedExceptions = {IndexOutOfBoundsException.class},
      expectedExceptionsMessageRegExp = "Cannot read outside of buffer bounds")
  public void testReadingCharArray2() {
    buffer.read(-1, 0, 3);
  }

  // Writing Char

  @Test
  public void testWritingChar() {
    boolean status = buffer.write(0, 0, 'C');
    Assert.assertTrue(status);
    Assert.assertEquals(buffer.read(0, 0), 'C');
  }

  @Test
  public void testWritingCharOutsideBuffer() {
    boolean status = buffer.write(-1, 0, 'C');
    Assert.assertFalse(status);
  }

  @Test
  public void testWritingCharOutsideBuffer2() {
    boolean status = buffer.write(4, 0, 'C');
    Assert.assertFalse(status);
  }

  // Writing Char[]

  @Test
  public void testWritingCharArray() {
    char[] data = new char[]{'d', 'd', 'd'};
    boolean status = buffer.write(0, 0, data);
    Assert.assertTrue(status);
    Assert.assertEquals(buffer.read(0, 0, 3), data);
  }

  @Test
  public void testWritingCharArray2() {
    char[] data = new char[]{'d', 'd', 'd'};
    boolean status = buffer.write(-1, 0, data);
    Assert.assertFalse(status);
  }

  // Layering

  @Test
  public void testLayering() {
    Buffer2D sampleBuff = Buffer2D.create(new Dimension(1, 2));
    sampleBuff.write(0, 0, new char[]{'a', 'a'});
    buffer.layer(sampleBuff, 0, 0, sampleBuff.getWidth(), sampleBuff.getHeight());
    char[] data = buffer.read(0, 0, 4);
    Assert.assertNotNull(data);
    Assert.assertEquals(data.length, 4);
    Assert.assertEquals(data, new char[]{'a', 'x', 'x', 'x'});
  }

  @Test
  public void testLayering2() {
    Buffer2D sampleBuff = Buffer2D.create(new Dimension(1, 2));
    sampleBuff.write(0, 0, new char[]{'a', 'a'});
    buffer.layer(sampleBuff, 0, -1, sampleBuff.getWidth(), sampleBuff.getHeight());
    char[] data = buffer.read(0, 0, 4);
    Assert.assertNotNull(data);
    Assert.assertEquals(data.length, 4);
    Assert.assertEquals(data, new char[]{'a', 'x', 'x', 'x'});
  }

  // Extracting

  @Test
  public void testExtracting() {
    Buffer2D extract = buffer.extract(0, 0, 1, 1);
    Assert.assertNotNull(extract);
    Assert.assertEquals(extract.getWidth(), 1);
    Assert.assertEquals(extract.getHeight(), 1);
    Assert.assertEquals(extract.read(0, 0), 'x');
  }

  @Test
  public void testExtracting2() {
    Buffer2D extract = buffer.extract(-1, 0, buffer.getWidth(), 1);
    Assert.assertNull(extract);
  }
}
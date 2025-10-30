package com.christian.apps.core;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Buffer2DTest {

  private Buffer2D testBuffer;

  @BeforeMethod
  public void setUp() {
    testBuffer = Buffer2D.wrap(new char[][]{
        {'Q', 'T', 'W'},
        {'L', 'C', 'R'},
        {'A', 'B', 'S'},
    });
  }

  @Test
  public void testReadChar() {
    Assert.assertEquals(testBuffer.readChar(0, 0), 'Q');
  }

  @Test
  public void testWriteChar() {
    testBuffer.writeChar('X', 0, 0);
    Assert.assertEquals(testBuffer.readChar(0, 0), 'X');
  }

  @Test
  public void testReadLine() {
    Assert.assertEquals(testBuffer.readLine(0), new char[]{'Q', 'T', 'W'});
  }

  @Test
  public void testWriteShortLine() {
    char[] line = new char[]{'X', 'X'};
    testBuffer.writeLine(line, 0);
    Buffer2D expected = Buffer2D.wrap(new char[][]{
        {'X', 'X', 'W'},
        {'L', 'C', 'R'},
        {'A', 'B', 'S'},
    });
    Assert.assertEquals(testBuffer, expected);
  }

  @Test
  public void testWriteLongLine() {
    char[] line = new char[]{'X', 'X', 'X', 'X'};
    testBuffer.writeLine(line, 0);
    Buffer2D expected = Buffer2D.wrap(new char[][]{
        {'X', 'X', 'X'},
        {'L', 'C', 'R'},
        {'A', 'B', 'S'},
    });
    Assert.assertEquals(testBuffer, expected);
  }

  @Test
  public void testFullLayer() {
    Buffer2D layer = Buffer2D.wrap(new char[][]{
        {'X', 'X', 'X'},
        {'X', 'X', 'X'},
        {'X', 'X', 'X'},
    });
    testBuffer.layer(layer);
    Assert.assertEquals(testBuffer, layer);
  }

  @Test
  public void testLayerWithOffsets() {
    Buffer2D layer = Buffer2D.wrap(new char[][]{
        {'X', 'X', 'X'},
        {'X', 'X', 'X'},
        {'X', 'X', 'X'},
    });
    Buffer2D expected = Buffer2D.wrap(new char[][]{
        {'Q', 'T', 'W'},
        {'L', 'X', 'X'},
        {'A', 'X', 'X'},
    });
    testBuffer.layer(layer, 1, 1);
    Assert.assertEquals(testBuffer, expected);
  }

  @Test
  public void testLayerWithOffsetsAndPartialBuffer() {
    Buffer2D layer = Buffer2D.wrap(new char[][]{
        {'X', 'X', 'X'},
        {'X', 'X', 'X'},
        {'X', 'X', 'X'},
    });
    Buffer2D expected = Buffer2D.wrap(new char[][]{
        {'Q', 'T', 'W'},
        {'L', 'X', 'R'},
        {'A', 'B', 'S'},
    });
    testBuffer.layer(layer, 1, 1, 1, 1);
    Assert.assertEquals(testBuffer, expected);
  }
}
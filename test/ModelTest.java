import org.junit.Before;
import org.junit.Test;

import model.IPixel;
import model.ImageDataBase;
import model.ImageImpl;
import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * a test for the model of the project.
 */
public class ModelTest {

  private ImageImpl image;



  @Before
  public void setUp() {
    ImageDataBase model = new ImageDataBase();
    image = new ImageImpl(2,3);

    IPixel pixel1 = new Pixel(1,2,3);
    IPixel pixel2 = new Pixel(11,22,33);
    IPixel pixel3 = new Pixel(100,100,100);
    IPixel pixel4 = new Pixel(5,15,25);
    IPixel pixel5 = new Pixel(10,100,200);
    IPixel pixel6 = new Pixel(20,30,40);

    image.setPixel(0,0, pixel1.getR(), pixel1.getG(), pixel1.getB());
    image.setPixel(0,1, pixel2.getR(), pixel2.getG(), pixel2.getB());
    image.setPixel(0,2, pixel3.getR(), pixel3.getG(), pixel3.getB());
    image.setPixel(1,0, pixel4.getR(), pixel4.getG(), pixel4.getB());
    image.setPixel(1,1, pixel5.getR(), pixel5.getG(), pixel5.getB());
    image.setPixel(1,2, pixel6.getR(), pixel6.getG(), pixel6.getB());


  }

  @Test
  public void getBlue() {
    assertEquals(image.getBlueChannel(0,1), 33);
    assertEquals(image.getBlueChannel(1,1), 200);
  }

  @Test
  public void getRed() {
    assertEquals(image.getRedChannel(0,1), 11);
    assertEquals(image.getRedChannel(1,2), 20);
  }

  @Test
  public void getGreen() {
    assertEquals(image.getGreenChannel(1,0), 15);
    assertEquals(image.getGreenChannel(1,2), 30);
  }


}

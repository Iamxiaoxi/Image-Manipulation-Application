import org.junit.Before;
import org.junit.Test;


import java.io.StringReader;
import controller.Controller;
import controller.util.PPMImageLoader;
import model.IImageState;
import model.ImageDataBase;
import model.ImageImpl;
import view.ImageTextView;
import view.ImageView;
import static org.junit.Assert.assertEquals;

/**
 * a test for the controller of the project.
 */
class ControllerTest {
  private ImageDataBase model;
  private ImageView view;
  Appendable output;

  @Before
  public void setUp() {
    model = new ImageDataBase();
    output = new StringBuilder();
    PPMImageLoader loader = new PPMImageLoader("res/red.ppm");
    IImageState image = new ImageImpl(2,3);
    model.add("test",image);
    view = new ImageTextView(model);
  }



  @Test
  public void testBlur() {
    new Controller(new StringReader("blur res/red.ppm red"),model,output).run();
    assertEquals("252 38  50  252 38  50  252 38  50  \n" +
            "252 38  50  252 38  50  252 38  50  \n" +
            "252 38  50  252 38  50  252 38  50  \n" +
            "252 38  50  252 38  50  252 38  50  ", view.toString());
  }

  @Test
  public void testSharpen() {
    new Controller(new StringReader("sharpen res/red.ppm red"), model, output).run();
    assertEquals("252 38  50  252 38  50  252 38  50  \n" +
            "252 38  50  252 38  50  252 38  50  \n" +
            "252 38  50  252 38  50  252 38  50  \n" +
            "252 38  50  252 38  50  252 38  50  ", view.toString());
  }

  @Test
  public void testSepia() {
    new Controller(new StringReader("sepia res/3x3.ppm sepiaImage"), model, output).run();
    new Controller(new StringReader("save sepiaImage.ppm sepiaImage"), model,output).run();
    assertEquals("829 133  175  829 133  175  829 133  175  \n" +
            "829 133  175  829 133  175  829 133  175  \n" +
            "829 133  175  829 133  175  829 133  175  \n" +
            "829 133  175  829 133  175  829 133  175  ", view.toString());

  }

  @Test
  public void testGreyScale() {
    new Controller(new StringReader("greyScale res/3x3.ppm greyImage"), model, output).run();
    assertEquals("829 133  175  829 133  175  829 133  175  \n" +
            "829 133  175  829 133  175  829 133  175  \n" +
            "829 133  175  829 133  175  829 133  175  \n" +
            "829 133  175  829 133  175  829 133  175  ", view.toString());
  }







}
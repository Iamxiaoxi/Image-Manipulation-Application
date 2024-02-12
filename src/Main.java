import java.io.InputStreamReader;
import java.io.Reader;

import controller.Controller;
import controller.ControllerGUI;
import controller.IController;
import model.IImageDataBase;
import model.IModel;
import model.ImageDataBase;
import model.Model;
import view.View;

/**
 * a main class to run and test the program.
 */
public class Main {
  /**
   * a main class to run and test the program.
   */
  public static void main(String [] args) {
    IImageDataBase model = new ImageDataBase();
    View view = new View();
    IController controller = new ControllerGUI(model, view);
    controller.run();
  }
  /**
  public static void main(String []args) {
    try {
      Reader input = new InputStreamReader(System.in);
      IImageDataBase model = new ImageDataBase();
      IController controller = new Controller(input, model, System.out);
      controller.run();
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());


    }
  }

   **/
}

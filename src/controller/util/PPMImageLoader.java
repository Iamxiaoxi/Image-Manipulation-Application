package controller.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

import model.IImage;
import model.IImageState;
import model.ImageImpl;

/**
 * a helper class for the loadppmcommand, to loop through the ppm file and load the image.
 */
public class PPMImageLoader implements IImageLoader {
  private final String filePath;

  public PPMImageLoader(String filePath) {

    this.filePath = Objects.requireNonNull(filePath);
  }

  @Override
  public IImageState run() {
    Scanner sc = null;

    //transform the ppm into a string.
    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      System.out.println("File" + filePath + " not found!");
    }
    StringBuilder builder = new StringBuilder();



    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    //create the image.
    IImage image = new ImageImpl(width, height);

    //get the r g b.
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        image.setPixel(col,row,r,g,b);
      }

    }
    return image;


  }


}

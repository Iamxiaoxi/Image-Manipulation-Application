package view;

import java.io.IOException;

import model.IImageDataBase;
import model.IImageState;

/**
 * a class to show the view of the project.
 */
public class ImageTextView implements ImageView {

  private final IImageDataBase model;
  private final Appendable destination;

  /**
   * a public constructor for the imageTextView class.
   */
  public ImageTextView(IImageDataBase model) {

    if (model == null) {
      throw new IllegalArgumentException("model is null.");
    }
    this.model = model;
    this.destination = System.out;
  }

  /**
   * a public constructor for the imageTextView class.
   */
  public ImageTextView(IImageDataBase model, Appendable destination) {
    if (model == null) {
      throw new IllegalArgumentException("model is null.");
    }
    if (destination == null) {
      throw new IllegalArgumentException("destination is null.");
    }
    this.model = model;
    this.destination = destination;
  }


    @Override
    public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (IImageState image : model.getImages().values()) {
      stringBuilder.append("P3\n");
      stringBuilder.append(image.getWidth() + " " + image.getHeight() + "\n");
      stringBuilder.append("255\n");

      for (int row = 0; row < image.getHeight(); row++) {
        for (int col = 0; col < image.getWidth(); col++) {
          stringBuilder.append(image.getRedChannel(row, col)
                  + " "
                  + image.getGreenChannel(row, col)
                  + " "
                  + image.getBlueChannel(row, col));

        }
      }
    }
    return stringBuilder.toString();
  }

  @Override
  public void renderImage() throws IOException {
    try {
      destination.append(toString());
    } catch (IOException e) {
      throw new IOException("rendering image failed",e);
    }
  }


  @Override
  public void renderMessage(String message) throws IOException {
    try {
      destination.append(message);
    } catch (IOException e) {
      throw new IOException("rendering message failed",e);
    }
  }
}

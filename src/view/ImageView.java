package view;

import java.io.IOException;

/**
 * an interface for the view of the project.
 */
public interface ImageView {

  String toString();

  void renderImage() throws IOException;

  void renderMessage(String message) throws IOException;
}

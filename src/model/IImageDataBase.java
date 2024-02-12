package model;


import java.util.Map;

/**
 * an interface for the model, that serves as the database for image storage.
 */
public interface IImageDataBase {
  // not preferable to have the IImage here, but for this assignment it's okay.
  void add(String id, IImageState image);

  IImageState get(String id);
  // there is an option to put this in the controller package.

  public Map<String, IImageState> getImages();
}

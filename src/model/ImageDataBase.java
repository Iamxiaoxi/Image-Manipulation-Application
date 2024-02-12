package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



/**
 * a class for the model, which stores images in a map.
 */
public class ImageDataBase implements IImageDataBase {
  private final Map<String, IImageState> images;

  public ImageDataBase() {
    this.images = new HashMap<>();
  }

  @Override
  public void add(String id, IImageState image) {
    if (id == null || image == null) {
      throw new IllegalArgumentException("id or image is null");
    }
    this.images.put(id,image);

  }

  @Override
  public IImageState get(String id) {
    return this.images.get(Objects.requireNonNull(id));

  }

  @Override
  public Map<String, IImageState> getImages() {
    return this.images;
  }
}

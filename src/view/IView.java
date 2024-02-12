package view;

import java.awt.image.BufferedImage;

public interface IView {
  void renderImage(BufferedImage image);
  void addVieListener(ViewListener listener);
  void setVisible(boolean value);
  void setViewText(String dataFromModel);
  void requestFramFocus();
  String getTextFromView();

}

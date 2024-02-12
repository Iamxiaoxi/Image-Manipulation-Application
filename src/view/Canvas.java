package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;


public class Canvas extends JComponent {
  private BufferedImage image;
  private String currentImageID;


  public Canvas(BufferedImage image) {
    this.image = image;
  }


  public void setImage(BufferedImage image) {
    this.image = image;
    repaint(); // Repaint the Canvas to display the new image
  }


  public String getCurrentImageID() {
    return currentImageID;
  }


  public void setCurrentImageID(String newImageID) {
    this.currentImageID = newImageID;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.cyan);

    // Check if an image is available
    if (image != null) {
      // Draw the image at position (0, 0) on the canvas
      g.drawImage(image, 0, 0, null);
    } else {
      // If no image is available, fill the canvas with the background color
      g.fillRect(0, 0, getWidth(), getHeight());
    }
  }
}

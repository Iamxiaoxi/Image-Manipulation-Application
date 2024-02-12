package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controller.ControllerGUI;


public class View extends JFrame implements ActionListener {
  private Canvas canvas;
  private JPanel panel;

  private final List<ViewListener> listenersToNotify;

  public View() {
    listenersToNotify = new ArrayList<>();
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(800, 600));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    panel = new JPanel(new BorderLayout());
    add(panel);

    // Create the initial Canvas with null image
    canvas = new Canvas(null);
    panel.add(canvas, BorderLayout.CENTER);

    JPanel panelEast = new JPanel(new GridLayout(0, 1));
    JPanel panelWest = new JPanel();
    BoxLayout boxLayout = new BoxLayout(panelWest, BoxLayout.PAGE_AXIS);
    panelWest.setLayout(boxLayout);

    JButton loadButton = new JButton("Load Image");
    loadButton.setActionCommand("load");
    loadButton.setPreferredSize(new Dimension(loadButton.getPreferredSize().width,
            loadButton.getPreferredSize().height * 2));

    JButton saveButton = new JButton("Save Image");
    saveButton.setActionCommand("save");
    saveButton.setPreferredSize(new Dimension(saveButton.getPreferredSize().width,
            saveButton.getPreferredSize().height * 2));

    panelWest.add(loadButton);
    panelWest.add(Box.createVerticalGlue()); // Add a vertical glue to create spacing
    panelWest.add(saveButton);

    panel.add(panelEast, BorderLayout.EAST);
    panel.add(panelWest, BorderLayout.WEST);

    loadButton.addActionListener(this);
    saveButton.addActionListener(this);

    // Adding additional buttons to panelEast

    JButton greenComponentButton = new JButton("Green-Component");
    JButton redComponentButton = new JButton("Red-Component");
    JButton brightenButton = new JButton("Brighten");
    JButton intensityComponentButton = new JButton("Intensity-Component");
    JButton lumaComponentButton = new JButton("Luma-Component");
    JButton valueComponentButton = new JButton("Value-Component");
    JButton blurButton = new JButton("Blur");
    JButton sharpenButton = new JButton("Sharpen");
    JButton greyscaleButton = new JButton("Greyscale");
    JButton sepiaButton = new JButton("Sepia");
    JButton blueComponentButton = new JButton("Blue-Component");

    blurButton.addActionListener(this);
    sharpenButton.addActionListener(this);
    greyscaleButton.addActionListener(this);
    sepiaButton.addActionListener(this);
    blueComponentButton.addActionListener(this);
    greenComponentButton.addActionListener(this);
    redComponentButton.addActionListener(this);
    brightenButton.addActionListener(this);
    intensityComponentButton.addActionListener(this);
    lumaComponentButton.addActionListener(this);
    valueComponentButton.addActionListener(this);


    panelEast.add(blueComponentButton);
    panelEast.add(greenComponentButton);
    panelEast.add(redComponentButton);
    panelEast.add(brightenButton);
    panelEast.add(intensityComponentButton);
    panelEast.add(lumaComponentButton);
    panelEast.add(valueComponentButton);
    panelEast.add(blurButton);
    panelEast.add(sharpenButton);
    panelEast.add(greyscaleButton);
    panelEast.add(sepiaButton);

    this.setFocusable(true);
    pack();
  }

  public void setCanvasImage(BufferedImage bufferedImage) {
    canvas.setImage(bufferedImage);
  }

  public Canvas getCanvas() {
    return this.canvas;
  }

  public void addViewListener(ControllerGUI listener) {
    this.listenersToNotify.add(listener);
  }

  private void emitLoadEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleLoadEvent();
    }
  }

  private void emitSaveEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleSaveEvent();
    }
  }

  private void emitBlurEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleBlurEvent();
    }
  }

  private void emitSharpenEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleSharpenEvent();
    }
  }

  private void emitGreyscaleEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleGreyscaleEvent();
    }
  }

  private void emitSepiaEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleSepiaEvent();
    }
  }

  private void emitBlueComponentEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleBlueComponentEvent();
    }
  }

  private void emitGreenComponentEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleGreenComponentEvent();
    }
  }

  private void emitRedComponentEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleRedComponentEvent();
    }
  }

  private void emitBrightenEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleBrightenEvent();
    }
  }

  private void emitIntensityComponentEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleIntensityComponentEvent();
    }
  }

  private void emitLumaComponentEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleLumaComponentEvent();
    }
  }

  private void emitValueComponentEvent() throws IOException {
    for (ViewListener listener : listenersToNotify) {
      listener.handleValueComponentEvent();
    }
  }




  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "load":
        try {
          emitLoadEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "save":
        try {
          emitSaveEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Blur":
        try {
          emitBlurEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Sharpen":
        try {
          emitSharpenEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Greyscale":
        try {
          emitGreyscaleEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Sepia":
        try {
          emitSepiaEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Blue-Component":
        try {
          emitBlueComponentEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Green-Component":
        try {
          emitGreenComponentEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Red-Component":
        try {
          emitRedComponentEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Brighten":
        try {
          emitBrightenEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Intensity-Component":
        try {
          emitIntensityComponentEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Luma-Component":
        try {
          emitLumaComponentEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      case "Value-Component":
        try {
          emitValueComponentEvent();
        } catch (IOException ex) {
          throw new RuntimeException(ex);
        }
        break;
      default:
        throw new IllegalStateException("Unknown action command.");
    }
  }


}


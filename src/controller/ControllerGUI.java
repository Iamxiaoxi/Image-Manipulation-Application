package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import controller.util.IImageLoader;
import controller.util.IImageSaver;
import controller.util.ImageLoader;
import controller.util.JPEGImageSaver;
import controller.util.PNGImageSaver;
import controller.util.PPMImageLoader;
import controller.util.PPMImageSaver;
import model.IImageDataBase;
import model.IImageState;

import model.transformations.BlueTransformation;
import model.transformations.BlurTransformation;
import model.transformations.BrightenTransformation;

import model.transformations.GreenTransformation;
import model.transformations.GreyscaleTransformation;
import model.transformations.ITransformation;

import model.transformations.IntensityTransformation;

import model.transformations.LumaTransformation;

import model.transformations.RedTransformation;
import model.transformations.SepiaTransformation;
import model.transformations.SharpenedTransformation;

import model.transformations.ValueTransformation;

import view.View;
import view.ViewListener;


public class ControllerGUI implements IController, ViewListener {
  private final IImageDataBase model;
  private final View view;


  public ControllerGUI(IImageDataBase model, View view) {
    this.model = Objects.requireNonNull(model);
    this.view = Objects.requireNonNull(view);
    this.view.addViewListener(this);
  }


  @Override
  public void run() {
    view.setVisible(true);
  }


  @Override
  public void handleLoadEvent() {
    JFileChooser fileChooser = new JFileChooser();
    javax.swing.filechooser.FileNameExtensionFilter ppmFilter =
            new javax.swing.filechooser.FileNameExtensionFilter("PPM Image", "ppm");
    javax.swing.filechooser.FileNameExtensionFilter pngFilter =
            new javax.swing.filechooser.FileNameExtensionFilter("PNG Image", "png");
    javax.swing.filechooser.FileNameExtensionFilter jpgFilter =
            new javax.swing.filechooser.FileNameExtensionFilter("JPEG Image", "jpg", "jpeg");
    fileChooser.addChoosableFileFilter(ppmFilter);
    fileChooser.addChoosableFileFilter(pngFilter);
    fileChooser.addChoosableFileFilter(jpgFilter);
    fileChooser.setAcceptAllFileFilterUsed(false);

    int returnVal = fileChooser.showOpenDialog(null);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String filePath = selectedFile.getAbsolutePath();
      String extension = getFileExtension(selectedFile);

      IImageState image;
      IImageLoader loader;

      if (extension.equalsIgnoreCase("ppm")) {
        loader = new PPMImageLoader(filePath);
      } else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
        loader = new ImageLoader(filePath);
      } else if (extension.equalsIgnoreCase("png")) {
        loader = new ImageLoader(filePath);
      } else {
        System.err.println("Unsupported file format: " + extension);
        return;
      }

      image = loader.run();
      if (image == null) {
        System.err.println("Can't load image");
      } else {
        System.out.println("Image loaded successfully.");
        BufferedImage bufferedImage = image.getImage();
        if (bufferedImage != null) {
          String imageID = ControllerGUI.getImageIDFromPath(filePath);
          model.add(imageID, image);
          view.setCanvasImage(bufferedImage);
          view.getCanvas().setCurrentImageID(imageID);
          view.getCanvas().repaint();
          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Failed to convert IImageState to BufferedImage.");
        }
      }
    }
  }

  @Override
  public void handleSaveEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();

    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setSelectedFile(new File(sourceImageID));

    int returnValue = fileChooser.showSaveDialog(null);

    if (returnValue == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      String pathToSave = selectedFile.getAbsolutePath();

      IImageState sourceImage = model.get(sourceImageID);
      if (sourceImage == null) {
        throw new IllegalStateException("Image does not exist.");
      }

      // Check the extension and use the appropriate saver
      String extension = getFileExtension(new File(pathToSave)).toLowerCase();
      IImageSaver saver;
      if (extension.equals("ppm")) {
        saver = new PPMImageSaver(pathToSave, sourceImage, new StringBuilder());
      } else if (extension.equals("jpg") || extension.equals("jpeg")) {
        saver = new JPEGImageSaver(pathToSave, sourceImage);
      } else if (extension.equals("png")) {
        saver = new PNGImageSaver(pathToSave, sourceImage);
      } else {
        JOptionPane.showMessageDialog(null,
                "Unsupported file format: " + extension,
                "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }


      saver.run(pathToSave);
    } else {
      System.out.println("File saving was canceled");
    }
  }


  @Override
  public void handleBrightenEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    int value = Integer.parseInt(JOptionPane.showInputDialog("Enter the brighten value:"));

    IImageState sourceImage = model.get(sourceImageID);
    if (sourceImage != null) {
      ITransformation brightenTransformation = new BrightenTransformation(value);
      IImageState brightenedImage = brightenTransformation.run(sourceImage);

      if (brightenedImage != null) {
        String destImageID = sourceImageID + "_brightened";
        model.add(destImageID, brightenedImage);


        BufferedImage bufferedImage = brightenedImage.getImage();
        if (bufferedImage != null) {

          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed.");
        }
      } else {
        System.err.println("Action failed.");
      }
    } else {
      System.err.println("Action failed.");
    }
  }


  @Override
  public void handleValueComponentEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    IImageState sourceImage = model.get(sourceImageID);

    if (sourceImage != null) {
      ITransformation valueComponentTransformation = new ValueTransformation();
      IImageState transformedImage = valueComponentTransformation.run(sourceImage);

      if (transformedImage != null) {
        String destImageID = sourceImageID + "_value_component";
        model.add(destImageID, transformedImage);

        // Retrieve the buffered image from the brightened image
        BufferedImage bufferedImage = transformedImage.getImage();
        if (bufferedImage != null) {
          // Update the Canvas with the new image and image ID
          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed.");
        }
      } else {
        System.err.println("Action failed");
      }
    }
  }

  @Override
  public void handleLumaComponentEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    IImageState sourceImage = model.get(sourceImageID);

    if (sourceImage != null) {
      ITransformation lumaComponentTransformation = new LumaTransformation();
      IImageState transformedImage = lumaComponentTransformation.run(sourceImage);

      if (transformedImage != null) {
        String destImageID = sourceImageID + "_luma_component";
        model.add(destImageID, transformedImage);

        // Retrieve the buffered image from the transformed image
        BufferedImage bufferedImage = transformedImage.getImage();
        if (bufferedImage != null) {
          // Update the Canvas with the new image and image ID
          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed");
        }
      } else {
        System.err.println("Action failed");
      }
    }
  }

  @Override
  public void handleIntensityComponentEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    IImageState sourceImage = model.get(sourceImageID);

    if (sourceImage != null) {
      ITransformation intensityComponentTransformation = new IntensityTransformation();
      IImageState transformedImage = intensityComponentTransformation.run(sourceImage);

      if (transformedImage != null) {
        String destImageID = sourceImageID + "_intensity_component";
        model.add(destImageID, transformedImage);

        // Retrieve the buffered image from the transformed image
        BufferedImage bufferedImage = transformedImage.getImage();
        if (bufferedImage != null) {
          // Update the Canvas with the new image and image ID
          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed");
        }
      } else {
        System.err.println("Action failed");
      }
    }
  }

  @Override
  public void handleRedComponentEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    IImageState sourceImage = model.get(sourceImageID);

    if (sourceImage != null) {
      ITransformation redComponentTransformation = new RedTransformation();
      IImageState transformedImage = redComponentTransformation.run(sourceImage);

      if (transformedImage != null) {
        String destImageID = sourceImageID + "_red_component";
        model.add(destImageID, transformedImage);

        // Retrieve the buffered image from the transformed image
        BufferedImage bufferedImage = transformedImage.getImage();
        if (bufferedImage != null) {
          // Update the Canvas with the new image and image ID
          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed");
        }
      } else {
        System.err.println("Action failed");
      }
    }
  }

  @Override
  public void handleGreenComponentEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    IImageState sourceImage = model.get(sourceImageID);

    if (sourceImage != null) {
      ITransformation greenComponentTransformation = new GreenTransformation();
      IImageState transformedImage = greenComponentTransformation.run(sourceImage);

      if (transformedImage != null) {
        String destImageID = sourceImageID + "_green_component";
        model.add(destImageID, transformedImage);

        // Retrieve the buffered image from the transformed image
        BufferedImage bufferedImage = transformedImage.getImage();
        if (bufferedImage != null) {
          // Update the Canvas with the new image and image ID
          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed");
        }
      } else {
        System.err.println("Action failed");
      }
    }
  }

  @Override
  public void handleBlueComponentEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    IImageState sourceImage = model.get(sourceImageID);

    if (sourceImage != null) {
      ITransformation blueComponentTransformation = new BlueTransformation();
      IImageState transformedImage = blueComponentTransformation.run(sourceImage);

      if (transformedImage != null) {
        String destImageID = sourceImageID + "_blue_component";
        model.add(destImageID, transformedImage);


        BufferedImage bufferedImage = transformedImage.getImage();
        if (bufferedImage != null) {

          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed");
        }
      } else {
        System.err.println("Action failed");
      }
    }
  }

  @Override
  public void handleSepiaEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    IImageState sourceImage = model.get(sourceImageID);

    if (sourceImage != null) {

      ITransformation sepiaFilterTransformation = new SepiaTransformation();
      IImageState transformedImage = sepiaFilterTransformation.run(sourceImage);

      if (transformedImage != null) {
        String destImageID = sourceImageID + "_sepia";
        model.add(destImageID, transformedImage);

        // Retrieve the buffered image from the transformed image
        BufferedImage bufferedImage = transformedImage.getImage();
        if (bufferedImage != null) {
          // Update the Canvas with the new image and image ID
          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed");
        }
      } else {
        System.err.println("Action failed");
      }
    }
  }

  @Override
  public void handleGreyscaleEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    IImageState sourceImage = model.get(sourceImageID);

    if (sourceImage != null) {


      ITransformation greyscaleFilterTransformation = new GreyscaleTransformation();
      IImageState transformedImage = greyscaleFilterTransformation.run(sourceImage);

      if (transformedImage != null) {
        String destImageID = sourceImageID + "_greyscale";
        model.add(destImageID, transformedImage);

        // Retrieve the buffered image from the transformed image
        BufferedImage bufferedImage = transformedImage.getImage();
        if (bufferedImage != null) {
          // Update the Canvas with the new image and image ID
          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed");
        }
      } else {
        System.err.println("Action failed");
      }
    }
  }

  @Override
  public void handleSharpenEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    IImageState sourceImage = model.get(sourceImageID);

    if (sourceImage != null) {
      ITransformation sharpenTransformation = new SharpenedTransformation();
      IImageState transformedImage = sharpenTransformation.run(sourceImage);

      if (transformedImage != null) {
        String destImageID = sourceImageID + "_sharpen";
        model.add(destImageID, transformedImage);

        // Retrieve the buffered image from the transformed image
        BufferedImage bufferedImage = transformedImage.getImage();
        if (bufferedImage != null) {
          // Update the Canvas with the new image and image ID
          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed");
        }
      } else {
        System.err.println("Action failed");
      }
    }
  }

  @Override
  public void handleBlurEvent() {
    String sourceImageID = view.getCanvas().getCurrentImageID();
    IImageState sourceImage = model.get(sourceImageID);

    if (sourceImage != null) {
      ITransformation blurTransformation = new BlurTransformation();
      IImageState transformedImage = blurTransformation.run(sourceImage);

      if (transformedImage != null) {
        String destImageID = sourceImageID + "_blur";
        model.add(destImageID, transformedImage);

        // Retrieve the buffered image from the transformed image
        BufferedImage bufferedImage = transformedImage.getImage();
        if (bufferedImage != null) {
          // Update the Canvas with the new image and image ID
          view.getCanvas().setImage(bufferedImage);
          view.getCanvas().setCurrentImageID(destImageID);

          view.setVisible(true);
          view.pack();
        } else {
          System.err.println("Action failed");
        }
      } else {
        System.err.println("Action failed");
      }
    }
  }



  private String getFileExtension(File file) {
    String name = file.getName();
    int lastIndex = name.lastIndexOf('.');
    if (lastIndex != -1 && lastIndex < name.length() - 1) {
      return name.substring(lastIndex + 1).toLowerCase();
    }
    return "";
  }


  public static String getImageIDFromPath(String filePath) {
    File file = new File(filePath);
    String fileName = file.getName();

    int extensionIndex = fileName.lastIndexOf('.');
    if (extensionIndex != -1) {
      fileName = fileName.substring(0, extensionIndex);
    }

    return fileName;
  }

}


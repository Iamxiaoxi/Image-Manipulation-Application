package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import controller.commands.BlueComponentCommand;
import controller.commands.GreenComponentCommand;
import controller.commands.ICommand;
import controller.commands.IntensityComponentCommand;
import controller.commands.LoadCommand;
import controller.commands.LumaComponentCommand;
import controller.commands.RedComponentCommand;
import controller.commands.SaveCommand;
import controller.commands.ValueComponentCommand;
import controller.commands.filter.BlurCommand;
import controller.commands.filter.GreyscaleCommand;
import controller.commands.filter.SepiaCommand;
import controller.commands.filter.SharpenCommand;
import model.IImageDataBase;
import controller.commands.BrightenCommand;

/**
 * a controller class for taking input from the user and give it to the model.
 */
public class Controller implements IController {
  private final Readable input;
  private final IImageDataBase model;
  private final Appendable appendable;
  private final Map<String, ICommand> commandMap;

  /**
   * a constructor for the controller.
   */
  public Controller(Readable input, IImageDataBase model, Appendable appendable) {
    this.input = Objects.requireNonNull(input);
    this.model = Objects.requireNonNull(model);
    this.appendable = Objects.requireNonNull(appendable);

    this.commandMap = new HashMap<String, ICommand>();
    this.commandMap.put("brighten", new BrightenCommand());
    this.commandMap.put("luma-component", new LumaComponentCommand());
    this.commandMap.put("value-component", new ValueComponentCommand());
    this.commandMap.put("intensity-component", new IntensityComponentCommand());
    this.commandMap.put("red-component", new RedComponentCommand());
    this.commandMap.put("green-component", new GreenComponentCommand());
    this.commandMap.put("blue-component", new BlueComponentCommand());
    this.commandMap.put("save", new SaveCommand());
    this.commandMap.put("load", new LoadCommand());
    this.commandMap.put("blur", new BlurCommand());
    this.commandMap.put("sharpen", new SharpenCommand());
    this.commandMap.put("greyscale", new GreyscaleCommand());
    this.commandMap.put("sepia", new SepiaCommand());

  }

  private void write(String message) {
    try {
      this.appendable.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("writing to the appendable failed");
    }
  }

  @Override
  public void run() {
    Scanner scanner = new Scanner(this.input);

    while (scanner.hasNext()) {
      String command = scanner.next();

      if (command.equals("file")) {
        if (!scanner.hasNext()) {
          write("Invalid command.");
          continue;
        }

        String scriptPath = scanner.next();
        File scriptFile = new File(scriptPath);

        Reader reader;

        try {
          reader = new FileReader(scriptFile);
          Readable script = new BufferedReader(reader);
          Scanner scriptScanner = new Scanner(script);

          while (scriptScanner.hasNext()) {
            String scriptCommand = scriptScanner.next();

            ICommand commandToRun = this.commandMap.getOrDefault(command, null);
            if (command == null) {
              write("Invalid command.");
              continue;
            }
            try {
              commandToRun.run(scanner, this.model);
            } catch (IllegalStateException e) {
              write(e.getMessage());
            }
          }
          scriptScanner.close();
          System.exit(0);

        } catch (IOException e) {
          write(e.getMessage());
          System.exit(1);
        }
      } else {
        ICommand commandtoRun = this.commandMap.getOrDefault(command, null);
        if (command == null) {
          write("Invalid command.");
          continue;
        }
        try {
          commandtoRun.run(scanner, this.model);
        } catch (IllegalStateException e) {
          write(e.getMessage());
        }
      }
    }
  }
}

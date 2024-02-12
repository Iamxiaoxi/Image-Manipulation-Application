package controller.commands;

import java.io.IOException;
import java.util.Scanner;

import model.IImageDataBase;

/**
 * an interface for the commands, contains one function called run() that runs the program.
 */
public interface ICommand {
  void run(Scanner scanner, IImageDataBase model);

}

package model.transformations;

import model.IImageState;

/**
 * an interface for the transformation classes,
 * that serve as helper functions for the command classes.
 */
public interface ITransformation {
  IImageState run(IImageState sourceImage);
}

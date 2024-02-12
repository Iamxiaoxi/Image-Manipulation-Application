package model;

import java.util.Objects;

/**
 * Created by vidojemihajlovikj on 7/26/23.
 */
public class Model implements IModel {
  private String data;

  public Model(){
    this.data = "";
  }
  @Override
  public String getData() {
    return this.data.toUpperCase();
  }

  @Override
  public void setData(String data) {
    this.data = Objects.requireNonNull(data);
  }
}

package view;


public interface ViewListener {

  void handleLoadEvent();


  void handleSaveEvent();


  void handleValueComponentEvent();


  void handleLumaComponentEvent();


  void handleIntensityComponentEvent();


  void handleBrightenEvent();


  void handleRedComponentEvent();


  void handleGreenComponentEvent();


  void handleBlueComponentEvent();


  void handleSepiaEvent();


  void handleGreyscaleEvent();


  void handleSharpenEvent();


  void handleBlurEvent();
}
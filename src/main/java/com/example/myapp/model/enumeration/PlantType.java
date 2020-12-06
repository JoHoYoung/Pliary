package com.example.myapp.model.enumeration;

public enum PlantType {
  STUKI(30),
  EUCALYPTUS(4),
  SANSEVIERIA(30),
  MONSTERA(5),
  PARLOUPALM(7),
  ELASTICA(7),
  TRAVELERSPALM(10),
  SCHEFFLERA(5),
  HANGING(0),
  CUSTOM(0),
  ;

  int wateringDay;

  PlantType(int wateringDay) {
    this.wateringDay = wateringDay;
  }

  public int getWateringDay() {
    return this.wateringDay;
  }
}

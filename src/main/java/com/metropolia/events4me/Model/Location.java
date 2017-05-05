package com.metropolia.events4me.Model;

/**
 * Created by mahdiaza on 01/05/17.
 */
public enum Location {
  Cafe_Mascot("Neljäs linja 2, 00530 Helsinki"), Lady_Moon("Kaivokatu 12, 00100 Helsinki");
  private String address;

  private Location(String address){
  this.address = address;

  }

  public String getAddress(){
    return this.address;
  }
}

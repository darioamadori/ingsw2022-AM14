package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import java.util.Vector;

public class MoveMessageResponse extends Message{

  private int place;
  private int studentColor;
  private int islandNumber;
  private int cloudTileNumber;
  private int[] studentsEntrance;
  private int[] studentsDiningRoom;
  private int[] studentsIsland;
  private int[] studentsCloud;
  private int[] professors;
  private Integer towersIsland;
  private int playerCoins;

  public MoveMessageResponse(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.MOVE;
    this.messageSecondary = messageSecondary;
  }

  public int getPlace() {
    return place;
  }

  public int getStudentColor() {
    return studentColor;
  }

  public int getIslandNumber() {
    return islandNumber;
  }

  public int getCloudTileNumber() {
    return cloudTileNumber;
  }

  public int[] getStudentsEntrance() {
    return studentsEntrance;
  }

  public int[] getStudentsDiningRoom() {
    return studentsDiningRoom;
  }

  public int[] getStudentsIsland() {
    return studentsIsland;
  }

  public int[] getStudentsCloud() {
    return studentsCloud;
  }

  public void setPlace(int place) {
    this.place = place;
  }

  public void setStudentColor(int studentColor) {
    this.studentColor = studentColor;
  }

  public void setIslandNumber(int islandNumber) {
    this.islandNumber = islandNumber;
  }

  public void setCloudTileNumber(int cloudTileNumber) {
    this.cloudTileNumber = cloudTileNumber;
  }

  public void setStudentsEntrance(int[] studentsEntrance) {
    this.studentsEntrance = studentsEntrance;
  }

  public void setStudentsDiningRoom(int[] studentsDiningRoom) {
    this.studentsDiningRoom = studentsDiningRoom;
  }

  public void setStudentsIsland(int[] studentsIsland) {
    this.studentsIsland = studentsIsland;
  }

  public void setStudentsCloud(int[] studentsCloud) {
    this.studentsCloud = studentsCloud;
  }

  public int[] getProfessors() {
    return professors;
  }

  public void setProfessors(int[] professors) {
    this.professors = professors;
  }

  public Integer getTowersIsland() {
    return towersIsland;
  }

  public void setTowersIsland(Integer towersIsland) {
    this.towersIsland = towersIsland;
  }

  public int getPlayerCoins() {
    return playerCoins;
  }

  public void setPlayerCoins(int playerCoins) {
    this.playerCoins = playerCoins;
  }
}

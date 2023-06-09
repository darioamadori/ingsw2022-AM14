package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;

/**
 * PlayMessageResponse class used as a response from server to client
 */
public class PlayMessageResponse extends Message {

  private int assistantId;
  private int characterId;
  private String effectString;

  // Character parameters
  private Color color;
  private int numIsland;
  private int motherNatureMoves;

  private int[] studentsCard;
  private int[] studentsEntrance;
  private int[] studentsDiningRoom;

  private String response;
  private int activePlayerId;
  private int previousPlayerId;

  /**
   * Constructor method for PlayMessageResponse class
   *
   * @param messageSecondary The messageSecondary to be set
   */
  public PlayMessageResponse(MessageSecondary messageSecondary) {
    this.messageMain = MessageMain.PLAY;
    this.messageSecondary = messageSecondary;
  }

  public int getAssistantId() {
    return assistantId;
  }

  public void setAssistantId(int assistantId) {
    this.assistantId = assistantId;
  }

  public int getCharacterId() {
    return characterId;
  }

  public void setCharacterId(int characterId) {
    this.characterId = characterId;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public int getNumIsland() {
    return numIsland;
  }

  public void setNumIsland(int numIsland) {
    this.numIsland = numIsland;
  }

  public int getMotherNatureMoves() {
    return motherNatureMoves;
  }

  public void setMotherNatureMoves(int motherNatureMoves) {
    this.motherNatureMoves = motherNatureMoves;
  }

  public int[] getStudentsCard() {
    return studentsCard;
  }

  public void setStudentsCard(int[] studentsCard) {
    this.studentsCard = studentsCard;
  }

  public int[] getStudentsEntrance() {
    return studentsEntrance;
  }

  public void setStudentsEntrance(int[] studentsEntrance) {
    this.studentsEntrance = studentsEntrance;
  }

  public int[] getStudentsDiningRoom() {
    return studentsDiningRoom;
  }

  public void setStudentsDiningRoom(int[] studentsDiningRoom) {
    this.studentsDiningRoom = studentsDiningRoom;
  }

  public String getEffectString() {
    return effectString;
  }

  public void setEffectString(String effectString) {
    this.effectString = effectString;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  public int getActivePlayerId() {
    return activePlayerId;
  }

  public void setActivePlayerId(int activePlayerId) {
    this.activePlayerId = activePlayerId;
  }

  public int getPreviousPlayerId() {
    return previousPlayerId;
  }

  public void setPreviousPlayerId(int previousPlayerId) {
    this.previousPlayerId = previousPlayerId;
  }
}

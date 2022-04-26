package it.polimi.ingsw.messages;

import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageMain;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.board.MainBoard;
import it.polimi.ingsw.model.board.StudentsBag;
import it.polimi.ingsw.model.player.Player;
import java.util.Vector;

public class PlayMessage extends Message {

  private int assistantId;
  private int characterId;

  // Character parameters
  private Color color;
  private int numIsland;
  private int motherNatureMoves;

  private int[] studentsCard;
  private int[] studentsEntrance;
  private int[] studentsDiningRoom;

  public PlayMessage(MessageSecondary messageSecondary) {
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
}
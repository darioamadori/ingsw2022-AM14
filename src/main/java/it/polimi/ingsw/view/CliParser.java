package it.polimi.ingsw.view;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.BeginTurnMessage;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.LoginMessageResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessageResponse;
import it.polimi.ingsw.messages.PhaseMessageResponse;
import it.polimi.ingsw.messages.PlayMessageResponse;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CliParser {

  private int playerId;

  public CliParser() {
  }

  public String fromJson(String jsonMessage) throws FileNotFoundException {
    Gson gson = new Gson();
    if (jsonMessage.contains("LOGIN")) {
      return elaborateMessage(gson.fromJson(jsonMessage, LoginMessageResponse.class));
    }
    if (jsonMessage.contains("MOVE")) {
      return elaborateMessage(gson.fromJson(jsonMessage, MoveMessageResponse.class));
    }
    if (jsonMessage.contains("PLAY")) {
      return elaborateMessage(gson.fromJson(jsonMessage, PhaseMessageResponse.class));
    }
    if (jsonMessage.contains("INFO")) {
      return elaborateMessage(gson.fromJson(jsonMessage, ClientResponse.class));
    }
    if (jsonMessage.contains("PHASE")) {
      return elaborateMessage(gson.fromJson(jsonMessage, BeginTurnMessage.class));
    }
    return null;
  }

  private String elaborateMessage(Message msg) throws FileNotFoundException {
    switch (msg.getMessageMain()) {
      case LOGIN:
        return printLoginMessage((LoginMessageResponse) msg);
      case MOVE:
        return printMoveMessage((MoveMessageResponse) msg);
      case PLAY:
        return printPlayMessage((PlayMessageResponse) msg);
      case INFO:
        return printInfoMessage((ClientResponse) msg);
      case PHASE:
        return printPhaseMessage((BeginTurnMessage) msg);
    }
    return null;
  }

  private String printLoginMessage(LoginMessageResponse msg) {
    if (msg.getMessageSecondary() == MessageSecondary.PLAYER_PARAMS) {
      setPlayerId(msg.getPlayerId());
    }
    StringBuilder returnString = new StringBuilder();
    returnString.append(msg.getResponse());
    if (msg.getPlayerId() == 0 && msg.getMessageSecondary() == MessageSecondary.PLAYER_PARAMS) {
      returnString.append("Now you have to choose the game mode and number of players");
    }
    return String.valueOf(returnString);
  }

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }

  private String printPlayMessage(PlayMessageResponse msg) throws FileNotFoundException {
    StringBuilder returnString = new StringBuilder();
    switch (msg.getMessageSecondary()) {
      case ASSISTANT:
        Gson gson = new Gson();
        JsonArray list = gson
            .fromJson(new FileReader(
                    "src/main/resources/json/assistants.json"),
                JsonArray.class);
        JsonObject object = list.get(msg.getAssistantId()).getAsJsonObject();
        int moves = object.get("MOVES").getAsInt();
        int speed = object.get("SPEED").getAsInt();

        if (msg.getPlayerId() == this.playerId) {
          returnString
              .append("Assistant played correctly!\n" + "You can now move mother nature of ")
              .append(moves).append(" spaces when your turn comes.\n").append("You're speed is ")
              .append(speed).append(" ouf of 10.\n").append("Please wait...");
        } else {
          returnString.append("another player has player an assistant now he/she can do")
              .append(moves).append("moves with a speed of").append(speed)
              .append("you cannot play the same assistant");
        }
        break;
      case CHARACTER:
        returnString.append("Character").append(msg.getCharacterId())
            .append("purchased now you can").append(msg.getEffectString());
        //TODO
    }
    return String.valueOf(returnString);
  }

  private String printMoveMessage(MoveMessageResponse msg) {

    StringBuilder printedString = new StringBuilder();

    switch (msg.getMessageSecondary()) {
      case ENTRANCE:
        printedString.append("now your entrance contains");
        for (Color color : Color.values()) {
          printedString.append(msg.getStudentsEntrance()[color.ordinal()]).append(" ")
              .append(color).append(" students;\n");
        }
        if (msg.getPlace() == 0) {
          printedString.append("now your dining room contains");
          for (Color color : Color.values()) {
            printedString.append(msg.getStudentsDiningRoom()[color.ordinal()]).append(" ")
                .append(color).append(" students;\n");
          }
        } else if (msg.getPlace() == 1) {
          printedString.append("now the island").append(msg.getIslandNumber()).append("contains");
          for (Color color : Color.values()) {
            printedString.append(msg.getStudentsIsland()[color.ordinal()]).append(" ")
                .append(color).append(" students;\n");
          }
        }
        break;
      case CLOUD_TILE:
        printedString.append("cloud tile number").append(msg.getCloudTileNumber())
            .append("successfully taken").append("now your entrance contains");
        for (Color color : Color.values()) {
          printedString.append(msg.getStudentsCloud()[color.ordinal()]).append(" ")
              .append(color).append(" students;\n");
        }
        break;
      case MOTHER_NATURE:
        printedString.append("now mother nature is on the island number")
            .append(msg.getIslandNumber()).append("island");
        break;
    }
    return String.valueOf(printedString);
  }

  private String printInfoMessage(ClientResponse msg) {
    //TODO
    return null;
  }

  /**
   * This method creates the string to be printed in case of a turn message
   * @param msg the input message to decode and print
   * @return the string to be printed
   */
  private String printPhaseMessage(BeginTurnMessage msg) throws FileNotFoundException {
    StringBuilder returnString = new StringBuilder();
    switch (msg.getMessageSecondary()) {
      case INIT_GAME:
        returnString.append("The game has now started you are the player number ")
            .append(this.playerId).append("\n");

        returnString.append("The island are as follows").append("\n");
        for (int[] students : msg.getStudentsIsland()) {
          returnString.append("Island number ").append(msg.getStudentsIsland().indexOf(students))
              .append(": \n");
          for (Color color : Color.values()) {
            returnString.append(students[color.ordinal()]).append(" ")
                .append(color).append(" students;\n");
          }
          returnString.append("\n");
        }

        returnString.append("your and opponents entrance are as follows").append("\n");
        for (int[] students : msg.getStudentEntrance()) {
          if (msg.getStudentEntrance().indexOf(students) == this.playerId) {
            returnString.append("your entrance is:").append("\n");
          } else {
            returnString.append("an opponent entrance is").append("\n");
          }
          for (Color color : Color.values()) {
            returnString.append(students[color.ordinal()]).append(" ")
                .append(color).append(" students;\n");
          }
          returnString.append("\n");
        }

        returnString.append("these are the assistant tou can play :").append("\n");
        Gson gson = new Gson();
        JsonArray list = gson
            .fromJson(new FileReader("src/main/resources/json/assistants.json"), JsonArray.class);
        for (Integer idAssistant : msg.getPlayableAssistantId()) {

          JsonObject object = list.get(idAssistant).getAsJsonObject();
          int speed = object.get("SPEED").getAsInt();
          int moves = object.get("MOVES").getAsInt();

          returnString.append("assistant speed is").append(" ").append(speed).append("\n")
              .append("max mother nature moves").append(" ").append(moves).append("\n")
              .append("\n");
        }

      case CHANGE_TURN:
        //TODO
    }
    return returnString.toString();
  }
}

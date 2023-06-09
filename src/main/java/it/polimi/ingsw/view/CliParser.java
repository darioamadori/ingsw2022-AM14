package it.polimi.ingsw.view;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.helpers.Color;
import it.polimi.ingsw.helpers.Effects;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.StudentsPlayerId;
import it.polimi.ingsw.messages.BeginTurnMessage;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.LoginMessageResponse;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessageResponse;
import it.polimi.ingsw.messages.PlayMessageResponse;
import it.polimi.ingsw.messages.WinnerMessage;
import java.io.IOException;

/**
 * CliParser class used to parse the message from server into printable string
 */
public class CliParser {

  private int playerId;

  /**
   * Constructor method for CliParser class
   */
  public CliParser() {
  }

  /**
   * This  method is used to invoke different method depending on what kind of message arrives and
   * parse them into the correct objects
   *
   * @param jsonMessage The message in Json format to decode
   * @return The printable string
   */
  public String fromJson(String jsonMessage) throws IOException {
    Gson gson = new Gson();
    if (jsonMessage.contains("\"messageMain\":\"LOGIN\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, LoginMessageResponse.class));
    }
    if (jsonMessage.contains("\"messageMain\":\"MOVE\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, MoveMessageResponse.class));
    }
    if (jsonMessage.contains("\"messageMain\":\"PLAY\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, PlayMessageResponse.class));
    }
    if (jsonMessage.contains("\"messageMain\":\"INFO\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, ClientResponse.class));
    }
    if (jsonMessage.contains("\"messageMain\":\"PHASE\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, BeginTurnMessage.class));
    }
    if (jsonMessage.contains("\"messageMain\":\"END\"")) {
      return elaborateMessage(gson.fromJson(jsonMessage, WinnerMessage.class));
    }
    return "A error has occurred please check the input";
  }

  /**
   * Method used to invoke different methods that will build the printable string
   *
   * @param msg The message arrived from client
   * @return The printable string
   */
  private String elaborateMessage(Message msg) throws IOException {
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
      case END:
        return printWinMessage((WinnerMessage) msg);
    }
    return "A error has occurred please check the input";
  }

  /**
   * This method prints the LoginMessageResponse arrived from server
   *
   * @param msg The message arrived from client
   * @return The printable string
   */
  private String printLoginMessage(LoginMessageResponse msg) {
    if (msg.getMessageSecondary() == MessageSecondary.LOBBY) {
      setPlayerId(msg.getPlayerId());
    }
    StringBuilder returnString = new StringBuilder();
    returnString.append(msg.getResponse()).append("\n");
    if (msg.getPlayerId() == 0 && msg.getMessageSecondary() == MessageSecondary.ASK_GAME_PARAMS) {
      returnString.append("Now you have to choose the game mode and number of players");
    }
    if (msg.getMessageSecondary() == MessageSecondary.LOBBY) {
      returnString.append("welcome into the Eriantys lobby\n");
    }
    return String.valueOf(returnString);
  }

  public int getPlayerId() {
    return playerId;
  }

  public void setPlayerId(int playerId) {
    this.playerId = playerId;
  }


  /**
   * This method prints the PlayMessageResponse arrived from server
   *
   * @param msg The message arrived from client
   * @return The printable string
   */
  private String printPlayMessage(PlayMessageResponse msg) throws IOException {
    StringBuilder returnString = new StringBuilder();
    switch (msg.getMessageSecondary()) {
      case ASK_ASSISTANT:
        Gson gson = new Gson();

        String path =
            it.polimi.ingsw.network.server.FileReader.getPath("/json/assistants.json");

        JsonArray list = gson.fromJson(path, JsonArray.class);

        JsonObject object = list.get(msg.getAssistantId()).getAsJsonObject();
        int moves = object.get("MOVES").getAsInt();
        int speed = object.get("SPEED").getAsInt();

        if (this.playerId == msg.getPreviousPlayerId()) {
          returnString
              .append("Assistant played correctly!\n" + "You can now move mother nature of ")
              .append(moves).append(" spaces when your turn comes.\n").append("You're speed is ")
              .append(speed).append(" ouf of 10.\n").append("Please wait...").append("\n")
              .append("\n");
        } else {
          returnString.append("another player has player an assistant now he/she can do")
              .append(" ")
              .append(moves).append(" ").append("moves with a speed of ").append(speed).append("\n")
              .append("\n");
        }
        break;

      case CHARACTER:
        returnString.append("Character ").append(msg.getCharacterId())
            .append(" purchased and correctly used\n");
        break;
    }
    return String.valueOf(returnString);
  }


  /**
   * This method prints the MoveMessageResponse arrived from server
   *
   * @param msg The message arrived from client
   * @return The printable string
   */
  private String printMoveMessage(MoveMessageResponse msg) {

    StringBuilder printedString = new StringBuilder();

    switch (msg.getMessageSecondary()) {
      case MOVE_STUDENT_ENTRANCE:
        printedString.append("now your entrance contains").append("\n");
        for (Color color : Color.values()) {
          printedString.append(msg.getStudentsEntrance()[color.ordinal()]).append(" ")
              .append(color).append(" students;\n");
        }
        printedString.append("\n");

        if (msg.getPlace() == 0) {
          printedString.append(printMoveMessageDiningRoomCase(msg));

        } else if (msg.getPlace() == 1) {
          printedString.append(printMoveMessageIslandCase(msg));
        }
        break;

      case CLOUD_TILE:
        printedString.append("cloud tile number ").append(msg.getCloudTileNumber()).append("\n")
            .append("successfully taken").append("\n")
            .append("These student are added to your entrance :").append("\n");
        for (Color color : Color.values()) {
          printedString.append(msg.getStudentsCloud()[color.ordinal()]).append(" ")
              .append(color).append(" students;\n");
        }
        printedString.append("\n");
        break;

      case MOVE_MN:
        printedString.append("now mother nature is on the island number ")
            .append(msg.getIslandNumber()).append(" island\n");
        if (msg.getTowersIsland() > 0) {
          printedString.append("this island contains ").append(msg.getTowersIsland())
              .append(" towers\n");
          printedString.append("Belonging to the ").append(msg.getIslandOwnerId()).append("team");
        }
        break;
    }
    return String.valueOf(printedString);
  }

  /**
   * This method builds a string in case of a MoveMessageResponse to an island
   *
   * @param msg The message arrived from client
   * @return The printable string
   */
  private String printMoveMessageIslandCase(MoveMessageResponse msg) {
    StringBuilder printedString = new StringBuilder();
    printedString.append("now the island ").append(msg.getIslandNumber())
        .append(" contains\n");
    for (Color color : Color.values()) {
      printedString.append(msg.getStudentsIsland()[color.ordinal()]).append(" ")
          .append(color).append(" students;\n");
    }
    if (msg.getTowersIsland() > 0) {
      printedString.append("this island contains ").append(msg.getTowersIsland())
          .append(" towers\n");
      printedString.append("Belonging to the ").append(msg.getIslandOwnerId()).append("team");
    }
    return printedString.toString();
  }

  /**
   * This method builds a string in case of a MoveMessageResponse to the dining room
   *
   * @param msg The message arrived from client
   * @return The printable string
   */
  private String printMoveMessageDiningRoomCase(MoveMessageResponse msg) {
    StringBuilder printedString = new StringBuilder();
    printedString.append("now your dining room contains\n");
    for (Color color : Color.values()) {
      printedString.append(msg.getStudentsDiningRoom()[color.ordinal()]).append(" ")
          .append(color).append(" students;\n");
    }
    printedString.append("\n");

    printedString.append("now you own these professors: \n");
    for (Color color : Color.values()) {
      if (msg.getProfessors()[color.ordinal()] == playerId) {
        printedString.append(color).append(" professor;\n");
      }
    }
    printedString.append("\n");
    printedString.append("now you have ").append(msg.getPlayerCoins()).append(" coins\n");

    return printedString.toString();
  }

  /**
   * This method prints the ClientResponse arrived from server
   *
   * @param msg The message arrived from client
   * @return The printable string
   */
  private String printInfoMessage(ClientResponse msg) {
    if (msg.getMessageSecondary() == MessageSecondary.GAME_ORDER) {
      return "You have to play as " + (msg.getPlayerOrderId().indexOf(this.playerId) + 1)
          + " player";
    }
    return msg.getResponse();

  }

  /**
   * This method creates the string to be printed in case of a Winner message
   *
   * @param msg the input message to decode and print
   * @return the string to be printed
   */
  private String printWinMessage(WinnerMessage msg) {
    StringBuilder returnedString = new StringBuilder();
    returnedString.append("The game is now finished").append("\n");
    returnedString.append("The winner is........... The ").append(msg.getWinnerId())
        .append(" team\n");
    returnedString.append("composed by:\n");
    for (String winnerNick : msg.getPlayersTeam()) {
      returnedString.append(winnerNick).append("\n");
    }
    return returnedString.toString();
  }

  /**
   * This method creates the string to be printed in case of a turn message
   *
   * @param msg the input message to decode and print
   * @return the string to be printed
   */
  private String printPhaseMessage(BeginTurnMessage msg) throws IOException {
    StringBuilder returnString = new StringBuilder();
    switch (msg.getMessageSecondary()) {
      case INFRA_TURN:
        if (msg.getActivePLayerId() != playerId) {
          return "another player did something";
        }
        return " ";

      case CLOUD_TILE:
        returnString.append(printCloudTiles(msg));
        returnString.append(printEntrance(msg));
        return returnString.toString();

      case MOVE_STUDENT_ENTRANCE:
        returnString.append(printEntrance(msg));

        returnString.append(printDiningRoom(msg));
        returnString.append(printProfessors(msg));

        returnString.append(printIslands(msg));
        return returnString.toString();

      case MOVE_MN:
        returnString.append(printIslands(msg));
        return returnString.toString();

      default:
        returnString.append(printIslands(msg));
        returnString.append(printCloudTiles(msg));
        returnString.append(printAvailableTowers(msg));
        returnString.append(printEntrance(msg));
        returnString.append(printDiningRoom(msg));
        returnString.append(printProfessors(msg));
        returnString.append(printPlayableAssistants(msg));
        returnString.append(printPurchasableCharacter(msg));
        return returnString.toString();
    }
  }

  /**
   * This method print all the teams available towers
   *
   * @param msg the message use to obtain printed values
   * @return the string constructed
   */
  private String printAvailableTowers(BeginTurnMessage msg) {
    //towers for each team
    StringBuilder returnString = new StringBuilder();
    returnString.append("The available towers for each team are:").append("\n");
    for (int teamId = 0; teamId < msg.getTowersNumber().length; teamId++) {
      returnString.append(msg.getTowersNumber()[teamId]).append(" belonging to the ").append(teamId)
          .append(" team").append("\n");
    }
    returnString.append("\n");
    return returnString.toString();
  }

  /**
   * This method print all the players dining rooms
   *
   * @param msg the message use to obtain printed values
   * @return the string constructed
   */
  private String printDiningRoom(BeginTurnMessage msg) {
    //dining rooms
    StringBuilder returnString = new StringBuilder();
    returnString.append("your and opponents dining room are as follows").append("\n");

    for (StudentsPlayerId diningRoomIdPlayer : msg.getStudentDiningRoom()) {
      if (diningRoomIdPlayer.getPlayerId() == this.playerId) {
        returnString.append("your diningRoom is:").append("\n");
      } else {
        returnString.append("an opponent diningRoom is").append("\n");
      }
      for (Color color : Color.values()) {
        returnString.append(diningRoomIdPlayer.getStudents()[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }
      returnString.append("\n");
    }
    return returnString.toString();
  }

  /**
   * This method print all the players entrances
   *
   * @param msg the message use to obtain printed values
   * @return the string constructed
   */
  private String printEntrance(BeginTurnMessage msg) {
    //entrances
    StringBuilder returnString = new StringBuilder();
    returnString.append("your and opponents entrance are as follows").append("\n");
    for (StudentsPlayerId entranceIdPlayer : msg.getStudentEntrance()) {
      if (entranceIdPlayer.getPlayerId() == this.playerId) {
        returnString.append("your entrance is:").append("\n");
      } else {
        returnString.append("an opponent entrance is").append("\n");
      }
      for (Color color : Color.values()) {
        returnString.append(entranceIdPlayer.getStudents()[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }
      returnString.append("\n").append("\n");
    }
    return returnString.toString();
  }

  /**
   * This method print the professors if you have them
   *
   * @param msg the message use to obtain printed values
   * @return the string constructed
   */
  private String printProfessors(BeginTurnMessage msg) {
    //professors
    StringBuilder returnString = new StringBuilder();
    returnString.append("You own the professor:").append("\n");
    int[] myProfessor = msg.getProfessors().get(this.playerId);
    for (Color color : Color.values()) {
      if (myProfessor[color.ordinal()] == 1) {
        returnString.append(myProfessor[color.ordinal()]).append(" ")
            .append(color).append(" professor;\n");
      }
    }
    returnString.append("\n");
    return returnString.toString();
  }

  /**
   * This method print all the possible cloud tile you can take
   *
   * @param msg the message use to obtain printed values
   * @return the string constructed
   */
  private String printCloudTiles(BeginTurnMessage msg) {

    //cloudTiles
    StringBuilder returnString = new StringBuilder();
    returnString.append("the cloud tiles are as follows").append("\n");
    for (int[] studentsCloud : msg.getStudentsCloudTiles()) {
      returnString.append("Cloud tile number ")
          .append(msg.getStudentsCloudTiles().indexOf(studentsCloud))
          .append(": \n");
      for (Color color : Color.values()) {
        returnString.append(studentsCloud[color.ordinal()]).append(" ")
            .append(color).append(" students;\n");
      }
      returnString.append("\n");
    }
    returnString.append("\n").append("\n");
    return returnString.toString();
  }

  /**
   * This method print all the islands
   *
   * @param msg the message use to obtain printed values
   * @return the string constructed
   */
  private String printIslands(BeginTurnMessage msg) {
    //islands and mother nature
    StringBuilder returnString = new StringBuilder();
    returnString.append("The island are as follows").append("\n");
    int countedIsland = 0;
    for (int islandIndex = 0; islandIndex < 12; islandIndex++) {
      if (msg.getIslandsIds()[countedIsland] == islandIndex) {
        returnString.append("Island number ").append(islandIndex)
            .append(": \n");

        int[] students = msg.getStudentsIsland().get(countedIsland);
        for (Color color : Color.values()) {
          returnString.append(students[color.ordinal()]).append(" ")
              .append(color).append(" students;\n");
        }

        if (msg.getTowersIsland().get(countedIsland) != 0) {
          returnString.append("this island contains ")
              .append(msg.getTowersIsland().get(countedIsland))
              .append("tower \n");
          returnString.append("this island belongs to the ")
              .append(msg.getTowersColor()[countedIsland])
              .append(" team\n");
        }

        if (msg.getMotherNaturePosition() == msg.getIslandsIds()[countedIsland]) {
          returnString.append("mother nature is here");
        } else {
          returnString.append("mother nature is NOT here");
        }
        returnString.append("\n").append("\n");
        countedIsland++;
      }

    }
    return returnString.toString();
  }

  /**
   * This method print all your playable assistants
   *
   * @param msg the message use to obtain printed values
   * @return the string constructed
   */
  private String printPlayableAssistants(BeginTurnMessage msg)
      throws IOException {
    //playable assistants
    StringBuilder returnString = new StringBuilder();
    returnString.append("these are the assistant tou can play :").append("\n");
    Gson gson = new Gson();

    String path =
        it.polimi.ingsw.network.server.FileReader.getPath("/json/assistants.json");

    JsonArray list = gson.fromJson(path, JsonArray.class);

    for (Integer idAssistant : msg.getPlayableAssistantId()) {

      JsonObject object = list.get(idAssistant).getAsJsonObject();
      int speed = object.get("SPEED").getAsInt();
      int moves = object.get("MOVES").getAsInt();

      returnString.append("To play this assistant use id ").append(idAssistant).append("\n");
      returnString.append("assistant speed is").append(" ").append(speed).append("\n")
          .append("max mother nature moves").append(" ").append(moves).append("\n")
          .append("\n");
    }
    return returnString.toString();
  }

  /**
   * This method print all the character you can purchase
   *
   * @param msg the message use to obtain printed values
   * @return the string constructed
   */
  private String printPurchasableCharacter(BeginTurnMessage msg) {
    //purchasable character
    StringBuilder returnString = new StringBuilder();
    if (msg.getPurchasableCharacterId() != null) {
      returnString.append("those are the assistant you can purchase:").append("\n").append("\n");

      for (int id : msg.getPurchasableCharacterId()) {
        for (Effects effect : Effects.values()) {

          if (id == effect.ordinal()) {
            returnString.append(effect.getStringEffectCard()).append("\n");
            returnString.append("The cost of this character is ")
                .append(msg.getCharactersCosts()[msg.getPurchasableCharacterId().indexOf(id)])
                .append("\n");

            if (effect.getNOfStudents() != 0) {
              returnString.append("it has the following students on it").append("\n");

              for (Color color : Color.values()) {
                returnString.append(
                        msg.getCharactersStudents()
                            .get(msg.getPurchasableCharacterId().indexOf(id))[color
                            .ordinal()]).append(" ")
                    .append(color).append(" students;\n");
              }
            }
          }
        }
        returnString.append("to purchase use id ")
            .append(msg.getPurchasableCharacterId().indexOf(id)).append("\n").append("\n");
      }

      //coins
      returnString.append("You have ").append(msg.getPlayerCoins()[playerId]).append(" coins")
          .append("\n").append("\n");
      return returnString.toString();
    }
    return "";
  }

}


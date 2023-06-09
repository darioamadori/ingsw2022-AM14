package it.polimi.ingsw.model.player;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.Places;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import org.jetbrains.annotations.NotNull;


/**
 * The Player is the entity used to model the behaviour and action a person might do in the game.
 */
public class Player implements Comparable<Player> {

  private final int playerId;
  private final String playerNickname;
  private final Vector<Assistant> playableAssistants;
  private PlayerBoard playerBoard;
  private Assistant assistant;
  private int coins;

  /**
   * Constructor method for the Player class
   *
   * @param playerId       Unique identifier for each player
   * @param playerNickname Unique name chosen by each player
   * @throws FileNotFoundException Throws FileNotFoundException if file containing all the
   *                               assistants is not found
   */

  public Player(int playerId, String playerNickname) throws IOException {
    this.coins = Constants.getInitialCoinNumber();
    this.assistant = null;
    this.playerId = playerId;
    this.playerNickname = playerNickname;
    this.playableAssistants = new Vector<>();
    String path =
        it.polimi.ingsw.network.server.FileReader.getPath("/json/assistants.json");
    initializeAssistants(path);
  }

  /**
   * Initializer for the player's assistants
   *
   * @param path File containing the assistants' information
   */
  private void initializeAssistants(String path) {

    Gson gson = new Gson();

    JsonArray assistants = gson.fromJson(path, JsonArray.class);

    //JsonArray assistants = JsonParser.parseReader(new FileReader(jsonFile)).getAsJsonArray();

    for (Object assistant : assistants) {
      JsonObject object = (JsonObject) assistant;
      int moves = object.get("MOVES").getAsInt();
      int speed = object.get("SPEED").getAsInt();
      int assistantId = object.get("ASSISTANT_ID").getAsInt();
      playableAssistants.add(new Assistant(moves, speed, assistantId));
    }
  }

  /**
   * This method sets up the player board of the play using the random students provided as
   * parameter
   *
   * @param students The students used to initialize the dining room's entrance
   */
  public void initializePlayerBoard(int[] students) {
    this.playerBoard = new PlayerBoard(students);
  }

  /**
   * This method allows a player to play an Assistant, and it removes that assistant form the
   * playable ones
   *
   * @param assistantId used to identify which assistant has been played
   */
  public void playAssistant(int assistantId) {

    Assistant returnAssistant;
    returnAssistant = playableAssistants.stream()
        .filter(assistant -> assistant.getAssistantId() == assistantId).findFirst().get();

    this.assistant = returnAssistant;
    this.playableAssistants.remove(returnAssistant);
  }

  /**
   * This method is used to move a student to the entrance or to the dining room
   *
   * @param place used to identify where the student will be put in
   * @param color used to identify the color of the student
   */
  public void moveToPlayerBoard(Places place, int color) {
    if (place == Places.ENTRANCE) {
      playerBoard.moveToEntrance(color);
    } else if (place == Places.DINING_ROOM) {
      playerBoard.moveToDiningRoom(color);
      if (this.playerBoard.getDiningRoom().getStudents()[color]
          % Constants.getNumberStudentToCoin() == 0) {
        addCoin();
      }
    }
  }

  /**
   * This method allows a player (using the character) to remove students from the dining room and
   * the entrance
   *
   * @param place where the student is taken
   * @param color which student is removed
   */
  public void removeFromPlayerBoard(Places place, int color) {
    if (place == Places.ENTRANCE) {
      playerBoard.removeFromEntrance(color);
    } else if (place == Places.DINING_ROOM) {
      playerBoard.removeFromDiningRoom(color);
    }
  }

  /**
   * This method adds a coin to the player "wallet"
   */
  public void addCoin() {
    this.coins++;
  }

  /**
   * This method removes coins to the player's "wallet"
   */
  public void removeCoins(int nOfCoins) {
    this.coins -= nOfCoins;
  }

  /**
   * This method adds additional moves to the assistant as a result of a Character Card effect
   *
   * @param nOfMoves Number of moves to be added
   */
  public void additionalMovesAssistant(int nOfMoves) {
    assistant.setAdditionalMoves(nOfMoves);
  }

  /**
   * This method removes the additional moves given to the assistant as a result of a Character Card
   * effect
   */
  public void removeAdditionalMovesAssistant() {
    assistant.setAdditionalMoves(0);
  }

  public int getCoins() {
    return coins;
  }

  public int getPlayerId() {
    return playerId;
  }

  public String getPlayerNickname() {
    return playerNickname;
  }

  public Assistant getAssistant() {
    return assistant;
  }

  public PlayerBoard getPlayerBoard() {
    return playerBoard;
  }

  public Vector<Assistant> getPlayableAssistant() {
    return playableAssistants;
  }

  public void playedAssistantToNull() {
    this.assistant = null;
  }

  @Override
  public int compareTo(@NotNull Player o) {
    return o.getAssistant().getSpeed() - this.getAssistant().getSpeed();
  }
}

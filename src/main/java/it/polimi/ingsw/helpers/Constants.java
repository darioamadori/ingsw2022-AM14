package it.polimi.ingsw.helpers;

/**
 * Constants class used to have all the game constants in one place and make the code better
 */
public class Constants {

  private static final int N_COLORS = 5;
  private static final int MAX_TOWERS = 8;
  private static final int TOTAL_STUDENTS = 270;
  private static final int MAX_STUDENTS_DINING_ROOM = 10;
  private static final int STUDENTS_FOR_COLOR = TOTAL_STUDENTS / N_COLORS;
  private static final int INITIAL_NUM_ISLANDS = 12;
  private static final int MAX_NUMBER_OF_PLAYER_EFFECTS = 5;
  private static final int MAX_NUMBER_OF_MAIN_BOARD_EFFECTS = 6;
  private static final int MAX_PURCHASABLE_CHARACTER=3;
  private static final int TWO_PLAYER_MODE=1;
  private static final int  THREE_PLAYER_MODE=2;
  private static final int FOUR_PLAYER_MODE=3;

  private Constants() {
  }

  public static int getNColors() {
    return N_COLORS;
  }

  public static int getMaxTowers() {
    return MAX_TOWERS;
  }

  public static int getStudentsForColor() {
    return STUDENTS_FOR_COLOR;
  }

  public static int getMaxStudentsDiningRoom() {
    return MAX_STUDENTS_DINING_ROOM;
  }

  public static int getInitialNumIslands() {
    return INITIAL_NUM_ISLANDS;
  }

  public static int getMaxNumberOfPlayerEffects() {
    return MAX_NUMBER_OF_PLAYER_EFFECTS;
  }

  public static int getMaxNumberOfMainBoardEffects() {
    return MAX_NUMBER_OF_MAIN_BOARD_EFFECTS;
  }

  public static int getMaxPurchasableCharacter() {
    return MAX_PURCHASABLE_CHARACTER;
  }

  public static int getTwoPlayerMode() {
    return TWO_PLAYER_MODE;
  }

  public static int getThreePlayerMode() {
    return THREE_PLAYER_MODE;
  }

  public static int getFourPlayerMode() {
    return FOUR_PLAYER_MODE;
  }




}

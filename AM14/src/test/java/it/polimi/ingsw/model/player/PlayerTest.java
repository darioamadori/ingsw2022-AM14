package it.polimi.ingsw.model.player;

import it.polimi.ingsw.exceptions.InvalidMoveException;
import java.util.Arrays;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Test for Player class
 */

public class PlayerTest extends TestCase {

  Player testPlayer;
  DiningRoom testDiningRoom;
  Entrance testEntrance;

  /**
   * test for the playAssistant method to ensure that the played assistant is correct and that is no
   * longer available into the playableAssistants
   */

  @Test
  public void testPlayAssistent() {
    testPlayer = new Player(1, "ale");
    Assistent assistent1 = new Assistent(1, 2, 2);

    testPlayer.playAssistent(2);
    assertEquals(testPlayer.getAssistent().getAssistantId(), assistent1.getAssistantId());
    assertFalse(testPlayer.getPlayableAssistent().contains(testPlayer.getAssistent()));
  }

  /**
   * Test for the moveTo playerBoard method in case of a student put into the entrance
   */
  @Test
  public void testMoveToPlayerBoardEntranceCase() {
    testPlayer = new Player(2, "ale");
    testEntrance = new Entrance();
    testPlayer.moveToPlayerBoard(0, 3);
    testEntrance.addStudent(3);
    assertEquals(Arrays.toString(testPlayer.getPlayerBoard().getEntrance().getStudents()),
        Arrays.toString(testEntrance.getStudents()));
  }

  /**
   * Test for the moveTo playerBoard method in case of a student put into the diningRoom
   *
   * @throws InvalidMoveException if the dining room is full
   */
  @Test
  public void testMoveToPlayerBoardDiningCase() throws InvalidMoveException {
    testPlayer = new Player(2, "ale");
    testDiningRoom = new DiningRoom();
    testPlayer.moveToPlayerBoard(1, 2);
    testDiningRoom.addStudent(2);
    assertEquals(Arrays.toString(testPlayer.getPlayerBoard().getDiningRoom().getStudents()),
        Arrays.toString(testDiningRoom.getStudents()));
  }

  /**
   * Test for the addCoin method
   */
  @Test
  public void testaddCoin() {
    testPlayer = new Player(1, "marco");
    testPlayer.addCoin();
    assertEquals(testPlayer.getCoins(), 1);
  }
}

package it.polimi.ingsw.controller;

import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.helpers.Places;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.MoveMessage;
import it.polimi.ingsw.messages.MoveMessageResponse;
import it.polimi.ingsw.model.Game;
import java.util.Arrays;
import java.util.Optional;

/**
 * Move controller class elaborates move messages and controls if they are correct
 */
public class MoveController {

  /**
   * Constructor method for MoveController class
   */
  public MoveController() {
  }

  /**
   * This method elaborates the message and return the response
   *
   * @param msg         The message sent
   * @param currentGame The started match
   */
  public Message elaborateMessage(MoveMessage msg, Game currentGame) {
    //can create a method for each control

    switch (msg.getMessageSecondary()) {
      case MOTHER_NATURE:
        //control if no entry tile
        int motherNatureMoves =
            msg.getIslandNumber() - currentGame.getMainBoard().getMotherNature().getPosition();
        currentGame.moveNature(
            motherNatureMoves);

        MoveMessageResponse responseNature = new MoveMessageResponse(
            MessageSecondary.MOTHER_NATURE);
        responseNature.setIslandNumber(currentGame.getMainBoard().getMotherNature().getPosition());
        return responseNature;

      case CLOUD_TILE:
        //control if the number is valid

        if (checkCloudTileValid(msg, currentGame) && !currentGame.getMainBoard().getIslands()
            .get(msg.getIslandNumber()).isNoEntry()) {

          MoveMessageResponse responseCloud = new MoveMessageResponse(MessageSecondary.CLOUD_TILE);

          responseCloud.setStudentsCloud(
              currentGame.getCloudTiles().get(msg.getCloudTileNumber()).getStudents());
          currentGame.takeCloud(currentGame.getCurrentPlayer(), msg.getCloudTileNumber());
          responseCloud.setIslandNumber(msg.getCloudTileNumber());
          return responseCloud;
        }
        break;
      case ENTRANCE:
        //control if the student is present
        if (checkStudentIfPresent(msg, currentGame)) {
          MoveMessageResponse responseEntrance = new MoveMessageResponse(MessageSecondary.ENTRANCE);
          responseEntrance
              .setStudentsEntrance(currentGame.getCurrentPlayer().getPlayerBoard().getEntrance()
                  .getStudents());

          if (msg.getPlace() == 0) {
            responseEntrance.setPlace(0);

            currentGame
                .moveStudent(currentGame.getCurrentPlayer(), Places.ENTRANCE, Places.DINING_ROOM,
                    msg.getStudentColor(),
                    Optional.empty());

            responseEntrance.setStudentsDiningRoom(
                currentGame.getCurrentPlayer().getPlayerBoard().getDiningRoom()
                    .getStudents());
          } else {
            responseEntrance.setPlace(1);

            currentGame
                .moveStudent(currentGame.getCurrentPlayer(), Places.ENTRANCE, Places.ISLAND,
                    msg.getStudentColor(),
                    Optional.of(msg.getIslandNumber()));

            responseEntrance.setStudentsIsland(
                currentGame.getMainBoard().getIslands().get(msg.getIslandNumber()).getStudents());
          }
          return responseEntrance;
        }
      default:
        break;
    }

    return null;
  }

  /**
   * This method controls if the cloud tile number is valid
   *
   * @param msg         The message sent
   * @param currentGame The started match
   * @return true if the cloudNumber is correct and it is not already taken
   */
  private boolean checkCloudTileValid(MoveMessage msg, Game currentGame) {
    return currentGame.getCloudTiles().size() >= msg.getCloudTileNumber()
        && Arrays.stream(currentGame.getCloudTiles().get(msg.getCloudTileNumber()).getStudents())
        .sum() != 0;
  }


  /**
   * This method controls if the student is present at the entrance
   *
   * @param msg         The message sent
   * @param currentGame The started match
   * @return true if the selected student is at the entrance
   */
  private boolean checkStudentIfPresent(MoveMessage msg, Game currentGame) {
    return currentGame.getCurrentPlayer().getPlayerBoard().getEntrance().getStudents()[msg
        .getStudentColor()] >= 1;
  }
}

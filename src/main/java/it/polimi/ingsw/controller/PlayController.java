package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.PlayMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Team;
import it.polimi.ingsw.model.characters.CharacterCard;
import it.polimi.ingsw.model.characters.CharacterStruct;
import it.polimi.ingsw.model.player.Player;
import java.util.Vector;

public class PlayController {

  /**
   *
   */
  public boolean elaborateMessage(PlayMessage msg, Game game) {
    switch (msg.getMessageSecondary()) {
      case ASSISTANT:
        return playAssistant(msg, game);
      case CHARACTER:
        return playCharacter(msg, game);
      default:
        return false;
    }
  }

  /**
   * @param msg
   * @param game
   * @return
   */
  private boolean playAssistant(PlayMessage msg, Game game) {
    if (canPlayAssistant(msg.getPlayerId(), msg.getAssistantId(), game.getTeams())) {
      for (Team team : game.getTeams()) {
        for (Player player : team.getPlayers()) {
          if (player.getPlayerId() == msg.getPlayerId()) {
            game.playAssistant(player, msg.getAssistantId());
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * @param msg
   * @param game
   * @return
   */
  private boolean playCharacter(PlayMessage msg, Game game) {

    CharacterStruct characterParameters = new CharacterStruct();
    CharacterCard characterCard = game.getPurchasableCharacter().elementAt(msg.getCharacterId());

    characterParameters.currentGame = game;
    characterParameters.color = msg.getColor();
    characterParameters.teams = game.getTeams();
    characterParameters.numIsland = msg.getNumIsland();
    characterParameters.mainBoard = game.getMainBoard();
    characterParameters.studentsBag = game.getStudentsBag();
    characterParameters.motherNatureMoves = msg.getMotherNatureMoves();

    characterParameters.studentsCard = msg.getStudentsCard();
    characterParameters.studentsEntrance = msg.getStudentsEntrance();
    characterParameters.studentsDiningRoom = msg.getStudentsDiningRoom();

    if (canPlayCharacter(msg.getPlayerId(), characterCard.getCost(), game.getTeams())) {
      for (Team team : game.getTeams()) {
        for (Player player : team.getPlayers()) {
          if (player.getPlayerId() == msg.getPlayerId()) {

            characterParameters.currentPlayer = player;
            game.playCharacter(player, msg.getAssistantId());
            characterCard.applyEffect(characterParameters);
            return true;

          }
        }
      }
    }
    return false;
  }

  /**
   * @param playerId
   * @param assistantId
   * @param teams
   * @return
   */
  private boolean canPlayAssistant(int playerId, int assistantId, Vector<Team> teams) {
    for (Team team : teams) {
      for (Player player : team.getPlayers()) {
        if (player.getPlayerId() == playerId) {
          return player.getPlayableAssistant().stream()
              .anyMatch(assistant -> assistant.getAssistantId() == assistantId);
        }
      }
    }
    return false;
  }

  /**
   * @param playerId
   * @param characterCost
   * @param teams
   * @return
   */
  private boolean canPlayCharacter(int playerId, int characterCost, Vector<Team> teams) {
    for (Team team : teams) {
      for (Player player : team.getPlayers()) {
        if (player.getPlayerId() == playerId) {
          return player.getCoins() >= characterCost;
        }
      }
    }
    return false;
  }
}
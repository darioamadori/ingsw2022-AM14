package it.polimi.ingsw.guicontrollers;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class IslandController implements Initializable {

  public StackPane island;
  public Circle teamTower;
  public Label redStudents;
  public Label blueStudents;
  public Label pinkStudents;
  public Label greenStudents;
  public Label yellowStudents;
  public Label numberOfTowers;
  public StackPane motherNature;
  public ImageView noEntry;

  public Circle highlight;

  public String islandOnDropId;

  boolean isCharacterActive;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    teamTower.setVisible(false);
    highlight.setVisible(false);
    motherNature.setVisible(false);
    numberOfTowers.setVisible(false);
  }

  @FXML
  protected void onMouseEnteredIsland(MouseEvent event) {
    highlight.setVisible(true);
  }

  @FXML
  protected void onMouseExitedIsland(MouseEvent event) {
    highlight.setVisible(false);
  }

  @FXML
  protected void onStudentDropOnIsland(MouseEvent event) {
    StackPane island = ((StackPane) event.getSource());
  }

  public void setRedStudents(int nStudents) {
    redStudents.setText(Integer.toString(nStudents));
  }

  public void setBlueStudents(int nStudents) {
    blueStudents.setText(Integer.toString(nStudents));
  }

  public void setPinkStudents(int nStudents) {
    pinkStudents.setText(Integer.toString(nStudents));
  }

  public void setGreenStudents(int nStudents) {
    greenStudents.setText(Integer.toString(nStudents));
  }

  public void setYellowStudents(int nStudents) {
    yellowStudents.setText(Integer.toString(nStudents));
  }

  public void setTeamTower(int towerColor) {

    teamTower.setVisible(true);

    if (towerColor == 0) {
      teamTower.setFill(WHITE);
      numberOfTowers.setTextFill(BLACK);
    } else if (towerColor == 1) {
      teamTower.setFill(BLACK);
      numberOfTowers.setTextFill(WHITE);
    } else if (towerColor == 2) {
      teamTower.setFill(Color.GRAY);
      numberOfTowers.setTextFill(BLACK);
    } else {
      teamTower.setVisible(false);
    }
  }

  public void setNumberOfTowers(int nTowers) {
    numberOfTowers.setText(Integer.toString(nTowers));
    if (nTowers > 0) {
      teamTower.setVisible(true);
      numberOfTowers.setVisible(true);
    } else {
      teamTower.setVisible(false);
      numberOfTowers.setVisible(false);
    }
  }

  public void setMotherNature(boolean isMotherNaturePresent) {
    motherNature.setVisible(isMotherNaturePresent);
  }

  public void setNoEntry(boolean isNoEntry) {
    noEntry.setVisible(isNoEntry);
  }

}

package it.polimi.ingsw.network.client;


import com.google.gson.Gson;
import it.polimi.ingsw.helpers.Constants;
import it.polimi.ingsw.helpers.MessageSecondary;
import it.polimi.ingsw.messages.ClientResponse;
import it.polimi.ingsw.messages.Message;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class for ping the clients and detects the disconnections
 */
public class Pinger implements Runnable {

  private final PrintWriter out;

  /**
   * Constructor method for Pinger class
   *
   * @param socketOut The socket for the communication
   */
  public Pinger(Socket socketOut) throws IOException {
    out = new PrintWriter(socketOut.getOutputStream(), true);
  }

  /**
   * This method send a ping message after a PING_TIME period of time
   */
  @Override
  public void run() {

    Message pingMessage = new ClientResponse(MessageSecondary.PING);
    int pingTime = Constants.getPingTime();
    while (true) {
      try {

        Thread.sleep(pingTime);
        out.println(toJson(pingMessage));

      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }

    }
  }

  /**
   * toJon method: is used to parse a String to json format
   *
   * @param msg Object to parse
   * @return String in json format
   */
  private String toJson(Message msg) {
    Gson gson = new Gson();
    return gson.toJson(msg);
  }
}
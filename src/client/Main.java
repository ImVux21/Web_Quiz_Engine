package client;

import action.Action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import static action.Action.EXIT;

public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int port = 23456;

    public static void main(String[] args) {

        try (
                Socket socket = new Socket(InetAddress.getByName(ADDRESS), port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        )  {

            Action action = askUserAction();

            // create the client request
            String clientRequest = null;
            switch (action) {
                case GET:
                    clientRequest = createGetRequest();
                    break;
                case PUT:
                    clientRequest = createCreateRequest();
                    break;
                case DELETE:
                    clientRequest = createDeleteRequest();
                    break;
                case EXIT:
                    clientRequest = createExitRequest();
                    break;

            }

            // send the request string
            sendClientRequest(clientRequest, output);

            // for the request GET, CREATE, DELETE, the server will send the response containing status code,
            if (action != EXIT) {
                // receive the server response

                String serverResponse = receiveServerResponse(input);

                // parse and display the response based on the user action
                parseAndDisplayServerResponse(serverResponse, action);
            }

        } catch (Exception e) {
            System.out.println("Error in client");
            e.printStackTrace();
        }
    }

    private static Action askUserAction() {

    }

    private static String createGetRequest() {

    }

    private static String createCreateRequest() {

    }

    private static String createDeleteRequest() {

    }

    private static String createExitRequest() {
        return null;
    }

    private static void sendClientRequest(String clientRequest, DataOutputStream output) {

    }

    private static String receiveServerResponse(DataInputStream input) {


    }

    private static void parseAndDisplayServerResponse(String serverResponse, Action action) {

    }
}

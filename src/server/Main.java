package server;

import action.Action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {

    private static final String ADDRESS = "127.0.0.1";
    private static final int port = 23456;

    private static final String DATA_STORED_DIR = "./src/server/data/";

    public static void main(String[] args) {

        try (
                ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(ADDRESS));
                Socket socket = server.accept();
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {

            String clientRequest = input.readUTF();
            Action action = parseActionFromRequest(clientRequest);

            // create server response based on the client request
            String serverResponse = null;
            switch (action) {
                case GET:
                    serverResponse = handleGetRequest(clientRequest);
                    break;
                case PUT:
                    serverResponse = handlePutRequest(clientRequest);
                case DELETE:
                    serverResponse = handleDeleteRequest(clientRequest);
                    break;
                case EXIT:
                    // shut down the server
                    System.exit(0);
            }

            // send the server response back to client
            sendServerResponse(serverResponse, output);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Action parseActionFromRequest(String clientRequest) {

    }

    private static String handleGetRequest(String clientRequest) {

    }

    private static String handlePutRequest(String clientRequest) {

    }

    private static String handleDeleteRequest(String clientRequest) {
        return null;
    }

    private static void sendServerResponse(String serverResponse, DataOutputStream output) {

    }
}

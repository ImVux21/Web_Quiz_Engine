package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    enum Action {
        GET, CREATE, DELETE, EXIT
    }
    private static final String ADDRESS = "127.0.0.1";
    private static final int port = 23456;

    public static void main(String[] args) {

        try (
            Socket socket = new Socket(InetAddress.getByName(ADDRESS), port);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

        )  {

            Action action = askUserAction();

            switch (action) {
                case GET:
                    sendGetReqest(output);
                    break;

            }

        } catch (Exception e) {
            System.out.println("Error in client");
            e.printStackTrace();
        }
    }
}

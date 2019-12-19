package pl.hit.servlets.scissors;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ScissorsServer implements Closeable {

    public static void main(String[] args) {

        System.out.println("Waiting for connection...");

        try {
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("localhost",9091));

            Socket clientSocket = serverSocket.accept();
            InetAddress clientAddress = clientSocket.getInetAddress();
            System.out.println("Connected with "+clientAddress);

            GameHandler gameHandler = new GameHandler(clientSocket);
            gameHandler.run();



        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void close() throws IOException {

    }
}

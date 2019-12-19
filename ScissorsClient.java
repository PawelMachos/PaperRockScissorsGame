package pl.hit.servlets.scissors;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ScissorsClient {

    public static void main(String[] args) {

        System.out.println("Connecting...");

        try(Socket clientSocket = new Socket()) {

            clientSocket.connect(new InetSocketAddress("localhost",9091));
            System.out.println("Connected!");

            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter outputWrite = new PrintWriter(new OutputStreamWriter(outputStream,"UTF-8"));

            Scanner scanner = new Scanner(System.in);

                System.out.println(bufferedReader.readLine());
                System.out.println(bufferedReader.readLine());
                System.out.println(bufferedReader.readLine());
                System.out.println(bufferedReader.readLine());
                System.out.println(bufferedReader.readLine());
                System.out.println(bufferedReader.readLine());
                System.out.println(bufferedReader.readLine());


            do {
                outputWrite.println(scanner.nextLine());
                outputWrite.flush();
                String serverLine = bufferedReader.readLine();
                System.out.println(serverLine);

                if(scanner.nextLine() == "quit") {


                    System.out.println("Running finished.");
                    clientSocket.shutdownInput();
                    clientSocket.shutdownOutput();
                    clientSocket.close();
                    System.out.println(scanner.nextLine());
                }

            } while (clientSocket.isConnected());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

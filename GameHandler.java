package pl.hit.servlets.scissors;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameHandler implements Runnable {
    String paper = "PAPER";
    String rock = "ROCK";
    String scissors = "SCISSORS";

    Socket clientSocket;
    public GameHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        System.out.println("Running started...");
        try {
            InputStream clientInput = clientSocket.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(clientInput,"UTF-8"));

            OutputStream outputToClient =  clientSocket.getOutputStream();
            PrintWriter streamWriter = new PrintWriter(new OutputStreamWriter(outputToClient, "UTF-8"));

            greetings(streamWriter);

            while (clientSocket.isConnected()) {
                String line = streamReader.readLine();
                System.out.println(line);
                randomChoice(streamWriter, line);


            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void greetings(PrintWriter streamWriter) {
//        streamWriter.println(" ");
//        streamWriter.println("Hello!\n" +
//                "Welcome in Paper Rock Scissors game!\n" +
//                "Please choose:\n" +
//                ">PAPER<, >ROCK< or >SCISSORS<\n" +
//                "and let's see who will win!");
        streamWriter.println(" ");
        streamWriter.println("Hello!");
        streamWriter.println(" ");
        streamWriter.println("Welcome in Paper Rock Scissors game!");
        streamWriter.println(" ");
        streamWriter.println("Please choose:");
        streamWriter.println(">PAPER<, >ROCK<, >SCISSORS< or >QUIT< if you want to end the game.");
        streamWriter.flush();
    }

    private void randomChoice(PrintWriter streamWriter, String line) throws IOException {

        Random random = new Random();
        Integer randomInt = random.nextInt(3);
        System.out.println(randomInt);

        // 0 = PAPER
        // 1 = ROCK
        // 2 = SCISSORS
        if ((line.equalsIgnoreCase(paper) && randomInt == 0) || (line.equalsIgnoreCase(rock) && randomInt == 1)
                || (line.equalsIgnoreCase(scissors) && randomInt == 2)) {

            streamWriter.println(" --> DRAW <-- Next round!");
            streamWriter.flush();
        } else if ((line.equalsIgnoreCase(paper) && randomInt == 1) || (line.equalsIgnoreCase(rock) && randomInt == 2)
                || (line.equalsIgnoreCase(scissors) && randomInt == 0)) {

            streamWriter.println("YOU WIN - play again!");
            streamWriter.flush();
        } else if ((line.equalsIgnoreCase(paper) && randomInt == 2) || (line.equalsIgnoreCase(rock) && randomInt == 0)
                || (line.equalsIgnoreCase(scissors) && randomInt == 1)) {

            streamWriter.println("YOU LOSE - try again!");
            streamWriter.flush();
        } else if(line.equalsIgnoreCase("quit")){
            streamWriter.println("GAME ENDED");
            streamWriter.flush();

            System.out.println("Game ended.");
            clientSocket.shutdownInput();
            clientSocket.shutdownOutput();
            System.out.println("Running finished.");
            clientSocket.close();
        }
        else if (line != "rock" && line != "ROCK" && line != "paper" && line != "PAPER"
                && line != "scissors" && line != "SCISSORS") {
            streamWriter.println("Please type >PAPER<, >ROCK< or >SCISSORS< or >QUIT< if you want to end the game.");
            streamWriter.flush();
        }


    }

}


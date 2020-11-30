import java.util.Scanner;

import API.Client;
import Client.ClientManager;
import Server.ServerManager;

public class Main {

    private final static String OPTIONS_MSG = "1. Start a new client.\n2. Exit this application (WARNING: kills all clients!).\n";

    public static void main(String[] args) {

        final ServerManager srvMgr = new ServerManager();
        final Runnable requestReplyRunnable = srvMgr::respondToRequests;
        final Runnable findGamesRunnable = srvMgr::findGames;

        final Thread replyThread = new Thread(requestReplyRunnable);
        final Thread findGamesThread = new Thread(findGamesRunnable);

        replyThread.start();
        findGamesThread.start();
        
        final Scanner stdInScanner = new Scanner(System.in);

        System.out.println("Welcome to TicTacToe! Select from one of the following options:");
        System.out.println(OPTIONS_MSG);

        while (true) {
            int optionChosen = stdInScanner.nextInt();
            if (optionChosen == 1) {
                final ClientManager clientMgr = new ClientManager(srvMgr); //spawn a new client
                System.out.println("\nStarted another client. Please choose again from the following:");
                System.out.println(OPTIONS_MSG);

            } else if (optionChosen == 2) {
                System.out.println("\nExiting application. Goodbye!");
                srvMgr.cancel();
                stdInScanner.close();
                System.exit(-1);
            
            } else {
                System.out.println("\nINVALID INPUT. Please choose from one of the following options:\n" + OPTIONS_MSG);
            }
        }
    }

}

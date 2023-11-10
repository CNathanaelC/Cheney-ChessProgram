package ui;

import ui.ChessClient;

import java.util.Scanner;
import static ui.EscapeSequences.*;

public class Repl {
    ChessClient chessClient;
    public Repl() {
        chessClient = new ChessClient();
    }
    public void run() {
        System.out.println(SET_TEXT_COLOR_BLUE + "Welcome to the Walmart Version of Chess.com");
        System.out.println(SET_TEXT_COLOR_RED + chessClient.help());
        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            System.out.print(SET_TEXT_COLOR_MAGENTA+"["+ chessClient.sessionLogin + "] >>> " + SET_TEXT_COLOR_RED);
            String line = scanner.nextLine();

            try {
                result = chessClient.execute(line);
                System.out.print(result);
            } catch (Throwable e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println();
    }
}

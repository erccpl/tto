package agh.po.tto;

import agh.po.tto.cli.CommandExecutor;
import org.apache.commons.cli.*;

import java.util.Scanner;

public class Main {

    public static void displayWelcomeMessage() {
        String welcomeMessage =
                "Hello and welcome to the Act Parser \n" +
                "Use -h to display the how-to message. \n\n";
        System.out.println(welcomeMessage);

        Options options = CommandExecutor.buildOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("LegalActParser", options);
    }


    public static void main(String[] args) {
        displayWelcomeMessage();
        System.out.println();

        CommandExecutor commandExecutor = new CommandExecutor();

        //Enter loop with Scanner
        while (true) {
            System.out.print(">>");
            Scanner scan = new Scanner(System.in);
            String s = scan.nextLine();
            commandExecutor.execCommand(s);
            System.out.println();
        }
    }
}

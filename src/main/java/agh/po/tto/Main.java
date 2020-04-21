package agh.po.tto;

import agh.po.tto.cli.CommandExecutor;
import org.apache.commons.cli.*;

import java.util.Scanner;


//TODO: may consider adding some more sophisticated formatting
//TODO: Titles should be separate nodes
//TODO: initially arguments must be in same format, i.e. roman or arabic

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
        String temp;
        displayWelcomeMessage();
        System.out.println();

        //Allow for using relative paths
        //String s1 = "-f /Users/eric/dev/java/text_to_object/src/main/resources/konstytucja.txt -a 4";
        //String s2 = "-f /Users/eric/dev/java/text_to_object/src/main/resources/uokik.txt  -t";

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

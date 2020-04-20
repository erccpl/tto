package agh.po.tto;

import agh.po.tto.cli.CommandExecutor;
import org.apache.commons.cli.*;


//TODO: may consider adding some more sophisticated formatting
//TODO: Titles should be separate nodes
//TODO: initially arguments must be in same format, i.e. roman or arabic

public class Main {
    private static void displayWelcomeMessage() {
        String welcomeMessage =
                "Hello and welcome to the Act Parser \n" +
                "Use -h to display the how-to message. \n\n";
        System.out.println(welcomeMessage);

        Options options = CommandExecutor.buildOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("LegalActParser", options);
    }


    public static void main(String[] args) throws ParseException {
        displayWelcomeMessage();
        System.out.println();

        //Allow for using relative paths
        String s = "-f /Users/eric/dev/java/text_to_object/src/main/resources/konstytucja.txt -a 4 -p 1 -a 5 ";
        String s2 = "-f /Users/eric/dev/java/text_to_object/src/main/resources/uokik.txt  -t";

        CommandExecutor cliHandler = new CommandExecutor();
        cliHandler.execCommand(s);




        //Enter loop with Scanner
//        while (true) {
//            System.out.print(">>");
//            Scanner scan = new Scanner(System.in);
//            String s1 = scan.next();
//
//
//
//
//        }


    }

}

package agh.po.tto;



import org.apache.commons.cli.*;

public class CLIHandler {

    public static Options buildOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "displays this usage message")
                .addOption("toc", true, "displays the table of contents");
        return options;
    }

    public static void doCommand(String s) throws ParseException {
            CommandLineParser parser = new DefaultParser();
            Options options = CLIHandler.buildOptions();

            CommandLine cmd =  parser.parse(options, new String[]{s});      //TODO: does this look ok
            //get the command



            //call appropriate function
    }


}

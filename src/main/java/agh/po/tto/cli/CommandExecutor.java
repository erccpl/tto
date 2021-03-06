package agh.po.tto.cli;

import agh.po.tto.Main;
import agh.po.tto.doc.Document;
import agh.po.tto.path.DocPath;
import agh.po.tto.preprocess.PreProcessor;
import agh.po.tto.printer.Printer;
import agh.po.tto.search.DocParser;

import org.apache.commons.cli.*;
import java.io.FileNotFoundException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;


public class CommandExecutor {

    private CommandLineParser parser;
    private Options options;

    private String filePath;
    private Document document;
    private DocParser docParser;

    public CommandExecutor() {
        this.parser = new DefaultParser();
        this.options = CommandExecutor.buildOptions();
        this.filePath = "";
    }

    public static Options buildOptions() {
        Options options = new Options();
        options.addOption("h",  "help", false, "Displays this usage message")
                .addRequiredOption("f", "file", true, "Path to the file containing the legal document")
                .addOption("t","toc", false, "Displays the table of contents")
                .addOption("s", "section", true, "Specifies a section")
                .addOption("c", "chapter", true, "Specifies a chapter")
                .addOption("a", "article", true, "Specifies an article")
                .addOption("p", "paragraph", true, "Specifies a paragraph")
                .addOption("pp", "paragraph-point", true, "Specifies a paragraph point")
                .addOption("l", "letter", true, "Specifies a letter")
                .addOption("q", "quit", false, "Quits the program");
        return options;
    }

    public void execCommand(String s) {
        if(s.equals("-q") || s.equals("--quit")) {
            System.exit(0);
        }

        String[] args = s.split(" ");

        try {
            CommandLine cmd = parser.parse(options, args);

            if(cmd.hasOption("h")) {
                Main.displayWelcomeMessage();
                return;
            }

            String filePath = cmd.getOptionValue("f");
            String absolutePath = FileSystems.getDefault().getPath(filePath).normalize().toAbsolutePath().toString();
            if(!CommandExecutor.pathExists(filePath)) {
                throw new FileNotFoundException();
            }
            if(!this.filePath.equals(absolutePath)) {
                PreProcessor preProcessor = new PreProcessor(filePath);
                preProcessor.preprocess();
                this.document = new Document(preProcessor.getPreProcessedInput());
                document.buildDocument();
                this.docParser = new DocParser(document.getRootNode());
            }

            if(filePath.isEmpty()) {
                this.filePath = absolutePath;
            }

            if(cmd.hasOption("t")) {
                this.docParser.createTOC();
                Printer.printTOC(docParser.getToc());
                return;
            }

            DocPath[] paths = DocPath.parseInputPaths(cmd);
            if(paths.length == 0) {
                throw new InvalidParameterException();
            }
            else if (paths[1].getPath().length == 0) {
                this.docParser.getNode(paths[0]);
            } else {
                this.docParser.getNodeRange(paths[0], paths[1]);
            }

        } catch (ParseException e) {
            System.err.println(e.getMessage());
        } catch (FileNotFoundException e){
            System.err.println("Incorrect path to file. Please provide correct absolute path.");
        } catch (InvalidParameterException e){
            System.err.println("Invalid parameters. Please check ordering and/or correct numerals.");
        }
    }

    private static boolean pathExists(String filePath) {
        Path path = Paths.get(filePath);
        return Files.exists(path);
    }


}

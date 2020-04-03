package agh.po.tto;

import agh.po.tto.cli.CLIHandler;
import agh.po.tto.doc.DocLine;
import agh.po.tto.doc.Document;
import agh.po.tto.preprocess.PreProcessor;
import agh.po.tto.search.RequestBuilder;
import org.apache.commons.cli.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    //TODO: may consider adding some more sophisticated formatting
    private static void displayWelcomeMessage() {
        String welcomeMessage =
                "Hello and welcome to the Act Parser \n" +
                "Use -h to display the how-to message. \n\n";
        System.out.println(welcomeMessage);

        Options options = CLIHandler.buildOptions();
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("LegalActParser", options);
    }


    public static void main(String[] args) throws ParseException {
        //displayWelcomeMessage();

        List<String> rawDocument = new ArrayList<>();
        String filename = "./src/main/resources/uokik.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while (br.ready()) {
                rawDocument.add(br.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        PreProcessor preProcessor = new PreProcessor(rawDocument);
        preProcessor.preprocess();
        ArrayList<DocLine> preprocessed = preProcessor.getPreProcessedInput();

//        for (DocLine line : preprocessed) {
//            System.out.println(line.getContent() + " -- \t" + line.getType());
//        }

        Document document = new Document(preprocessed);
        document.buildDocument();

        RequestBuilder request = new RequestBuilder(document.getRootNode());
        request.printTOC();




        //System.out.println("Its Britney, bitch");












        //Enter loop with Scanner
//        while (true) {
//            System.out.print(">>");
//            Scanner scan = new Scanner(System.in);
//            String s = scan.next();
//
//            CLIHandler.doCommand(s);
//
//        }


    }

}

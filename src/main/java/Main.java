import org.apache.commons.lang3.text.WordUtils;

public class Main {

    //TODO: may consider adding some more sophisticated formatting
    private static void displayWelcomeMessage() {
        String welcomeMessage =
                "Hello and welcome to the Act Parser! \n" +
                "Enter the path and options and we'll handle the rest. \n\n" +

                "The program arguments are <path_to_file> followed by: \n" +
                "-toc   prints the table of contents\n" +
                "-p     prints a selected paragraph\n";

        System.out.println(welcomeMessage);
    }

    public static void main(String[] args) {
        displayWelcomeMessage();
        //Enter loop with Scanner
            //Parse input to determine what we're doing today

            //Do said thing

        //Go back to loop to get next command



    }

}

package agh.po.tto.path;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.*;


public class DocPath {
    private String[] path;

    public DocPath(String[] path){
        this.path = path;
    }

    //TODO: supposes order in arguments is correct
    public static DocPath[] parseInputPaths(CommandLine cmd) {
        List<String> p1 = new ArrayList<>();
        List<String> p2 = new ArrayList<>();
        Set charSet = new HashSet();

        Option[] options = cmd.getOptions();
        for (Option option : options) {
            String op = option.getOpt();
            if(op.equals("f") || op.equals("h") || op.equals("t")) {
                continue;
            }
            String id = convertToNodeIdentifier(op, option.getValue());
            if(!charSet.contains(op)) {
                charSet.add(op);
                p1.add(id);
            }
            else {
                p2.add(id);
            }

        }
        for(String s : p1) {
            System.out.println(s);
        }
        for(String s : p2) {
            System.out.println(s);
        }

        String[] path1 =  p1.toArray(new String[0]);
        String[] path2 =  p2.toArray(new String[0]);

        return new DocPath[]{new DocPath(path1), new DocPath(path2)};

    }

    private static String convertToNodeIdentifier(String op, String value) {
        switch (op) {
            case ("s"):
                return "^DZIAŁ " + value;
            case ("c"):
                return "^Rozdział " + value;
            case ("a"):
                return "^Art. " + value + "\\.";
            case ("p"):
                return "^" + value + "\\. ";
            case ("pp"):
                return "^" + value + "\\)\\. ";
            case ("l"):
                return "^" + value + "\\)\\. ";
            default:
                return "";
        }
    }

    public String[] getPath() {
        return path;
    }
}

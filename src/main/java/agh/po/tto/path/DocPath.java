package agh.po.tto.path;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.util.*;


public class DocPath {
    private String[] path;

    public DocPath(String[] path){
        this.path = path;
    }

    public static DocPath[] parseInputPaths(CommandLine cmd) {
        List<String> p1 = new ArrayList<>();
        List<String> p2 = new ArrayList<>();
        Map<String, String> charMap = new HashMap<>();

        Option[] options = cmd.getOptions();
        for (Option option : options) {
            String op = option.getOpt();
            if(op.equals("f") || op.equals("h") || op.equals("t")) {
                continue;
            }
            String id = DocPath.convertToNodeIdentifier(op, option.getValue());
            if(!charMap.containsKey(op)) {
                charMap.put(op, option.getValue());
                p1.add(id);
            }
            else {
                if(charMap.get(op).compareTo(option.getValue()) > 0){
                    return new DocPath[]{};
                }
                p2.add(id);
            }
        }
        String[] path1 =  p1.toArray(new String[0]);
        String[] path2 =  p2.toArray(new String[0]);

        return new DocPath[]{new DocPath(path1), new DocPath(path2)};
    }

    private static String convertToNodeIdentifier(String op, String value) {
        return switch (op) {
            case ("s") -> "^DZIAŁ " + value + "$";
            case ("c") -> "^Rozdział " + value + "$";
            case ("a") -> "^Art. " + value + "\\.$";
            case ("p") -> "^" + value + ". ";
            case ("pp"), ("l") -> "^" + value + "\\) ";
            default -> "";
        };
    }

    public String[] getPath() {
        return path;
    }
}

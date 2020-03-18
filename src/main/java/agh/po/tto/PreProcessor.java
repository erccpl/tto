package agh.po.tto;

import agh.po.tto.structure.DocLineType;
import org.apache.commons.lang3.tuple.Pair;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Pre-processes the raw input .txt file and hands over a labeled list of
 * DocLines for further processing.
 */
public class PreProcessor {
    List<String> rawInput;
    List<String> preProcessedInput;
    PatternManager patternManager;

    ArrayList<DocLine> docLines;


    public PreProcessor(List<String> input) {
        this.rawInput = input;
        this.preProcessedInput = input;
        this.patternManager = new PatternManager();
        this.docLines = new ArrayList<>();
    }

    public List<String> getRawInput() {
        return rawInput;
    }

    public List<DocLine> getPreProcessedInput() {
        return docLines;
    }

    /**
     * Converts the input .txt file to an array of DocLines
     * @return list - a list of DocLines
     */
    //public ArrayList<DocLine> preprocess() {
    //1. Label every line and add to the list.
    //If it starts with Art. , then check if there is another designator behind that
    //if there is then it is added as a seperate line as
    public void preprocess() {
        removeRedundantLines();
        mergeDisjointLines();
        delimitSections();
        labelLines();
    }


    /**
     * Scans the raw document representation and removes the redundant lines based on regex expressions.
     * @return returns an array of String without the redundant lines
     */
    private void removeRedundantLines() {
        preProcessedInput = preProcessedInput.stream().filter(this::containsRedundantPattern).collect(Collectors.toList());
    }
    private boolean containsRedundantPattern(String line) {
        Matcher matcher;
        for (Pattern p : this.patternManager.getRedundantPatterns()) {
            matcher = p.matcher(line);
            if (matcher.find()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Scans the raw document representation and merges disjoint lines (those ending with "-").
     * @return returns an array of String without disjoint lines
     */
    private void mergeDisjointLines() {
        ListIterator<String> it = this.preProcessedInput.listIterator();
        while (it.hasNext()) {
            String line = it.next();
            Matcher m = patternManager.getDisjointLinePattern().matcher(line);
            if (m.find()) {
                String first = line.substring(0, line.length() - 1);
                it.remove();
                String second = it.next();
                it.set(first + second);
                it.previous();
            }
        }
    }


    /**
     * Scans the raw document representation and delimits sections, where structure may be on the same line.
     * Eg. Art 1. 1. ... -->
     * Art 1.
     * 1. ...
     * @return returns an array of String without the redundant lines
     */
    private void delimitSections() {
        ListIterator<String> it = this.preProcessedInput.listIterator();
        while(it.hasNext()) {
            String line = it.next();
            Matcher m = patternManager.getArticleOverlapPattern().matcher(line);
            if (m.find()) {
                int delimitingPoint = m.end();
                String first = line.substring(0, delimitingPoint);
                String newLine = line.substring(delimitingPoint+1);
                it.remove();
                it.add(first);
                it.add(newLine);
            }
        }
    }


    /**
     * Scans the raw document representation and builds a new array with labelled lines.
     * @return returns an array of DocLines, which contain metainformation about the line.
     */
    private void labelLines() {
        ListIterator<String> it = this.preProcessedInput.listIterator();
        while (it.hasNext()) {

            String line = it.next();
            boolean found = false;

            for (Pair<Pattern, DocLineType> label : patternManager.getLabels()) {
                Matcher m = label.getLeft().matcher(line);
                if (m.find()) {
                    DocLine newLabelledLine = new DocLine(label.getRight(), line);
                    this.docLines.add(newLabelledLine);
                    found = true;
                    break;
                }
            }
            if(!found) {
                switch (docLines.get(docLines.size()-1).getType()) {
                    case MAIN_HEADER, TITLE, SECTION:
                        docLines.add(new DocLine(DocLineType.TITLE, line));
                        break;
                    default:
                        docLines.add(new DocLine(DocLineType.NORMAL_TEXT, line));
                }
            }
        }
    }


}

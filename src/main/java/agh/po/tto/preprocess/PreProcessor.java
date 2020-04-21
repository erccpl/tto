package agh.po.tto.preprocess;

import agh.po.tto.doc.DocLine;
import agh.po.tto.doc.DocLineType;
import agh.po.tto.regex.PatternManager;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class PreProcessor {
    private List<String> rawInput;
    private List<String> preProcessedInput;
    private PatternManager patternManager;
    private List<DocLine> docLines;


    public PreProcessor(String filePath) {
        this.rawInput = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (br.ready()) {
                this.rawInput.add(br.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.preProcessedInput = rawInput;
        this.patternManager = new PatternManager();
        this.docLines = new ArrayList<>();
    }

    public List<String> getRawInput() {
        return rawInput;
    }

    public List<DocLine> getPreProcessedInput() {
        return docLines;
    }

    public void preprocess() {
        removeRedundantLines();
        mergeDisjointLines();
        delimitSections();
        labelLines();
    }

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

    private void delimitSections() {
        ListIterator<String> it = this.preProcessedInput.listIterator();
        while(it.hasNext()) {
            String line = it.next();
            Matcher m = patternManager.getArticleOverlapPattern().matcher(line);
            if (m.find()) {
                int delimitingPoint = m.end();
                String first = line.substring(0, delimitingPoint);
                String newLine = line.substring(delimitingPoint);
                it.remove();
                it.add(first);
                it.add(newLine);
            }
        }
    }

    private void labelLines() {
        ListIterator<String> it = this.preProcessedInput.listIterator();
        if (docLines.isEmpty()) {
            DocLine newLabelledLine = new DocLine(DocLineType.HEADER, it.next());
            this.docLines.add(newLabelledLine);
            newLabelledLine = new DocLine(DocLineType.HEADER, it.next());
            this.docLines.add(newLabelledLine);
            newLabelledLine = new DocLine(DocLineType.HEADER, it.next());
            this.docLines.add(newLabelledLine);
        }
        while (it.hasNext()) {
            String line = it.next().strip();
            DocLineType previousLineType = docLines.get(docLines.size() - 1).getType();
            Pair<Pattern, DocLineType> label = this.getMatchingLabel(line);
            Matcher m = patternManager.getAllCapsPattern().matcher(line);

            if (previousLineType == DocLineType.CHAPTER || previousLineType == DocLineType.SECTION) {
                docLines.add(new DocLine(DocLineType.TITLE, line));
            }
            else if (label.getRight() != DocLineType.TEXT) {
                docLines.add(new DocLine(label.getRight(), line));
            }
            else if(m.find()) {
                docLines.add(new DocLine(DocLineType.TITLE, line));
            }
            else {
                docLines.add(new DocLine(label.getRight(), line));
            }

        }
    }

    private Pair<Pattern, DocLineType> getMatchingLabel(String line) {
        for (Pair<Pattern, DocLineType> label : patternManager.getLabels()) {
            Matcher m = label.getLeft().matcher(line);
            if (m.find()) {
                return label;
            }
        }
        return Pair.of(Pattern.compile(""), DocLineType.TEXT);
    }

}

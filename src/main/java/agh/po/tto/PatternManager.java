package agh.po.tto;

import agh.po.tto.structure.DocLineType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * This class is for managing all the regular expressions handled in the preprocessing phase.
 * Ths PreProcessor class HAS-A PatternManager class and queries it to perform specific tasks.
 */

public class PatternManager {
    private Set<Pattern> redundantPatterns = new HashSet<>();
    private Set<Pair<Pattern, DocLineType>> labels = new HashSet<>();
    private Pattern disjointLinePattern;
    private Pattern articleOverlapPattern;
    private Pattern allCapsPattern;


    public PatternManager() {
        //Redundant patterns
        Pattern copyright = Pattern.compile("^©");
        this.redundantPatterns.add(copyright);

        Pattern reference = Pattern.compile("^Dz.U.");
        this.redundantPatterns.add(reference);

        Pattern date = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        this.redundantPatterns.add(date);

        Pattern oneCharacter = Pattern.compile("^\\w{1}$");
        this.redundantPatterns.add(oneCharacter);


        //End of a line that requires merging
        this.disjointLinePattern = Pattern.compile("-$");


        //Jumbled article with first point that needs seperation
        this.articleOverlapPattern = Pattern.compile("^Art. [0-9a-z]+. ");


        //Speical all-caps pattern
        //TODO: write a proper regex for this
        this.allCapsPattern = Pattern.compile("^[A-Z]{3}");



        //Patterns for labelling the document
        Pattern sectionPattern = Pattern.compile("^DZIAŁ [I|V|X]+$");
        this.labels.add(Pair.of(sectionPattern, DocLineType.SECTION));

        Pattern chapterPattern = Pattern.compile("^Rozdział [I|V|X|[1-9]]+$");
        this.labels.add(Pair.of(chapterPattern, DocLineType.CHAPTER));

        Pattern articlePattern = Pattern.compile("^Art. [0-9a-z]+.");
        this.labels.add(Pair.of(articlePattern, DocLineType.ARTICLE));

        Pattern numDotPattern = Pattern.compile("^[1-9]+[a-z]*\\.[a-z]*");
        this.labels.add(Pair.of(numDotPattern, DocLineType.NUM_DOT));

        Pattern numParenPattern = Pattern.compile("^[1-9]+[a-z]*\\)");
        this.labels.add(Pair.of(numParenPattern, DocLineType.NUM_PAREN));

        Pattern letterParenPattern = Pattern.compile("^[a-z]\\)");
        this.labels.add(Pair.of(letterParenPattern, DocLineType.LETTER_PAREN));
    }


    public Set<Pattern> getRedundantPatterns() {
        return redundantPatterns;
    }

    public Pattern getDisjointLinePattern() {
        return disjointLinePattern;
    }

    public Pattern getArticleOverlapPattern() {
        return articleOverlapPattern;
    }

    public Set<Pair<Pattern,DocLineType>> getLabels() {
        return labels;
    }

    public Pattern getAllCapsPattern() {
        return allCapsPattern;
    }
}

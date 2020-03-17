package agh.po.tto;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * This class is intended to manage all the regular expressions handled in the preprocessing phase.
 * Ths PreProcessor class HAS-A PatternManager class and queries it to perform specific tasks.
 */

public class PatternManager {
    Set<Pattern> redundantPatterns = new HashSet<>();
    Pattern disjointLinePattern;
    Pattern articleOverlapPattern;


    public PatternManager() {
        //Redundant patterns
        Pattern copyright = Pattern.compile("^©");
        this.redundantPatterns.add(copyright);

        Pattern reference = Pattern.compile("^Dz.U.");
        this.redundantPatterns.add(reference);

        Pattern date = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        this.redundantPatterns.add(date);

        Pattern oneCharacter = Pattern.compile("^[a-zA-Z0-9]{1}$");
        this.redundantPatterns.add(oneCharacter);


        //End of a line that requires merging
        this.disjointLinePattern = Pattern.compile("-$");


        //Jumbled article with first point that needs seperation
        this.articleOverlapPattern = Pattern.compile("^Art. [1-9]+. ");

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
}

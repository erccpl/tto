package agh.po.tto.doc;


/**
 * Represents the structure of the document.
 * Used by the pre-processor to label every line of the document
 * Many documents have a similar structures and this class suffices for the two documents in use.
 * However, in case of some exotic structures the strategy pattern would need to be employed
 * to select a correct hierarchy.
 */
public enum DocLineType {

        HEADER,         //0
        SECTION,        //1
        CHAPTER,        //2
        TITLE,          //3
        ARTICLE,        //4
        NUM_DOT,        //5
        NUM_PAREN,      //6
        LETTER_PAREN,   //7
        TEXT;           //8

        /**
         * Returns the depth of the current line with regards to the entire document
         * @return
         */
        public int getDepth() {
            return this.ordinal();
        }
}

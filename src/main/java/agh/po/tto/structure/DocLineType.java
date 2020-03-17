package agh.po.tto.structure;


/**
 * Represents the structure of the document.
 * Used by the pre-processor to label every line of the document
 * Many documents have a similar structures and this class suffices for the two documents in use.
 * However, in case of some exotic structures the strategy pattern would need to be employed
 * to select a correct hierarchy.
 */
public enum DocLineType {

        HEADER,
        SECTION,
        CHAPTER,
        TITLE,
        POINT_DOT,
        POINT_PAREN,
        LETTER,
        NORMAL_TEXT;

        /**
         * Returns the depth of the current line with regards to the entire document
         * @return
         */
        public int getDepth() {
            return this.ordinal();
        }
}

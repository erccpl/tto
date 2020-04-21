package agh.po.tto.doc;

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


        public int getDepth() {
            return this.ordinal();
        }
}

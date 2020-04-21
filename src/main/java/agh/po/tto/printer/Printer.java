package agh.po.tto.printer;

import agh.po.tto.doc.DocLine;

import java.util.List;

//TODO: option to separate printing
public class Printer {

    public static void printTOC(List<DocLine> toc) {
        for (DocLine line : toc){
            System.out.println(line.getContent());
        }
    }


    class Formatter {
    }

}

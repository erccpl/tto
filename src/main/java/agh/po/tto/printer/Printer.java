package agh.po.tto.printer;

import agh.po.tto.doc.DocLine;

import java.util.List;

public class Printer {

    public static void printTOC(List<DocLine> toc) {
        for (DocLine line : toc){
            System.out.println(line.getContent());
        }
    }


    class Formatter {



    }

//TODO : how exactly does this iteration work
//    public void printTOC(List<DocLine> toc) {
//        ListIterator<DocLine> it = toc.listIterator();
//
//        StringBuilder indent = new StringBuilder();
//        DocLine start = it.next();
//        System.out.println(start.getContent());
//
//        while(it.hasNext()) {
//            DocLine previousLine = it.previous();
//            it.next();
//            DocLine currentLine = it.next();
//
//            if (currentLine.getType().getDepth() == 1 || currentLine.getType().getDepth() == 2){
//                indent.delete(0, indent.length());
//            }
//
//            else if(currentLine.getType().getDepth() == previousLine.getType().getDepth()) {
//                System.out.println(currentLine.getContent());
//            }
//            else if (currentLine.getType().getDepth() > previousLine.getType().getDepth()) {
//                indent.append("\t");
//                System.out.println(indent + currentLine.getContent());
//
//            } else if (currentLine.getType().getDepth() < previousLine.getType().getDepth()) {
//                if(indent.length() > 0) {
//                    indent.deleteCharAt(indent.length());
//                }
//                System.out.println(indent + currentLine.getContent());
//            }
//        }
//    }







}
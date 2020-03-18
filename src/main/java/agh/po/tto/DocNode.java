package agh.po.tto;

import agh.po.tto.structure.DocLineType;

import java.util.ArrayList;

/**
 * Represents a single node in the graph of the input file
 */
public class DocNode {
    DocLine id;
    DocNode parent;
    ArrayList<DocLine> contents;
    ArrayList<DocNode> subContents;



}

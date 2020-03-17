package agh.po.tto;

import java.util.ArrayList;

/**
 * Represents a single node in the graph representing the input file
 */
public class DocNode {
    DocLine identifier;
    DocNode parent;
    ArrayList<DocLine> contents;
    ArrayList<DocNode> subContents;



}

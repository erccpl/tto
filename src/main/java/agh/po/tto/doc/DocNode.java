package agh.po.tto.doc;

import java.util.ArrayList;

/**
 * Represents a single node in the graph of the input file
 */

//TODO: contents should be transformed into a String at this point
public class DocNode {

    private int depth;
    private DocNode parent;
    private ArrayList<DocLine> id;
    private ArrayList<DocLine> contents;
    private ArrayList<DocNode> subContents;

    public DocNode() {
        this.id = new ArrayList<>();
        this.contents = new ArrayList<>();
        this.subContents = new ArrayList<>();
    }

    public ArrayList<DocLine> getId() {
        return id;
    }

    public DocNode getParent() {
        return parent;
    }

    public ArrayList<DocLine> getContents() {
        return contents;
    }

    public ArrayList<DocNode> getSubContents() {
        return subContents;
    }

    public void setId(ArrayList<DocLine> id) {
        this.id = id;
    }

    public void setParent(DocNode parent) {
        this.parent = parent;
    }

    public void setContents(ArrayList<DocLine> contents) {
        this.contents = contents;
    }

    public void setSubContents(ArrayList<DocNode> subContents) {
        this.subContents = subContents;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

}

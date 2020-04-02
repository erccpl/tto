package agh.po.tto;

import agh.po.tto.node.DocNode;
import agh.po.tto.structure.DocLineType;

import java.util.ArrayList;
import java.util.List;

public class Document {
    private ArrayList<DocLine> labelledLines;
    private DocNode rootNode;

    public Document(ArrayList<DocLine> labelledLines) {
        this.labelledLines = labelledLines;
        this.rootNode = new DocNode();
    }

    public void buildDocument() {
        for (DocLine line : labelledLines) {

        }


    }

    private void createNode(int index, DocNode currentNode, DocLine line, DocNode rootNode) {

        if (index == labelledLines.size()) {
            return;
        }

        if (rootNode.getId().isEmpty()) {
            rootNode.setParent(rootNode);
            rootNode.getId().add(labelledLines.get(index));
            createNode(index + 1, rootNode, labelledLines, rootNode);

        }

        DocLine currentLine = labelledLines.get(index);
        int lineDepth = currentLine.getType().getDepth();

        if (lineDepth == 0) {
            currentNode.getId().add(currentLine);
            createNode(index + 1, currentNode, labelledLines, rootNode);
        }

        else if (currentLine.getType() == DocLineType.TEXT) {
            currentNode.getContents().add(currentLine);
            createNode(index + 1, currentNode, labelledLines, rootNode);
        }

        else if (lineDepth == 3) {
            DocNode parent = findParent(lineDepth, currentNode);
            parent.getContents().add(currentLine);
            createNode(index + 1, currentNode, labelledLines, rootNode);
        }

        else {
            DocNode newNode = new DocNode();
            newNode.getId().add(currentLine);
            DocNode parent = findParent(newNode.getId().get(1).getType().getDepth(), currentNode);
            newNode.setParent(parent);
            parent.getSubContents().add(newNode);
            createNode(index + 1, newNode, labelledLines, rootNode);
        }

    }



    private DocNode findParent(int newNodeDepth, DocNode currentNode) {
        int currentNodeDepth = currentNode.getId().get(1).getType().getDepth();
        DocNode parent = currentNode;

        while(newNodeDepth - currentNodeDepth <= 0) {
            parent = parent.getParent();
            currentNodeDepth = parent.getId().get(1).getType().getDepth();
        }
        return parent;
    }


}

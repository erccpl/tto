package agh.po.tto.doc;

import java.util.List;

public class Document {
    private List<DocLine> labelledLines;
    private DocNode rootNode;

    public Document(List<DocLine> labelledLines) {
        this.labelledLines = labelledLines;
        this.rootNode = new DocNode();
    }

    public void buildDocument() {
        DocNode currentNode = rootNode;
        for (DocLine line : labelledLines) {
            currentNode = createNode(line, currentNode);
        }
    }

    private DocNode createNode(DocLine currentLine, DocNode currentNode) {

        if (currentNode.getId().isEmpty()) {
            currentNode.setParent(currentNode);
            currentNode.getId().add(currentLine);
            return currentNode;
        }

        int lineDepth = currentLine.getType().getDepth();

        if (lineDepth == 0) {
            currentNode.getId().add(currentLine);
            return currentNode;
        }

        else if (lineDepth == 3) {
            DocNode parent = findParent(lineDepth, currentNode);
            if(parent.getId().size() == 1) {
                parent.getId().add(currentLine);
            } else {
                parent.getContents().add(currentLine);
            }
            return currentNode;
        }

        else if (currentLine.getType() == DocLineType.TEXT) {
            currentNode.getContents().add(currentLine);
            return currentNode;
        }

        else {
            DocNode newNode = new DocNode();
            newNode.getId().add(currentLine);
            newNode.setDepth(currentLine.getType().getDepth());
            DocNode parent = findParent(lineDepth, currentNode);
            newNode.setParent(parent);
            parent.getSubContents().add(newNode);
            return newNode;
        }

    }

    private DocNode findParent(int newNodeDepth, DocNode currentNode) {
        int currentNodeDepth = currentNode.getDepth();
        DocNode parent = currentNode;

        while(newNodeDepth - currentNodeDepth <= 0) {
            parent = parent.getParent();
            currentNodeDepth = parent.getDepth();
        }
        return parent;
    }


    public DocNode getRootNode() {
        return rootNode;
    }
}

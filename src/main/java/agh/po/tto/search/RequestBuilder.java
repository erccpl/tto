package agh.po.tto.search;

import agh.po.tto.doc.DocLine;
import agh.po.tto.doc.DocNode;

import java.util.List;

public class RequestBuilder {
    private DocNode rootNode;
    private List<DocNode> response;

    public RequestBuilder(DocNode rootNode) {
        this.rootNode = rootNode;
    }

    public void printTOC() {
        printTOCValues(rootNode);
    }

    //TODO: nodes need to store type, this kind of access is ridiculous
    //TODO: separate formatter class
    private void printTOCValues(DocNode rootNode) {
        if (rootNode.getId().get(0).getType().getDepth() <= 2) {
            for (DocLine line : rootNode.getId()) {
                System.out.println(line.getContent());
            }
            for (DocLine line : rootNode.getContents()) {
                System.out.println(line.getContent());
            }
        }
        for (DocNode subNode : rootNode.getSubContents()) {
            printTOCValues(subNode);
        }

    }


}

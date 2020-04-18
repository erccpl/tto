package agh.po.tto.search;

import agh.po.tto.doc.DocLine;
import agh.po.tto.doc.DocNode;
import agh.po.tto.path.DocPath;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NodeParser {
    private DocNode rootNode;
    private NodeFinder nodeFinder;
    private List<DocLine> toc;

    public NodeParser(DocNode rootNode) {
        this.rootNode = rootNode;
        this.nodeFinder = new NodeFinder(rootNode);
        this.toc = new ArrayList<>();
    }

    public void createTOC() {
        buildTOC(rootNode);
    }

    private void buildTOC(DocNode rootNode) {
        if (rootNode.getDepth() <= 2) {
            this.toc.addAll(rootNode.getId());
            if(rootNode.getDepth() != 0) {
                this.toc.addAll(rootNode.getContents());
            }
        }
        for (DocNode subNode : rootNode.getSubContents()) {
            buildTOC(subNode);
        }
    }

    public void printTOC() {
        toc.forEach(l -> System.out.println(l.getContent()));
    }


    public void getOneNode(DocPath path) {
        DocNode node = nodeFinder.findNode(path);
        if(node == null) {
            System.out.println("Path does not exist.");
            return;
        }
        List<String> result = getNodeContents(node, new ArrayList<>());
        result.forEach(System.out::println);
    }

    public void getRange(DocPath start, DocPath end) {
        DocNode node1 = nodeFinder.findNode(start);
        DocNode node2 = nodeFinder.findNode(end);
        if(node1 == null || node2 == null){
            System.out.println("At least one of the given paths does not exist");
        }
        List<String> result = getNodeContents(node1, node2, new ArrayList<>());
        result.forEach(System.out::println);
    }

    private List<String> getNodeContents(DocNode node, List<String> result) {
        for (DocLine line : node.getId()) {
            result.add(line.getContent());
        }
        for (DocLine line : node.getContents()) {
            result.add(line.getContent());
        }
        for (DocNode subNode : node.getSubContents()){
            getNodeContents(subNode, result);
        }
        return result;
    }

    private List<String> getNodeContents(DocNode node1, DocNode node2, List<String> result) {
        result = getNodeContents(node1, result);
        DocNode nextNode = node1;
        while(nextNode.getDepth() >= node2.getDepth()){
            nextNode = getNextNode(nextNode, node2);
            result = getNodeContents(nextNode, result);
        }
        int next = nextNode.getSubContents().indexOf(node1);

        while(nextNode.getSubContents().indexOf(next) != nextNode.getSubContents().indexOf(node2) + 1) {
            result = getNodeContents(nextNode.getSubContents().get(next), result);
            next++;
        }
        return result;
    }

    private DocNode getNextNode(DocNode node1, DocNode node2) {
        DocNode parent = node1.getParent();
        Pattern id = Pattern.compile(parent.getId().get(0).getContent());
        Matcher m;
        for(int i = 0; i < parent.getSubContents().size(); i++) {
            m = id.matcher(parent.getSubContents().get(i).getId().get(0).getContent());
            if(m.find()){
                if(i == parent.getSubContents().size()) {
                    return parent;
                }
                return parent.getSubContents().get(i+1);
            }
        }
        return null;
    }


    private boolean verifyResult(List<String> result, String lastId) {
        Pattern pattern = Pattern.compile(lastId);
        for (String line : result) {
            if (pattern.matcher(line).find()) {
                return true;
            }
        }
        return false;
    }


}

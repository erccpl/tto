package agh.po.tto.search;

import agh.po.tto.doc.DocLine;
import agh.po.tto.doc.DocNode;
import agh.po.tto.path.DocPath;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class DocParser {

    private DocNode rootNode;
    private NodeFinder nodeFinder;
    private List<DocLine> toc;

    public DocParser(DocNode rootNode) {
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

    public void getNode(DocPath path) {
        DocNode node = nodeFinder.findNode(path);
        if(node == null) {
            System.err.println("Path does not exist (maybe wrong numerals?)");
            return;
        }
        List<String> result = getNodeContents(node, new ArrayList<>());
        result.forEach(System.out::println);
    }

    public void getNodeRange(DocPath start, DocPath end) {
        DocNode node1 = nodeFinder.findNode(start);
        DocNode node2 = nodeFinder.findNode(end);
        if(node1 == null || node2 == null){
            System.err.println("At least one of the given paths does not exist");
            return;
        }
        List<String> result = getNodeRangeContents(node1, node2, end, new ArrayList<>());
        result = result.stream().distinct().collect(Collectors.toList());
        result.forEach(System.out::println);
    }

    private List<String> getNodeRangeContents(DocNode node1, DocNode node2, DocPath end, List<String> result) {
        String endPattern = node2.getId().get(0).getContent()
                .replaceAll("\\)", "\\\\)");
        Pattern endNodeId = Pattern.compile(endPattern);
        Matcher m;
        DocNode currentNode = node1;
        int previousDepth;
        int currentDepth;

        getNodeContents(currentNode, result);
        while(true) {
            m = endNodeId.matcher(currentNode.getId().get(0).getContent());
            if(m.find()) {
                getNodeContents(currentNode, result);
                break;
            }
            previousDepth = currentNode.getDepth();

            currentNode = getNextNode(currentNode);
            currentDepth = currentNode.getDepth();

            if(currentDepth == previousDepth) {
                getNodeContents(currentNode, result);
                while(currentNode.getSubContents().size() != 0){
                    currentNode = currentNode.getSubContents().get(0);
                }
                getNodeContents(currentNode, result);
            }
        }
        return result;
    }

    private DocNode getNextNode(DocNode node) {
        DocNode parent = node.getParent();
        String pattern = node.getId().get(0).getContent()
                .replaceAll("\\)", "\\\\)").replaceAll("\\(", "\\\\(");
        Pattern id = Pattern.compile(pattern);
        Matcher m;
        for(int i = 0; i < parent.getSubContents().size(); i++) {
            m = id.matcher(parent.getSubContents().get(i).getId().get(0).getContent());
            if(m.find()){
                if(i == parent.getSubContents().size() - 1) {
                    return parent;
                }
                return parent.getSubContents().get(i+1);
            }
        }
        return parent.getSubContents().get(0);
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

    public List<DocLine> getToc() {
        return toc;
    }
}

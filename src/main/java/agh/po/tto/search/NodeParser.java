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
    private List<DocLine> toc;

    public NodeParser(DocNode rootNode) {
        this.rootNode = rootNode;
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
        List<String> result = new ArrayList<>();
        String lastElement = path.getStart()[path.getStart().length - 1];

        findOneNode(rootNode, path.getStart(), 0, result);
        if (verifyResult(result, lastElement)) {
            result.forEach(System.out::println);
        } else {
            System.out.println("Path does not exist.");
        }
    }

    public void getRange(DocPath path) {

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

    //TODO: why no null pointer exception
    private void findOneNode(DocNode node, String[] ids, int index, List<String> result) {
        //TODO: pattern is compiled every time
        Pattern pattern = Pattern.compile(ids[index]);
        String line = node.getId().get(0).getContent();
        Matcher m = pattern.matcher(line);

        if (!m.find()) {
            for (DocNode subNode : node.getSubContents()) {
                findOneNode(subNode, ids, index, result);
            }
        }
        else {
            node.getId().forEach(l -> result.add(l.getContent()));
            if (index == ids.length - 1) {
                node.getContents().forEach(l -> result.add(l.getContent()));
                for (DocNode subNode : node.getSubContents()) {
                    findOneNode(subNode, new String[]{"."}, 0, result);
                }
            }
            else if (++index != ids.length) {
                for (DocNode subNode : node.getSubContents()) {
                    findOneNode(subNode, ids, index, result);
                }
            }
        }
    }


    public void getRange() {



    }





}

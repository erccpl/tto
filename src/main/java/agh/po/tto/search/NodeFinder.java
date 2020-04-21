package agh.po.tto.search;

import agh.po.tto.doc.DocNode;
import agh.po.tto.path.DocPath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NodeFinder {
    private DocNode rootNode;
    private DocNode result;
    private int unique;

    public NodeFinder(DocNode rootNode) {
        this.rootNode = rootNode;
        this.unique = 0;
    }

    public DocNode findNode(DocPath path) {
        findNode(rootNode, path.getPath(), 0);
        if(unique > 1) {
            return null;
        }
        unique = 0;
        return result;
    }

    private void findNode(DocNode node, String[] ids, int index) {
        Pattern pattern = Pattern.compile(ids[index]);
        String line = node.getId().get(0).getContent();
        Matcher m = pattern.matcher(line);

        if (!m.find()) {
            for (DocNode subNode : node.getSubContents()) {
                findNode(subNode, ids, index);
            }
        }
        else {
            if (index == ids.length - 1) {
                if(ids.length == 1) {
                    this.unique++;
                    this.result = node;
                    return;
                }
                String l = node.getParent().getId().get(0).getContent();
                Pattern p = Pattern.compile(ids[index-1]);
                if(p.matcher(l).find()) {
                    this.result = node;
                }
            }
            else if (++index != ids.length) {
                for (DocNode subNode : node.getSubContents()) {
                    findNode(subNode, ids, index);
                }
            }
        }
    }
}

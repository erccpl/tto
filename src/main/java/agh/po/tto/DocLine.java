package agh.po.tto;


import agh.po.tto.structure.DocLineType;

public class DocLine {
    private DocLineType type;
    private String content;

    DocLine(DocLineType type, String content) {
        this.type = type;
        this.content = content;
    }

    public DocLineType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}

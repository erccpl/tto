package agh.po.tto.doc;


public class DocLine {
    private DocLineType type;
    private String content;

    public DocLine(DocLineType type, String content) {
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

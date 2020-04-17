package agh.po.tto.path;

public class DocPath {
    private String[] start;
    private String[] end;

    public DocPath(String[] start, String[] end) {
        this.start = start;
        this.end = end;
    }

    public DocPath(String[] left) {
        this.start = left;
        this.end = new String[]{""};

    }

    public String[] getStart() {
        return start;
    }

    public String[] getEnd() {
        return end;
    }


}

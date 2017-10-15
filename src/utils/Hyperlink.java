package utils;

public class Hyperlink {
    private String myLabel;
    private String myUrl;

    public Hyperlink(String label, String url) {
        myLabel = label;
        myUrl = url;
    }

    public String getLabel() {
        return myLabel;
    }

    public void setLabel(String label) {
        myLabel = label;
    }

    public String getUrl() {
        return myUrl;
    }

    public void setUrl(String url) {
        myUrl = url;
    }
}

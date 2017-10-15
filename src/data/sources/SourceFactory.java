package data.sources;

public class SourceFactory {
    public static Source getSource(String sourceName, String apiKey) {
        Source source;
        switch(sourceName) {
            default:
                source = new CircleCiSource(apiKey);
                break;
        }

        return source;
    }
}

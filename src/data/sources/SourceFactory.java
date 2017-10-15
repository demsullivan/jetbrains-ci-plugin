package data.sources;

import ui.settings.CiOptionsProvider;

public class SourceFactory {
    public static Source getSource(String sourceName, String apiKey, String project) {
        Source source;
        switch(sourceName) {
            default:
                source = new CircleCiSource(apiKey, project);
                break;
        }

        return source;
    }

    public static Source getSource(CiOptionsProvider provider) {
        return getSource(provider.getService(), provider.getApiKey(), provider.getProjectName());
    }
}

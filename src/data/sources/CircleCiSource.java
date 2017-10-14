package data.sources;

import data.structures.Build;
import data.structures.BuildStep;
import data.structures.circleci.CircleCiBuild;
import data.structures.circleci.CircleCiBuildStep;
import utils.*;
import com.google.gson.*;

public class CircleCiSource implements Source {
    public String myApiKey;


    private Gson gson;
    private String rootUrl = "https://circleci.com/api/v1.1";
    private String projectPath;

    public CircleCiSource(String apiKey) {
        myApiKey = apiKey;
        projectPath = "github/enginuitygroup/platform";
        gson = new Gson();
    }

    @Override
    public Build[] getBuilds() {
        String response = fetchData(getProjectUrl());
        return gson.fromJson(response, CircleCiBuild[].class);
    }

    @Override
    public BuildStep[] getStepsForBuild(Build build) {
        String response = fetchData(getProjectUrl() + "/" + build.getBuildNum());

        JsonParser parser = new JsonParser();
        JsonObject dataObject = parser.parse(response).getAsJsonObject();

        return gson.fromJson(dataObject.get("steps"), CircleCiBuildStep[].class);
    }

    private String fetchData(String url) {
        return HttpRequest.get(url, true, "circle-token", myApiKey).body();
    }


    private String getProjectUrl() {
        return rootUrl + "/project/" + projectPath;
    }
}

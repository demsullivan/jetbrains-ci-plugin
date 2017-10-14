package data.sources;

import data.structures.Build;
import data.structures.circleci.CircleCiBuild;
import utils.*;
import com.google.gson.*;


public class CircleCiSource implements Source {
    public String myApiKey;

    private Gson gson;
    private String rootUrl = "https://circleci.com/api/v1.1";

    public CircleCiSource(String apiKey) {
        myApiKey = apiKey;
        gson = new Gson();
    }

    @Override
    public Build[] getBuilds() {
        String response = HttpRequest.get(rootUrl + "/project/github/enginuitygroup/platform", true, "circle-token", myApiKey).body();
        return gson.fromJson(response, CircleCiBuild[].class);
    }

//    @Override
//    public Step[] getStepsForBuild(Build build) {
//        return new Arr
//    }
}

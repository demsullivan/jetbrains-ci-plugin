package data.sources;

import data.structures.*;
import utils.*;
import com.google.gson.*;


public class CircleCiSource implements Source {
    public String myApiKey;

    private Gson gson;
    private String rootUrl = "https://circleci.com/api/v1.1";

    public void CircleCiSource(String apiKey) {
        myApiKey = apiKey;
        gson = new Gson();
    }

    @Override
    public Build[] getBuilds() {
        String response = HttpRequest.get(rootUrl + "/project/github/enginuitygroup/platform").body();
        return gson.fromJson(response, Build[].class);
    }

//    @Override
//    public Step[] getStepsForBuild(Build build) {
//        return new Arr
//    }
}

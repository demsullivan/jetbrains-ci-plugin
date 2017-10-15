package data.sources;

import data.structures.Build;
import data.structures.BuildStep;
import data.structures.circleci.CircleCiBuild;
import data.structures.circleci.CircleCiBuildStep;
import utils.*;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CircleCiSource implements Source {
    private Gson gson;
    private String rootUrl = "https://circleci.com/api/v1.1";

    private String myApiKey;
    private String myProjectPath;

    public CircleCiSource(String apiKey, String projectPath) {
        myApiKey = apiKey;
        myProjectPath = projectPath;
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

    @Override
    public String[] getProjects() {
        String response = fetchData("/projects");
        JsonParser parser = new JsonParser();
        JsonArray projects = parser.parse(response).getAsJsonArray();

        List<String> projectNames = new ArrayList<String>();

        Iterator<JsonElement> iterator = projects.iterator();

        while (iterator.hasNext()) {
            JsonObject project = iterator.next().getAsJsonObject();

            String vcsUrl = project.get("vcs_url").getAsString();
            String vcsType;

            if (vcsUrl.indexOf("github") > -1) {
                vcsType = "github";
            } else {
                vcsType = "bitbucket";
            }

            projectNames.add(vcsType + "/" + project.get("username").getAsString() + "/" + project.get("reponame").getAsString());
        }

        String[] projectNamesArr = new String[projectNames.size()];
        return projectNames.toArray(projectNamesArr);
    }

    @Override
    public Boolean testConnection() {
        return request("/me").code() == 200;
    }

    private HttpRequest request(String url) {
        return HttpRequest.get(rootUrl + url, true, "circle-token", myApiKey);
    }

    private String fetchData(String url) {
        return request(url).body();
    }

    private String getProjectUrl() {
        return "/project/" + myProjectPath;
    }
}

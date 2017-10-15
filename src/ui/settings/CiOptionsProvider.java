package ui.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;

@State(
        name = "CiSettings",
        storages = @Storage("ci_settings.xml")
)
public class CiOptionsProvider implements PersistentStateComponent<CiOptionsProvider.State> {
    public static class State {
        public String service = "";
        public String apiKey = "";
        public String projectName = "";
    }

    private State myState = new State();

    public static CiOptionsProvider getInstance(Project project) {
        return ServiceManager.getService(project, CiOptionsProvider.class);
    }

    public State getState() {
        return myState;
    }

    public void loadState(State state) {
        myState = state;
    }

    public String getService() {
        return myState.service;
    }

    public void setService(String service) {
        myState.service = service;
    }

    public Boolean hasService() {
        return myState.service != "";
    }

    public String getApiKey() {
        return myState.apiKey;
    }

    public void setApiKey(String apiKey) {
        myState.apiKey = apiKey;
    }

    public Boolean hasApiKey() {
        return myState.apiKey != "";
    }

    public String getProjectName() {
        return myState.projectName;
    }

    public void setProjectName(String projectName) {
        myState.projectName = projectName;
    }

    public Boolean hasProjectName() {
        return myState.projectName != "";
    }


}

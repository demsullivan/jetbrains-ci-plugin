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
    private State myState;

    public static CiOptionsProvider getInstance(Project project) {
        return ServiceManager.getService(project, CiOptionsProvider.class);
    }

    @Override
    public State getState() {
        return myState;
    }

    @Override
    public void loadState(State state) {
        myState.service = state.service;
        myState.apiKey = state.apiKey;
        myState.projectName = state.projectName;
    }

    public String getService() {
        return myState.service;
    }

    public void setService(String service) {
        myState.service = service;
    }

    public String getApiKey() {
        return myState.apiKey;
    }

    public void setApiKey(String apiKey) {
        myState.apiKey = apiKey;
    }

    public String getProjectName() {
        return myState.projectName;
    }

    public void setProjectName(String projectName) {
        myState.projectName = projectName;
    }

    public class State {
        String service = "";
        String apiKey = "";
        String projectName = "";
    }
}

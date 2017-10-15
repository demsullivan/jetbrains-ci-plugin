package ui.settings;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import org.antlr.v4.runtime.misc.NotNull;
import org.jetbrains.annotations.Nls;

import javax.swing.*;

public class CiOptionsConfigurable implements SearchableConfigurable, Disposable {
    private CiSettingsPanel myPanel;
    private final CiOptionsProvider myOptionsProvider;

    private Project myProject;

    public CiOptionsConfigurable(@NotNull Project project) {
        myProject = project;
        myOptionsProvider = CiOptionsProvider.getInstance(myProject);
    }

    @NotNull
    @Override
    public String getId() {
        return "ci";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Continuous Integration";
    }

    @Override
    public JComponent createComponent() {
        myPanel = new CiSettingsPanel();
        return myPanel.createPanel(myOptionsProvider);
    }

    @Override
    public boolean isModified() {
        return myPanel.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        myPanel.apply();
    }

    @Override
    public void reset() {
        myPanel.reset();
    }

    @Override
    public void disposeUIResources() {
        Disposer.dispose(this);
    }

    @Override
    public void dispose() {
        myPanel = null;
    }
}

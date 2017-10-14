package ui.ciToolWindow;

import data.sources.*;
import data.structures.*;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBList;
import com.intellij.ui.content.*;

import javax.swing.*;

public class CiToolWindowFactory implements ToolWindowFactory {
    private ToolWindow ciToolWindow;
    private JPanel ciToolWindowContent;
    private JBList<String> listBuilds;
    private JBList<String> listSteps;
    private JBSplitter splitter;

    private Source dataSource;

    public CiToolWindowFactory() {
        dataSource = new CircleCiSource("e2386cc9784bffeb4aac72cc6cc75a48d77560ae");
    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        ciToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(ciToolWindowContent, "", false);

        ciToolWindowContent.add(createSplitter());

        toolWindow.getContentManager().addContent(content);

        getBuilds();
    }

    private JComponent createSplitter() {
        splitter = new JBSplitter();

        listBuilds = new JBList<String>();
        listSteps = new JBList<String>();

        splitter.setFirstComponent(listBuilds);
        splitter.setSecondComponent(listSteps);

        return splitter;
    }

    private void getBuilds() {
        Build[] builds = dataSource.getBuilds();

        DefaultListModel<String> model = new DefaultListModel<String>();

        for (int i=0; i < builds.length; i++) {
            model.addElement(builds[i].getRevisionTitle());
        }

        listBuilds.setModel(model);

        getSteps(builds[0]);
    }

    private void getSteps(Build build) {
        BuildStep[] steps = dataSource.getStepsForBuild(build);

        DefaultListModel<String> model = new DefaultListModel<String>();

        for (int i=0; i < steps.length; i++) {
            model.addElement(steps[i].getStepName());
        }

        listSteps.setModel(model);
    }
}

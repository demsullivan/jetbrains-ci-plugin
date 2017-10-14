package ui.ciToolWindow;

import data.sources.*;
import data.structures.*;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBList;
import com.intellij.ui.content.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CiToolWindowFactory implements ToolWindowFactory {
    private ToolWindow ciToolWindow;
    private JPanel ciToolWindowContent;
    private JBList<Build> listBuilds;
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

        addSplitterToToolWindow();
        attachEventListeners();

        toolWindow.getContentManager().addContent(content);

        getBuilds();
    }

    private void addSplitterToToolWindow() {
        ciToolWindowContent.add(createSplitter());
    }

    private JComponent createSplitter() {
        splitter = new JBSplitter();

        listBuilds = new JBList<Build>();
        listSteps = new JBList<String>();

        splitter.setFirstComponent(listBuilds);
        splitter.setSecondComponent(listSteps);

        return splitter;
    }

    private void attachEventListeners() {
        listBuilds.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                CiToolWindowFactory.this.getSteps(listBuilds.getModel().getElementAt(e.getFirstIndex()));
            }
        });
    }

    private void getBuilds() {
        Build[] builds = dataSource.getBuilds();

        DefaultListModel<Build> model = new DefaultListModel<Build>();

        for (int i=0; i < builds.length; i++) {
            model.addElement(builds[i]);
        }

        listBuilds.setModel(model);

        getSteps(builds[0]);
    }

    private void getSteps(Build build) {
        BuildStep[] steps = dataSource.getStepsForBuild(build);

        DefaultListModel<String> model = new DefaultListModel<String>();

        for (int i=0; i < steps.length; i++) {
            model.addElement(build.getRevision() + ": " + steps[i].getStepName());
        }

        listSteps.setModel(model);
    }
}

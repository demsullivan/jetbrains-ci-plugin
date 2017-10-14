package ui.ciToolWindow;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import data.sources.*;
import data.structures.*;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBList;
import com.intellij.ui.content.*;
import ui.ciToolWindow.table.CiBuildTable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;

public class CiToolWindowFactory implements ToolWindowFactory {
    private ToolWindow ciToolWindow;
    private JPanel ciToolWindowContent;
    private CiBuildTable listBuilds;
    private JBList<String> listSteps;
    private JBSplitter splitter;

    private JBScrollPane paneBuilds;
    private JBScrollPane paneSteps;

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

        listBuilds = new CiBuildTable();
        paneBuilds = new JBScrollPane(listBuilds);

        listSteps = new JBList<String>();
        paneSteps = new JBScrollPane(listSteps);

        paneSteps.setVisible(false);

        splitter.setFirstComponent(paneBuilds);
        splitter.setSecondComponent(paneSteps);

        splitter.setResizeEnabled(true);

        return splitter;
    }

    private void attachEventListeners() {
        listBuilds.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                CiBuildTableModel model = (CiBuildTableModel)listBuilds.getModel();
                CiToolWindowFactory.this.getSteps(model.getBuildAt(e.getFirstIndex()));
            }
        });
    }

    private void getBuilds() {
        Build[] builds = dataSource.getBuilds();

        CiBuildTableModel model = new CiBuildTableModel(builds);
        listBuilds.setModel(model);
    }

    private void getSteps(Build build) {

        paneSteps.setVisible(true);

        BuildStep[] steps = dataSource.getStepsForBuild(build);

        DefaultListModel<String> model = new DefaultListModel<String>();

        for (int i=0; i < steps.length; i++) {
            model.addElement(build.getRevision() + ": " + steps[i].getStepName());
        }

        listSteps.setModel(model);
    }
}

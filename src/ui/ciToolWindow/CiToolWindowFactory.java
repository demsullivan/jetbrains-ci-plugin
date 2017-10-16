package ui.ciToolWindow;

import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.ui.components.JBScrollPane;
import data.sources.*;
import data.structures.*;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBList;
import com.intellij.ui.content.*;
import ui.ciToolWindow.table.CiBuildStepTable;
import ui.ciToolWindow.table.CiBuildStepTableModel;
import ui.ciToolWindow.table.CiBuildTable;
import ui.ciToolWindow.table.CiBuildTableModel;
import ui.settings.CiOptionsProvider;
import ui.settings.CiSettingsChangeNotifier;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CiToolWindowFactory implements ToolWindowFactory {
    private ToolWindow ciToolWindow;
    private JPanel ciToolWindowContent;

    private CiBuildTable listBuilds;
    private JBScrollPane paneBuilds;

    private CiBuildStepTable listSteps;
    private JBScrollPane paneSteps;

    private JBSplitter splitter;

    private JButton btnRefresh;

    private Source dataSource;

    private CiOptionsProvider myOptionsProvider;

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        ciToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(ciToolWindowContent, "", false);

        addToolbarToToolWindow();
        addSplitterToToolWindow();
        attachEventListeners();

        toolWindow.getContentManager().addContent(content);

        myOptionsProvider = CiOptionsProvider.getInstance(project);

        refreshData();

        project.getMessageBus().connect().subscribe(CiSettingsChangeNotifier.CI_SETTINGS_CHANGE_TOPIC, new CiSettingsChangeNotifier() {
            @Override
            public void beforeAction(CiOptionsProvider options) {
                return;
            }

            @Override
            public void afterAction(CiOptionsProvider options) {
                CiToolWindowFactory.this.updateOptionsProvider(options);
            }
        });


    }

    public void updateOptionsProvider(CiOptionsProvider options) {
        myOptionsProvider = options;
        refreshData();
    }

    private void addToolbarToToolWindow() {
        JToolBar toolbar = new JToolBar();

        btnRefresh = new JButton();
        btnRefresh.setText("Refresh");

        btnRefresh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CiToolWindowFactory.this.refreshData();
            }
        });

        toolbar.add(btnRefresh);

        ciToolWindowContent.add(toolbar, BorderLayout.PAGE_START);
    }

    private void addSplitterToToolWindow() {
        ciToolWindowContent.add(createSplitter(), BorderLayout.CENTER);
    }

    private JComponent createSplitter() {
        splitter = new JBSplitter();

        listBuilds = new CiBuildTable();
        paneBuilds = new JBScrollPane(listBuilds);

        listSteps = new CiBuildStepTable();
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

    private void refreshData() {
        if (myOptionsProvider.hasApiKey() && myOptionsProvider.hasProjectName()) {
            dataSource = SourceFactory.getSource(myOptionsProvider);
            getBuilds();
        }
    }

    private void getBuilds() {
        Build[] builds = dataSource.getBuilds();

        CiBuildTableModel model = new CiBuildTableModel(builds);
        listBuilds.setModel(model);
    }

    private void getSteps(Build build) {

        paneSteps.setVisible(true);

        BuildStep[] steps = dataSource.getStepsForBuild(build);

        CiBuildStepTableModel model = new CiBuildStepTableModel(steps);
        listSteps.setModel(model);
    }
}

package ciToolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.*;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBList;
import com.intellij.ui.content.*;

import javax.swing.*;

public class CiToolWindowFactory implements ToolWindowFactory {
    private ToolWindow ciToolWindow;
    private JPanel ciToolWindowContent;
    private JList listBuilds;
    private JList listSteps;
    private JBSplitter splitter;

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        ciToolWindow = toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(ciToolWindowContent, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private void createUIComponents() {
        listBuilds = new JBList();
        listSteps = new JBList();

        splitter.setFirstComponent(listBuilds);
        splitter.setSecondComponent(listSteps);
    }
}

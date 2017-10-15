package ui.ciToolWindow.table;

import com.intellij.icons.AllIcons;
import data.structures.BuildResult;
import data.structures.BuildStep;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CiBuildStepTableModel extends DefaultTableModel {
    private BuildStep[] mySteps;
    private static String[] columnNames = {
            "Icon",
            "Step Name"
    };

    public CiBuildStepTableModel(BuildStep[] steps) {
        mySteps = steps;
        columnIdentifiers = convertToVector(columnNames);

        for (int i=0; i < steps.length; i++) {
            Object[] data = {
                    getBuildStepIcon(steps[i].getStatus()),
                    steps[i].getStepName()
            };
            addRow(data);
        }
    }

    public Class<?> getColumnClass(int column) {
        if (column == 0) {
            return Icon.class;
        } else {
            return String.class;
        }
    }

    public boolean isCellEditable(int row, int column) { return false; }

    private Icon getBuildStepIcon(int status) {
        switch(status) {
            case BuildResult.Success:
                return AllIcons.Process.State.GreenOK;
            case BuildResult.Failed:
                return AllIcons.Process.State.RedExcl;
            default:
                return AllIcons.Process.State.YellowStr;
        }
    }
}

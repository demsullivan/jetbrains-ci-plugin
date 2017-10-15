package ui.ciToolWindow.table;

import com.intellij.icons.AllIcons;
import data.structures.Build;
import data.structures.BuildResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CiBuildTableModel extends DefaultTableModel {
    private Build[] builds;
    private static String[] columnNames = {
            "Icon",
            "Build #",
            "Branch",
            "Rev #",
            "Subject",
            "Committer"
    };

    public CiBuildTableModel(Build[] myBuilds) {
        builds = myBuilds;
        columnIdentifiers = convertToVector(columnNames);

        for (int i=0; i < builds.length; i++) {
            Object[] data = {
                    getBuildResultIcon(builds[i].getBuildResult()),
                    builds[i].getBuildNum(),
                    builds[i].getBranch(),
                    builds[i].getRevision(),
                    builds[i].getRevisionTitle(),
                    builds[i].getCommitter()
            };
            addRow(data);
        }
    }

    public Class<?> getColumnClass(int column) {
        switch(column) {
            case 0: return Icon.class;
            default: return String.class;
        }
    }

    public Build getBuildAt(int row) {
        return builds[row];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private Icon getBuildResultIcon(int result) {
        switch(result) {
            case BuildResult.Success:
                return AllIcons.Process.State.GreenOK;
            case BuildResult.Failed:
                return AllIcons.Process.State.RedExcl;
            default:
                return AllIcons.Process.State.YellowStr;
        }
    }


}

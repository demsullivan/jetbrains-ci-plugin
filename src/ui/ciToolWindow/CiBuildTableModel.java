package ui.ciToolWindow;

import data.structures.Build;
import javax.swing.table.DefaultTableModel;

public class CiBuildTableModel extends DefaultTableModel {
    private Build[] builds;

    public CiBuildTableModel(Build[] myBuilds) {
        builds = myBuilds;

        String[] columnNames = {"Icon", "Subject", "Committer"};
        columnIdentifiers = convertToVector(columnNames);

        for (int i=0; i < builds.length; i++) {
            Object[] data = {builds[i].getBuildResult(), builds[i].getRevisionTitle(), builds[i].getCommitter()};
            addRow(data);
        }
    }


}

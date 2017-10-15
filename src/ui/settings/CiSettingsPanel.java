package ui.settings;

import data.sources.Source;
import data.sources.SourceFactory;

import com.intellij.ui.components.JBList;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CiSettingsPanel {
    private JRadioButton myRdProviderCircle;
    private JRadioButton myRdProviderHeroku;
    private JRadioButton myRdProviderTravis;
    private JTextField myTxApiKey;
    private JButton myBtTestConnection;
    private JLabel myLbConnectionStatus;
    private JBList<String> myProjectsList;
    private JPanel myWholePanel;

    private CiOptionsProvider myOptionsProvider;

    public JComponent createPanel(CiOptionsProvider provider) {
        myOptionsProvider = provider;

        myBtTestConnection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CiSettingsPanel.this.testConnection();
            }
        });
        return myWholePanel;
    }

    public Boolean isModified() {
        return myOptionsProvider.getService() != getProviderName() ||
                myOptionsProvider.getApiKey() != myTxApiKey.getText() ||
                myOptionsProvider.getProjectName() != myProjectsList.getSelectedValue();
    }

    public void apply() {
        myOptionsProvider.setService("circleci");
        myOptionsProvider.setApiKey(myTxApiKey.getText());
        myOptionsProvider.setProjectName(myProjectsList.getSelectedValue());
    }

    public void reset() {
        switch(myOptionsProvider.getService()) {
            case "circleci":
                myRdProviderCircle.setSelected(true);
                break;
            case "heroku":
                myRdProviderHeroku.setSelected(true);
                break;
            case "travis":
                myRdProviderTravis.setSelected(true);
                break;
        }

        myTxApiKey.setText(myOptionsProvider.getApiKey());
    }

    private String getProviderName() {
        return "circleci";
    }

    private void testConnection() {
        Source source = SourceFactory.getSource(getProviderName(), myTxApiKey.getText());
        String[] projects = source.getProjects();

        myLbConnectionStatus.setText("Connection success!");
        DefaultListModel<String> model = new DefaultListModel<String>();

        for (int i=0; i < projects.length; i++) {
            model.addElement(projects[i]);
        }

        myProjectsList.setModel(model);
    }
}

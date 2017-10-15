package ui.ciToolWindow.table;

import com.intellij.ide.BrowserUtil;
import utils.Hyperlink;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

public class CiBuildTable extends ResizableHeaderlessTable {

    public CiBuildTable() {
        setTableHeader(new InvisibleResizableHeader());
        setShowHorizontalLines(false);
        setShowVerticalLines(false);

        setDefaultRenderer(Hyperlink.class, new HyperlinkTableCellRenderer());

        getColumnModel().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int row = CiBuildTable.this.getSelectedRow();

                if (row >= 0 && e.getFirstIndex() >= 0) {
                    Object value = CiBuildTable.this.getValueAt(row, e.getFirstIndex());
                    if (value.getClass().getName().equals("utils.Hyperlink")) {
                        Hyperlink link = (Hyperlink) value;
                        BrowserUtil.browse(link.getUrl());
                    }
                }
            }
        });
    }

    public void setModel(TableModel model) {
        super.setModel(model);
        TableColumnModel columnModel = getColumnModel();

        for (int i=0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            String header = (String)column.getHeaderValue();

            if (header.equals("Icon")) {
                column.setPreferredWidth(50);
            } else if (header.equals("Build #") || header.equals("Branch") || header.equals("Rev #")) {
                column.setPreferredWidth(100);
            } else {
                column.setPreferredWidth(400);
            }

        }
    }
}

package ui.ciToolWindow.table;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class CiBuildTable extends ResizableHeaderlessTable {

    public CiBuildTable() {
        setTableHeader(new InvisibleResizableHeader());
        setShowHorizontalLines(false);
        setShowVerticalLines(false);
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

package ui.ciToolWindow.table;

import com.intellij.ui.table.JBTable;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class CiBuildStepTable extends ResizableHeaderlessTable {

    public CiBuildStepTable() {
        setTableHeader(new InvisibleResizableHeader());
        setShowHorizontalLines(false);
        setShowVerticalLines(false);
        setAutoResizeMode(AUTO_RESIZE_OFF);
    }

    public void setModel(TableModel model) {
        super.setModel(model);

        TableColumnModel columnModel = getColumnModel();

        for (int i=0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            String header = (String)column.getHeaderValue();

            if (header.equals("Icon")) {
                column.setPreferredWidth(5);
            } else {
                column.setPreferredWidth(1000);
            }
        }
    }
}

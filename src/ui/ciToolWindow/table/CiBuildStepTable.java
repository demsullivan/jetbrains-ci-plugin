package ui.ciToolWindow.table;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class CiBuildStepTable extends ResizableHeaderlessTable {

    public CiBuildStepTable() {
        setTableHeader(new InvisibleResizableHeader());
        setShowHorizontalLines(false);
        setShowVerticalLines(false);
    }

    public void setModel(TableModel model) {
        super.setModel(model);
    }
}

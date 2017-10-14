package ui.ciToolWindow.table;

import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicTableHeaderUI;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseEvent;

import static com.intellij.vcs.log.ui.table.GraphTableModel.ROOT_COLUMN;

public class ResizableHeaderlessTable extends JBTable {
    // much of this was taken from intellij-community
    // see: https://github.com/JetBrains/intellij-community/blob/master/platform/vcs-log/impl/src/com/intellij/vcs/log/ui/table/VcsLogGraphTable.java
    private static class EmptyTableCellRenderer implements TableCellRenderer {
        @NotNull
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setMaximumSize(new Dimension(0, 0));
            return panel;
        }
    }

    private static class MyBasicTableHeaderUI extends BasicTableHeaderUI implements MouseInputListener {
        private int myStartXCoordinate = 0;
        private int myStartYCoordinate = 0;

        public MyBasicTableHeaderUI(@NotNull JTableHeader tableHeader) {
          header = tableHeader;
          mouseInputListener = createMouseInputListener();
        }

        @NotNull
        private MouseEvent convertMouseEvent(@NotNull MouseEvent e) {
          // create a new event, almost exactly the same, but in the header
          return new MouseEvent(e.getComponent(), e.getID(), e.getWhen(), e.getModifiers(), e.getX(), 0, e.getXOnScreen(), header.getY(),
                                e.getClickCount(), e.isPopupTrigger(), e.getButton());
        }

        @Override
        public void mouseClicked(@NotNull MouseEvent e) {
        }

        @Override
        public void mousePressed(@NotNull MouseEvent e) {
          if (isOnBorder(e) || isOnRootColumn(e)) return;
          myStartXCoordinate = e.getX();
          myStartYCoordinate = e.getY();
          mouseInputListener.mousePressed(convertMouseEvent(e));
        }

        @Override
        public void mouseReleased(@NotNull MouseEvent e) {
          mouseInputListener.mouseReleased(convertMouseEvent(e));
          if (header.getCursor() == Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)) {
            header.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
          }
        }

        @Override
        public void mouseEntered(@NotNull MouseEvent e) {
          mouseInputListener.mouseEntered(convertMouseEvent(e));
        }

        @Override
        public void mouseExited(@NotNull MouseEvent e) {
          mouseInputListener.mouseExited(convertMouseEvent(e));
        }

        @Override
        public void mouseDragged(@NotNull MouseEvent e) {
          if (!isDraggingEnabled(e)) {
            return;
          }

          mouseInputListener.mouseDragged(convertMouseEvent(e));
          // if I change cursor on mouse pressed, it will change on double-click as well
          // and I do not want that
          if (header.getDraggedColumn() != null) {
            if (header.getCursor() == Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)) {
              header.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
            int draggedColumn = header.getTable().convertColumnIndexToView(header.getDraggedColumn().getModelIndex());
            if (header.getTable().convertColumnIndexToView(ROOT_COLUMN) == draggedColumn + (header.getDraggedDistance() < 0 ? -1 : 1)) {
              mouseReleased(e); //cancel dragging to the root column
            }
          }
        }

        private boolean isDraggingEnabled(@NotNull MouseEvent e) {
          if (isOnBorder(e) || isOnRootColumn(e)) return false;
          // can not check for getDragged/Resized column here since they can be set in mousePressed method
          // their presence does not necessarily means something is being dragged or resized
          if (header.getCursor() == Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR) ||
              header.getCursor() == Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR)) {
            return true;
          }

          int deltaX = Math.abs(e.getX() - myStartXCoordinate);
          int deltaY = Math.abs(e.getY() - myStartYCoordinate);
          Point point = new Point(Math.min(Math.max(e.getX(), 0), header.getTable().getWidth() - 1), e.getY());
          boolean sameColumn;
          if (header.getDraggedColumn() == null) {
            sameColumn = true;
          }
          else {
            sameColumn = (header.getTable().getColumnModel().getColumn(header.getTable().columnAtPoint(point)) ==
                          header.getDraggedColumn());
          }
          // start dragging only if mouse moved horizontally
          // or if dragging was already started earlier (it looks weird to stop mid-dragging)
          return deltaX >= 3 * deltaY && sameColumn;
        }

        @Override
        public void mouseMoved(@NotNull MouseEvent e) {
          if (isOnBorder(e)) return;
          mouseInputListener.mouseMoved(convertMouseEvent(e));
        }

        public boolean isOnBorder(@NotNull MouseEvent e) {
          return Math.abs(header.getTable().getWidth() - e.getPoint().x) <= JBUI.scale(3);
        }

        public boolean isOnRootColumn(@NotNull MouseEvent e) {
          return header.getTable().getColumnModel().getColumnIndexAtX(e.getX()) == ROOT_COLUMN;
        }
    }


    protected class InvisibleResizableHeader extends JBTable.JBTableHeader {
        @NotNull
        private final MyBasicTableHeaderUI myHeaderUI;
        @Nullable
        private Cursor myCursor = null;

        public InvisibleResizableHeader() {
            myHeaderUI = new MyBasicTableHeaderUI(this);
            // need a header to resize/drag columns, so use header that is not visible
            setDefaultRenderer(new EmptyTableCellRenderer());
            setReorderingAllowed(true);
        }

        @Override
        public void setTable(JTable table) {
            JTable oldTable = getTable();
            if (oldTable != null) {
                oldTable.removeMouseListener(myHeaderUI);
                oldTable.removeMouseMotionListener(myHeaderUI);
            }

            super.setTable(table);

            if (table != null) {
                table.addMouseListener(myHeaderUI);
                table.addMouseMotionListener(myHeaderUI);
            }
        }

        @Override
        public void setCursor(@Nullable Cursor cursor) {
          /* this method and the next one fixes cursor:
             BasicTableHeaderUI.MouseInputHandler behaves like nobody else sets cursor
             so we remember what it set last time and keep it unaffected by other cursor changes in the table
           */
            JTable table = getTable();
            if (table != null) {
                table.setCursor(cursor);
                myCursor = cursor;
            } else {
                super.setCursor(cursor);
            }
        }

        @Override
        public Cursor getCursor() {
            if (myCursor == null) {
                JTable table = getTable();
                if (table == null) return super.getCursor();
                return table.getCursor();
            }
            return myCursor;
        }

        @NotNull
        @Override
        public Rectangle getHeaderRect(int column) {
            // if a header has zero height, mouse pointer can never be inside it, so we pretend it is one pixel high
            Rectangle headerRect = super.getHeaderRect(column);
            return new Rectangle(headerRect.x, headerRect.y, headerRect.width, 1);
        }
    }
}

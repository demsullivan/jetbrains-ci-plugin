package ui.ciToolWindow.table;

import com.intellij.ide.BrowserUtil;
import javafx.scene.control.TableCell;
import utils.Hyperlink;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class HyperlinkTableCellRenderer extends JLabel implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column)
        {
            Font font = getFont();
            Hyperlink link = (Hyperlink)value;

            Map<TextAttribute, Integer> fontAttributes = new HashMap<TextAttribute, Integer>();
            fontAttributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            fontAttributes.put(TextAttribute.BACKGROUND, null);

            setText(link.getLabel());

            if (!isSelected) {
                setForeground(table.getSelectionBackground());
                setBackground(table.getSelectionForeground());
            } else {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            }

            setFont(new Font(font.getFamily(), Font.PLAIN, font.getSize()).deriveFont(fontAttributes));
            setOpaque(true);

            return this;
        }
}

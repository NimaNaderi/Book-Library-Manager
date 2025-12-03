import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

class ThemeManager {
    public static final Color DARK_BG = new Color(33, 33, 33);
    public static final Color DARK_PANEL = new Color(45, 45, 45);
    public static final Color DARK_FG = new Color(230, 230, 230);
    public static final Color DARK_INPUT = new Color(60, 60, 60);
    public static final Color DARK_HEADER = new Color(55, 55, 55);

    public static final Color LIGHT_BG = new Color(240, 242, 245);
    public static final Color LIGHT_PANEL = new Color(255, 255, 255);
    public static final Color LIGHT_FG = new Color(30, 30, 30);
    public static final Color LIGHT_INPUT = new Color(255, 255, 255);
    public static final Color LIGHT_HEADER = new Color(220, 220, 220);

    public static final Color ACCENT_BLUE = new Color(0, 120, 215);
    public static final Color ACCENT_RED = new Color(220, 53, 69);
    public static final Color ACCENT_GREEN = new Color(40, 167, 69);
    public static final Color ACCENT_ORANGE = new Color(255, 152, 0);

    public static void applyTheme(Container container, boolean isDark) {
        Color bg = isDark ? DARK_BG : LIGHT_BG;
        Color fg = isDark ? DARK_FG : LIGHT_FG;
        Color panelBg = isDark ? DARK_PANEL : LIGHT_PANEL;
        Color inputBg = isDark ? DARK_INPUT : LIGHT_INPUT;

        container.setBackground(bg);

        for (Component c : container.getComponents()) {
            if (c instanceof JPanel) {
                c.setBackground(panelBg);
                applyTheme((Container) c, isDark);
            }
            else if (c instanceof JLabel) {
                c.setForeground(fg);
            }
            else if (c instanceof JTextField) {
                c.setBackground(inputBg);
                c.setForeground(fg);
                ((JTextField) c).setCaretColor(fg);
                ((JTextField) c).setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(isDark ? Color.GRAY : Color.LIGHT_GRAY),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            }
            else if (c instanceof JCheckBox) {
                c.setBackground(panelBg);
                c.setForeground(fg);
            }
            else if (c instanceof JScrollPane) {
                c.setBackground(panelBg);
                applyTheme((Container) c, isDark);
            }
            else if (c instanceof JTable) {
                JTable table = (JTable) c;
                table.setBackground(panelBg);
                table.setForeground(fg);
                table.setGridColor(isDark ? new Color(80, 80, 80) : new Color(200, 200, 200));
                table.setSelectionBackground(ACCENT_BLUE);
                table.setSelectionForeground(Color.WHITE);

                JTableHeader header = table.getTableHeader();
                header.setBackground(isDark ? DARK_HEADER : LIGHT_HEADER);
                header.setForeground(fg);
                header.setBorder(BorderFactory.createLineBorder(isDark ? Color.GRAY : Color.LIGHT_GRAY));
            }
        }
    }
}
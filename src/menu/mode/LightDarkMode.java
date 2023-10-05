package menu.mode;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusStyle;

/**
 *
 * @author Raven
 */
public class LightDarkMode extends JPanel {

    public void setMenuFull(boolean menuFull) {
        this.menuFull = menuFull;
        if (menuFull) {
            buttonLight.setVisible(true);
            buttonDark.setVisible(true);
            buttonLighDark.setVisible(false);
        } else {
            buttonLight.setVisible(false);
            buttonDark.setVisible(false);
            buttonLighDark.setVisible(true);
        }
    }

    private boolean menuFull = true;

    public LightDarkMode() {
        init();
    }

    private void init() {
        setBorder(new EmptyBorder(2, 2, 2, 2));
        setLayout(new LightDarkModeLayout());
        setBorder(BorderFactory.createLineBorder(new Color(51,51,51)));
        setBackground(new Color(51,51,51));

        buttonLight = new JButton("Light", new FlatSVGIcon(getClass().getResource("/menu/mode/light.svg")));
        buttonDark = new JButton("Dark", new FlatSVGIcon(getClass().getResource("/menu/mode/dark.svg")));
        buttonLighDark = new JButton();
        buttonLighDark.setBackground(Color.RED);
        buttonLighDark.setForeground(Color.WHITE);
        buttonLighDark.setFocusPainted(false);
        buttonLighDark.setBorderPainted(false);
        buttonLighDark.setBorder(new EmptyBorder(0, 0, 0, 0));

        buttonLighDark.addActionListener((ActionEvent e) -> {
            changeMode(!FlatLaf.isLafDark());
        });
        checkStyle();
        buttonDark.addActionListener((ActionEvent e) -> {
            changeMode(true);
        });
        buttonLight.addActionListener((ActionEvent e) -> {
            changeMode(false);
        });

        add(buttonLight);
        add(buttonDark);
        add(buttonLighDark);
    }

    private void changeMode(boolean dark) {
        if (FlatLaf.isLafDark() != dark) {
            if (dark) {
                EventQueue.invokeLater(() -> {
                    FlatAnimatedLafChange.showSnapshot();
                    FlatMacDarkLaf.setup();
                    FlatLaf.updateUI();
                    checkStyle();
                    FlatAnimatedLafChange.hideSnapshotWithAnimation();
                });
            } else {
                EventQueue.invokeLater(() -> {
                    FlatAnimatedLafChange.showSnapshot();
                    FlatMacLightLaf.setup();
                    FlatLaf.updateUI();
                    checkStyle();
                    FlatAnimatedLafChange.hideSnapshotWithAnimation();
                });
            }
        }
    }

    private void checkStyle() {
        boolean isDark = FlatLaf.isLafDark();
        addStyle(buttonLight, !isDark);
        addStyle(buttonDark, isDark);
        if (isDark) {
            buttonLighDark.setIcon(new FlatSVGIcon("/menu/mode/dark.svg"));
        } else {
            buttonLighDark.setIcon(new FlatSVGIcon("/menu/mode/light.svg"));
        }
    }

    private void addStyle(JButton button, boolean style) {
        if (style) {
            button.setBackground(new Color(240, 240, 240));
            button.setForeground(new Color(49, 62, 74));

            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setFont(new Font("sansserif", Font.BOLD, 11));
        } else {
            button.setBackground(null); // Xóa màu nền
            button.setForeground(new Color(240, 240, 240));

            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setFont(new Font("sansserif", Font.BOLD, 11));
        }

    }

    private JButton buttonLight;
    private JButton buttonDark;
    private JButton buttonLighDark;

    private class LightDarkModeLayout implements LayoutManager {

        @Override
        public void addLayoutComponent(String name, Component comp) {
        }

        @Override
        public void removeLayoutComponent(Component comp) {
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(5, buttonDark.getPreferredSize().height + (menuFull ? 0 : 5));
            }
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                return new Dimension(0, 0);
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int x = insets.left;
                int y = insets.top;
                int gap = 5;
                int width = parent.getWidth() - (insets.left + insets.right);
                int height = parent.getHeight() - (insets.top + insets.bottom);
                int buttonWidth = (width - gap) / 2;
                if (menuFull) {
                    buttonLight.setBounds(x, y, buttonWidth, height);
                    buttonDark.setBounds(x + buttonWidth + gap, y, buttonWidth, height);
                } else {
                    buttonLighDark.setBounds(x, y, width, height);
                }
            }
        }
    }
}

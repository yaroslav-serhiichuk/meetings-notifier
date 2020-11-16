package org.home.notifier.ui;

import org.home.notifier.ui.buttons.ExitButton;
import org.home.notifier.ui.buttons.FinishMeetingButton;
import org.home.notifier.ui.buttons.IndicatorOffButton;
import org.home.notifier.ui.buttons.StartMeetingButton;
import org.home.notifier.ui.labels.CurrentStatusTitleLabel;
import org.home.notifier.ui.labels.CurrentStatusValueLabel;
import org.home.notifier.ui.service.UIComponent;

import javax.swing.*;
import java.awt.*;

import static org.home.notifier.ui.utils.ResourceImageReader.getImage;

public class MainFrame extends JFrame implements UIComponent {

    private static final int FRAME_WIDTH = 280;
    private static final int FRAME_HEIGHT = 320;

    public MainFrame() {
        super();
        initialize();
    }

    @Override
    public void initialize() {
        this.setTitle("Meetings Notifier");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.getContentPane().setLayout(null);
        var contentPane = this.getContentPane();
        contentPane.add(new CurrentStatusTitleLabel());
        contentPane.add(new CurrentStatusValueLabel());
        contentPane.add(new StartMeetingButton());
        contentPane.add(new FinishMeetingButton());
        contentPane.add(new IndicatorOffButton());
        contentPane.add(new ExitButton());
        var bounds = getScreenBounds();
        int x = (int) bounds.getMaxX() - this.getWidth() - 10;
        int y = (int) bounds.getMaxY() - this.getHeight() - 50;
        this.setLocation(x, y);
        defineCollaborationWithSystemTray();
        this.setVisible(true);
    }

    private Rectangle getScreenBounds() {
        var graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var defaultScreen = graphicsEnvironment.getDefaultScreenDevice();
        return defaultScreen.getDefaultConfiguration().getBounds();
    }

    private void defineCollaborationWithSystemTray() {
        SystemTray systemTray = SystemTray.getSystemTray();
        PopupMenu trayPopupMenu = getSystemTrayPopupMenu();
        TrayIcon trayIcon = new TrayIcon(getImage("/icons/bulb-green.png"), "Meetings Notifier", trayPopupMenu);
        trayIcon.setImageAutoSize(true);
        addWindowStateListener(e -> {
            if (e.getNewState() == ICONIFIED) {
                try {
                    systemTray.add(trayIcon);
                    setVisible(false);
                } catch (AWTException ex) {
                    // ToDo Handle it
                    throw new RuntimeException("Fail to add to system tray");
                }
            }
            if (e.getNewState() == NORMAL) {
                systemTray.remove(trayIcon);
                setVisible(true);
            }
        });
        setIconImage(getImage("/icons/bulb-green.png"));
        setVisible(true);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    private PopupMenu getSystemTrayPopupMenu() {
        PopupMenu popupMenu = new PopupMenu();
        MenuItem menuItem = new MenuItem("Open");
        menuItem.addActionListener(e -> {
            setVisible(true);
            setExtendedState(JFrame.NORMAL);
        });
        popupMenu.add(menuItem);
        menuItem = new MenuItem("Exit");
        menuItem.addActionListener(e -> System.exit(0));
        popupMenu.add(menuItem);
        return popupMenu;
    }
}

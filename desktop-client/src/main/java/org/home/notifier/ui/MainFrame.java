package org.home.notifier.ui;

import org.home.notifier.indicator.IndicatorClient;
import org.home.notifier.ui.buttons.ExitButton;
import org.home.notifier.ui.buttons.FinishMeetingButton;
import org.home.notifier.ui.buttons.IndicatorOffButton;
import org.home.notifier.ui.buttons.StartMeetingButton;
import org.home.notifier.ui.labels.CurrentStatusTitleLabel;
import org.home.notifier.ui.labels.CurrentStatusValueLabel;
import org.home.notifier.ui.service.UIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import static org.home.notifier.ui.utils.ResourceImageReader.getImage;

public class MainFrame extends JFrame implements UIComponent {

    private static final int FRAME_WIDTH = 280;
    private static final int FRAME_HEIGHT = 320;

    private Image statusIcon;
    private final IndicatorClient indicatorClient;
    private TrayStateListener trayStateListener;

    public MainFrame() {
        super();
        this.statusIcon = getImage("/icons/bulb-red.png");
        this.indicatorClient = new IndicatorClient();
        initialize();
    }

    public class TrayStateListener implements WindowStateListener {

        private final SystemTray systemTray;
        private final TrayIcon trayIcon;

        private TrayStateListener(SystemTray systemTray, PopupMenu trayPopupMenu) {
            this.systemTray = systemTray;
            this.trayIcon = new TrayIcon(statusIcon, "Meetings Notifier", trayPopupMenu);
            trayIcon.setImageAutoSize(true);
        }

        @Override
        public void windowStateChanged(WindowEvent e) {
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
        }

        private void updateIcon() {
            trayIcon.setImage(statusIcon);
        }
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
        var currentStatusValueLabel = new CurrentStatusValueLabel();
        contentPane.add(currentStatusValueLabel);
        contentPane.add(new StartMeetingButton(this, currentStatusValueLabel, indicatorClient));
        contentPane.add(new FinishMeetingButton(this, currentStatusValueLabel, indicatorClient));
        contentPane.add(new IndicatorOffButton(this, currentStatusValueLabel, indicatorClient));
        contentPane.add(new ExitButton());
        var bounds = getScreenBounds();
        int x = (int) bounds.getMaxX() - this.getWidth() - 10;
        int y = (int) bounds.getMaxY() - this.getHeight() - 50;
        this.setLocation(x, y);
        this.setIconImage(statusIcon);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setVisible(true);
        defineCollaborationWithSystemTray();
    }

    private Rectangle getScreenBounds() {
        var graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        var defaultScreen = graphicsEnvironment.getDefaultScreenDevice();
        return defaultScreen.getDefaultConfiguration().getBounds();
    }

    private void defineCollaborationWithSystemTray() {
        SystemTray systemTray = SystemTray.getSystemTray();
        PopupMenu trayPopupMenu = getSystemTrayPopupMenu();
        trayStateListener = new TrayStateListener(systemTray, trayPopupMenu);
        this.addWindowStateListener(trayStateListener);
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

    public void updateTrayIcon() {
        trayStateListener.updateIcon();
    }

    public void setStatusIcon(Image statusIcon) {
        this.statusIcon = statusIcon;
    }
}

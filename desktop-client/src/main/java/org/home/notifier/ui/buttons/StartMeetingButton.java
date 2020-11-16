package org.home.notifier.ui.buttons;

import org.home.notifier.ui.service.UIComponent;

import javax.swing.*;
import java.awt.*;

public class StartMeetingButton extends JButton implements UIComponent {

    public StartMeetingButton() {
        super();
        initialize();
    }

    @Override
    public void initialize() {
        this.setText("Start meeting");
        var size = new Dimension(210, 40);
        this.setPreferredSize(size);
        this.setBounds(30, 70, size.width, size.height);
        this.setForeground(Color.BLUE);
    }
}

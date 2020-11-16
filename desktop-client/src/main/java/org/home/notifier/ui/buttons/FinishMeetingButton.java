package org.home.notifier.ui.buttons;

import org.home.notifier.ui.service.UIComponent;

import javax.swing.*;
import java.awt.*;

public class FinishMeetingButton extends JButton implements UIComponent {

    public FinishMeetingButton() {
        super();
        initialize();
    }

    @Override
    public void initialize() {
        this.setText("Finish Meeting");
        var size = new Dimension(210, 40);
        this.setPreferredSize(size);
        this.setBounds(30, 120, size.width, size.height);
        this.setForeground(Color.RED);
    }
}

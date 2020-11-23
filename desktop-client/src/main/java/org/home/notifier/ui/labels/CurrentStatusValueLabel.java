package org.home.notifier.ui.labels;

import org.home.notifier.common.MeetingState;
import org.home.notifier.ui.service.UIComponent;

import javax.swing.*;
import java.awt.*;

public class CurrentStatusValueLabel extends JLabel implements UIComponent {

    public CurrentStatusValueLabel() {
        super();
        initialize();
    }

    @Override
    public void initialize() {

        // ToDo change logic
        this.setText(MeetingState.UNKNOWN.toString());
        this.setForeground(MeetingState.UNKNOWN.getColor());

        var size = new Dimension(150, 15);
        this.setPreferredSize(size);
        this.setBounds(140, 30, size.width, size.height);
        this.setFont(this.getFont().deriveFont(14.0f));
    }
}

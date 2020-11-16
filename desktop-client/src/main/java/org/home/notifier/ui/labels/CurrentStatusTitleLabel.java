package org.home.notifier.ui.labels;

import org.home.notifier.ui.service.UIComponent;

import javax.swing.*;
import java.awt.*;

public class CurrentStatusTitleLabel extends JLabel implements UIComponent {

    public CurrentStatusTitleLabel() {
        super();
        initialize();
    }

    @Override
    public void initialize() {
        this.setText("Current Status:");
        var size = new Dimension(110, 15);
        this.setPreferredSize(size);
        this.setBounds(30, 30, size.width, size.height);
        this.setFont(this.getFont().deriveFont(14.0f));
    }
}

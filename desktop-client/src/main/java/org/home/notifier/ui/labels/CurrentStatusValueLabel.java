package org.home.notifier.ui.labels;

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
        var size = new Dimension(150, 15);
        this.setPreferredSize(size);
        this.setBounds(140, 30, size.width, size.height);
        this.setFont(this.getFont().deriveFont(14.0f));
    }
}

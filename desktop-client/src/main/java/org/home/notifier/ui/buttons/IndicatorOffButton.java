package org.home.notifier.ui.buttons;

import org.home.notifier.ui.service.UIComponent;

import javax.swing.*;
import java.awt.*;

public class IndicatorOffButton extends JButton implements UIComponent {

    public IndicatorOffButton() {
        super();
        initialize();
    }

    @Override
    public void initialize() {
        this.setText("Indicator Off");
        var size = new Dimension(210, 40);
        this.setPreferredSize(size);
        this.setBounds(30, 170, size.width, size.height);
    }
}

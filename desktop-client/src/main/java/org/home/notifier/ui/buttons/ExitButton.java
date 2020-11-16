package org.home.notifier.ui.buttons;

import org.home.notifier.ui.service.UIComponent;

import javax.swing.*;
import java.awt.*;

public class ExitButton extends JButton implements UIComponent {

    public ExitButton() {
        super();
        initialize();
    }

    @Override
    public void initialize() {
        this.setText("Exit");
        var size = new Dimension(210, 40);
        this.setPreferredSize(size);
        this.setBounds(30, 220, size.width, size.height);
    }
}

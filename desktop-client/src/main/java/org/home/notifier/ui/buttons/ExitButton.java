package org.home.notifier.ui.buttons;

import org.home.notifier.ui.service.UIComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitButton extends JButton implements UIComponent {

    public ExitButton() {
        super();
        this.addActionListener(new ButtonActionListener());
        initialize();
    }

    private static class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    @Override
    public void initialize() {
        this.setText("Exit");
        var size = new Dimension(210, 40);
        this.setPreferredSize(size);
        this.setBounds(30, 220, size.width, size.height);
    }
}

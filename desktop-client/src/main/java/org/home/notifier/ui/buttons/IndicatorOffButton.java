package org.home.notifier.ui.buttons;

import org.home.notifier.common.MeetingState;
import org.home.notifier.indicator.IndicatorClient;
import org.home.notifier.ui.MainFrame;
import org.home.notifier.ui.labels.CurrentStatusValueLabel;
import org.home.notifier.ui.service.UIComponent;

import java.awt.*;

public class IndicatorOffButton extends ActionButton implements UIComponent {

    public IndicatorOffButton(final MainFrame mainFrame,
                              final CurrentStatusValueLabel currentStatusValueLabel,
                              final IndicatorClient indicatorClient) {

        super(MeetingState.OFF, mainFrame, currentStatusValueLabel, indicatorClient);
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

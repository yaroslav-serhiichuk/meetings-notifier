package org.home.notifier.ui.buttons;

import org.home.notifier.common.MeetingState;
import org.home.notifier.indicator.IndicatorClient;
import org.home.notifier.ui.MainFrame;
import org.home.notifier.ui.labels.CurrentStatusValueLabel;
import org.home.notifier.ui.service.UIComponent;

import java.awt.*;

public class StartMeetingButton extends ActionButton implements UIComponent {

    public StartMeetingButton(final MainFrame mainFrame,
                              final CurrentStatusValueLabel currentStatusValueLabel,
                              final IndicatorClient indicatorClient) {

        super(MeetingState.MEETING_STARTED, mainFrame, currentStatusValueLabel, indicatorClient);
        initialize();
    }

    @Override
    public void initialize() {
        this.setText("Start meeting");
        var size = new Dimension(210, 40);
        this.setPreferredSize(size);
        this.setBounds(30, 70, size.width, size.height);
        this.setForeground(Color.RED);
    }
}

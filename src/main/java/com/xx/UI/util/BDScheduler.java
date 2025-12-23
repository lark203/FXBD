package com.xx.UI.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Objects;

public class BDScheduler {
    private final Timeline timeline;
    private int maxRuns = 1;

    public BDScheduler(Runnable task,Integer millis) {
        Objects.requireNonNull(task);
        Objects.requireNonNull(millis);
        if (millis <= 0)
            throw new IllegalArgumentException("Millis必须为正数");
        timeline = new Timeline(new KeyFrame(Duration.millis(millis),_-> task.run()));
    }

    public void run() {
        maxRuns = 1;
        startTimeline();
    }

    public void run(int n) {
        if (n <= 0) return;
        maxRuns = n;
        startTimeline();
    }

    public void stop() {
        timeline.stop();
    }

    private void startTimeline() {
        timeline.stop();
        timeline.setCycleCount(maxRuns);
        timeline.playFromStart();
    }
    public void dispose() {
        timeline.stop();
        timeline.getKeyFrames().clear();
    }
}

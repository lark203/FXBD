package com.xx.UI.complex.textArea.view;

import com.xx.UI.complex.textArea.content.BDTextAreaContent;
import com.xx.UI.util.BDMapping;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.*;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ListView;
import javafx.util.Duration;

import java.lang.ref.WeakReference;

public class BDTextAreaListView extends ListView<Object> {
    protected final BDMapping mapping = new BDMapping();
    final BDTextAreaContent content;
    final BDTextArea textArea;
    final SimpleBooleanProperty caretBlink = new SimpleBooleanProperty();
    final SimpleBooleanProperty caretVisible = new SimpleBooleanProperty(true);
    BDTextAreaContent.Point selectStart = null;
    final DoubleProperty verticalScroll = new SimpleDoubleProperty(0);
    final DoubleProperty horizontalScroll = new SimpleDoubleProperty(0);
    final DoubleProperty horizontalScrollMax= new SimpleDoubleProperty(0);

    public BDTextAreaListView(BDTextArea textArea, BDTextAreaContent content) {
        this.content = content;
        this.textArea = textArea;
        setCellFactory(_ -> new BDTextCell(this));
    }

    public double getHorizontalScroll() {
        return horizontalScroll.get();
    }

    public ReadOnlyDoubleProperty horizontalScrollProperty() {
        return horizontalScroll;
    }

    public double getVerticalScroll() {
        return verticalScroll.get();
    }

    public ReadOnlyDoubleProperty verticalScrollProperty() {
        return verticalScroll;
    }
    public IndexedCell<?> getFirstVisibleCell() {
        return getSkin() == null? null : ((BDTextAreaListViewSKin) getSkin()).firstVisibleCell();
    }
    public IndexedCell<?> getLastVisibleCell() {
        return getSkin() == null? null : ((BDTextAreaListViewSKin) getSkin()).lastVisibleCell();
    }



    @Override
    protected BDTextAreaListViewSKin createDefaultSkin() {
        return new BDTextAreaListViewSKin(this);
    }

    static final class CaretBlinking {
        private final Timeline caretTimeline;
        private final WeakReference<BooleanProperty> blinkPropertyRef;

        public CaretBlinking(BooleanProperty var1, BDMapping mapping) {
            this.blinkPropertyRef = new WeakReference<>(var1);
            this.caretTimeline = new Timeline();
            this.caretTimeline.setCycleCount(-1);
            this.caretTimeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO, (_) -> this.setBlink(true)),
                    new KeyFrame(Duration.seconds(0.5F), (_) -> this.setBlink(false)),
                    new KeyFrame(Duration.seconds(1.0F)));
            mapping.addDisposeEvent(this::dispose);
            start();
        }

        public void start() {
            this.caretTimeline.play();
        }

        public void playFromStart() {
            this.caretTimeline.playFromStart();
        }

        public void stop() {
            this.caretTimeline.stop();
        }

        public void dispose() {
            stop();
            caretTimeline.getKeyFrames().clear();
        }

        private void setBlink(boolean var1) {
            BooleanProperty var2 = this.blinkPropertyRef.get();
            if (var2 == null) {
                this.caretTimeline.stop();
            } else {
                var2.set(var1);
            }
        }
    }
}

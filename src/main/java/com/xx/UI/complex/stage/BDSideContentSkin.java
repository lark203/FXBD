package com.xx.UI.complex.stage;

import com.xx.UI.ui.BDSkin;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class BDSideContentSkin extends BDSkin<BDSideContent> {
    private final AnchorPane header;
    private final HBox prePane;
    private final Text title;
    private final HBox postPane;
    private final PseudoClass ANIMATION_CLASS;
    private final StackPane content;
    private final VBox root;

    protected BDSideContentSkin(BDSideContent bdSideContent) {
        this.header = new AnchorPane();
        this.prePane = new HBox();
        this.title = new Text();
        this.postPane = new HBox();
        this.ANIMATION_CLASS = PseudoClass.getPseudoClass("animation");
        this.content = new StackPane();
        this.root = new VBox();
        super(bdSideContent);
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void initProperty() {
        mapping.bindProperty(title.textProperty(), control.titleProperty())
                .addListener(() -> postPane.pseudoClassStateChanged(ANIMATION_CLASS, control.isAnimated()), true, control.animatedProperty())
                .addListener(()->{
                    content.getChildren().clear();
                    if (control.getContent() != null)
                        content.getChildren().add(control.getContent());
                },true,control.contentProperty())
                .addListener(() -> {
                    prePane.getChildren().add(title);
                    if (!control.preNodeItemsProperty().isEmpty())
                        prePane.getChildren().addAll(control.preNodeItemsProperty().get());
                    if (!control.afterNodeItemsProperty().isEmpty())
                        postPane.getChildren().addAll(control.afterNodeItemsProperty().get());
                }, true, (ObservableList<?>) control.preNodeItemsProperty(), control.afterNodeItemsProperty());
    }

    @Override
    public void initUI() {
        header.getChildren().addAll(prePane, postPane);
        AnchorPane.setLeftAnchor(prePane, .0);
        AnchorPane.setTopAnchor(prePane, .0);
        AnchorPane.setBottomAnchor(prePane, .0);
        AnchorPane.setRightAnchor(postPane, .0);
        AnchorPane.setTopAnchor(postPane, .0);
        AnchorPane.setBottomAnchor(postPane, .0);
        header.getStyleClass().add("bd-side-content-header");
        prePane.getStyleClass().add("bd-side-content-header-pre");
        title.getStyleClass().add("bd-side-content-title");
        postPane.getStyleClass().add("bd-side-content-header-post");
        content.getStyleClass().add("bd-side-content-content");
        VBox.setVgrow(content, Priority.ALWAYS);
        root.getChildren().setAll(header, content);
        root.getStyleClass().add("bd-side-content");
        getChildren().add(root);
    }
}

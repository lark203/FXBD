package com.xx.UI.complex.stage;

import com.xx.UI.basic.BDButton;
import com.xx.UI.ui.BDIcon;
import com.xx.UI.util.Util;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HeaderBar;
import javafx.scene.layout.HeaderButtonType;
import javafx.scene.layout.HeaderDragType;
import javafx.scene.text.Text;

public class BDHeaderBarBuilder {
    private final HeaderBar headerBar = new HeaderBar();
    private final HBox leadingBox = new HBox();
    private final HBox centerBox = new HBox();
    private final HBox trailingBox = new HBox();

    public BDHeaderBarBuilder() {
        headerBar.getStyleClass().add("bd-header-bar");
        leadingBox.getStyleClass().add("bd-header-bar-leading");
        centerBox.getStyleClass().add("bd-header-bar-center");
        trailingBox.getStyleClass().add("bd-header-bar-trailing");
        headerBar.setLeading(leadingBox);
        HeaderBar.setAlignment(leadingBox, Pos.CENTER_LEFT);
        headerBar.setCenter(centerBox);
        HeaderBar.setAlignment(centerBox, Pos.CENTER);
        headerBar.setTrailing(trailingBox);
        HeaderBar.setAlignment(trailingBox, Pos.CENTER_RIGHT);
        HeaderBar.setDragType(centerBox, HeaderDragType.DRAGGABLE);
    }

    public BDHeaderBarBuilder addLeading(Node node) {
        leadingBox.getChildren().add(node);
        return this;
    }

    public BDHeaderBarBuilder addIcon(ImageView imageView) {
        HBox.setMargin(imageView,new Insets(0, 0, 0, 10));
        return addLeading(imageView);
    }

    public BDHeaderBarBuilder addCenter(Node node) {
        centerBox.getChildren().add(node);
        return this;
    }

    public BDHeaderBarBuilder addTitleIncenter(String title) {
        Text node = new Text(title);
        node.getStyleClass().add("bd-header-bar-title");
        return addCenter(node);
    }

    public BDHeaderBarBuilder addTitleInLeft(String title) {
        Text node = new Text(title);
        node.getStyleClass().add("bd-header-bar-title");
        return addLeading(node);
    }

    public BDHeaderBarBuilder addTrailing(Node node) {
        trailingBox.getChildren().add(node);
        return this;
    }

    public BDHeaderBarBuilder addMaximizeButton() {
        BDButton button = new BDButton();
        button.getStyleClass().add("bd-stage-tool-button");
        button.getStyleClass().add("bd-stage-maximize-button");
        button.setDefaultGraphic(Util.getImageView(15, BDIcon.MAXIMIZE_DARK));
        button.setPressGraphic(Util.getImageView(15, BDIcon.MAXIMIZE_INACTIVE_DARK));
        button.setSelectable(false);
        HeaderBar.setButtonType(button, HeaderButtonType.MAXIMIZE);
        return addTrailing(button);
    }

    public BDHeaderBarBuilder addMinimizeButton() {
        BDButton button = new BDButton();
        button.getStyleClass().add("bd-stage-tool-button");
        button.getStyleClass().add("bd-stage-minimize-button");
        button.setDefaultGraphic(Util.getImageView(15, BDIcon.MINIMIZE_DARK));
        button.setPressGraphic(Util.getImageView(15, BDIcon.MINIMIZE_INACTIVE_DARK));
        button.setSelectable(false);
        HeaderBar.setButtonType(button, HeaderButtonType.ICONIFY);
        return addTrailing(button);
    }

    public BDHeaderBarBuilder addCloseButton() {
        BDButton button = new BDButton();
        button.getStyleClass().add("bd-stage-tool-button");
        button.getStyleClass().add("bd-stage-close-button");
        button.setDefaultGraphic(Util.getImageView(15, BDIcon.CLOSE_DARK));
        button.setPressGraphic(Util.getImageView(15, BDIcon.CLOSE_INACTIVE_DARK));
        button.setSelectable(false);
        HeaderBar.setButtonType(button, HeaderButtonType.CLOSE);
        return addTrailing(button);
    }

    public HeaderBar build() {
        return headerBar;
    }
}

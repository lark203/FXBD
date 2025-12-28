package com.xx.UI.complex.stage;

import com.xx.UI.util.Util;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HeaderBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class BDStageBuilder {
    private final VBox root = new VBox();
    private final Stage stage = new Stage();

    public BDStageBuilder(HeaderBar headerBar) {
        stage.initStyle(StageStyle.EXTENDED);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        root.getChildren().add(0, headerBar);
        HeaderBar.setPrefButtonHeight(stage, 0);
    }

    public BDStageBuilder buildContent(Node content) {
        VBox.setVgrow(content, Priority.ALWAYS);
        root.getChildren().add(content);
        return this;
    }

    public BDStageBuilder buildStyle(String path) {
        Application.setUserAgentStylesheet(Util.getResourceUrl(path));
        return this;
    }

    public Stage build() {
        return stage;
    }
}

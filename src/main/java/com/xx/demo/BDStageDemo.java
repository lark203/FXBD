package com.xx.demo;

import com.xx.UI.complex.stage.BDHeaderBarBuilder;
import com.xx.UI.complex.stage.BDStageBuilder;
import com.xx.UI.complex.textArea.view.BDTextArea;
import com.xx.UI.complex.textArea.view.BDTextAreaSearch;
import com.xx.UI.ui.BDIcon;
import com.xx.UI.util.Util;
import javafx.application.Application;
import javafx.stage.Stage;

public class BDStageDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        BDHeaderBarBuilder builder = new BDHeaderBarBuilder()
                .addTitleIncenter("BDStageDemo")
                .addIcon(Util.getImageView(25, BDIcon.IDEA_MODULE))
                .addMinimizeButton()
                .addMaximizeButton()
                .addCloseButton();
        new BDStageBuilder(builder.build())
                .buildContent(new BDTextAreaSearch(new BDTextArea()))
                .buildStyle("/css/cupertino-light.css")
                .build()
                .show();
    }
}

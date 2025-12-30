package com.xx.demo;

import com.xx.UI.complex.stage.BDHeaderBarBuilder;
import com.xx.UI.complex.stage.BDStageBuilder;
import com.xx.UI.complex.textArea.view.BDTextArea;
import com.xx.UI.complex.textArea.view.BDTextAreaSearch;
import com.xx.UI.ui.BDIcon;
import com.xx.UI.util.Util;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * BDStage演示程序
 * 优化后的代码，具有更好的可读性和可维护性
 */
public class BDStageDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 构建标题栏
        BDHeaderBarBuilder headerBarBuilder = createHeaderBarBuilder();

        // 构建主窗口
        BDStageBuilder stageBuilder = new BDStageBuilder()
                .setContent(new BDTextAreaSearch(new BDTextArea()))
                .setStyle(Util.getResourceUrl("/css/cupertino-light.css"))
                .setHeaderBar(headerBarBuilder);

        // 显示窗口
        stageBuilder.build().show();
    }

    /**
     * 创建标题栏构建器
     * @return 配置好的标题栏构建器
     */
    private BDHeaderBarBuilder createHeaderBarBuilder() {
        return new BDHeaderBarBuilder()
                .addIcon(Util.getImageView(25, BDIcon.IDEA_MODULE))
                .addTitle("BDStageDemo")
                .addMinimizeButton()
                .addMaximizeButton()
                .addCloseButton();
    }
}
package com.xx.demo;

import com.xx.UI.basic.BDButton;
import com.xx.UI.complex.stage.*;
import com.xx.UI.complex.textArea.view.BDTextArea;
import com.xx.UI.complex.textArea.view.BDTextAreaSearch;
import com.xx.UI.ui.BDIcon;
import com.xx.UI.util.Util;
import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * BDStage 演示程序
 */
public class BDStageDemo extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 构建标题栏
        BDHeaderBarBuilder headerBarBuilder = new BDHeaderBarBuilder()
                .addIcon(Util.getImageView(25, BDIcon.IDEA_MODULE))
                .addTitle("BD Stage Demo - 全面测试版")
                .addMinimizeButton()
                .addMaximizeButton()
                .addCloseButton();

        // 构建内容区域 - 项目侧边栏 (LEFT, FRONT)
        BDSideContent fileContent = new BDSideContent();
        fileContent.setTitle("项目文件");
        BDTextArea fileTextArea = new BDTextArea("项目文件内容展示区域\n- src/main/java\n- src/test/java\n- pom.xml\n- README.md");
        fileContent.setContent(fileTextArea);
        BDSideBarItem projectItem = new BDSideBarItem("项目", Util.getImageView(30, BDIcon.FOLDER), Util.getImageView(30, BDIcon.FOLDER_DARK), BDDirection.LEFT, BDInSequence.FRONT, fileContent);

        // 构建内容区域 - 搜索侧边栏 (LEFT, AFTER) - 第一个搜索项
        BDSideContent searchContent = new BDSideContent();
        searchContent.setTitle("搜索结果");
        BDTextArea searchTextArea = new BDTextArea("搜索功能测试\n- 搜索框功能\n- 搜索历史\n- 搜索结果高亮\n- 高级搜索选项");
        searchContent.setContent(searchTextArea);
        BDSideBarItem searchItem = new BDSideBarItem("搜索", Util.getImageView(30, BDIcon.SEARCH), Util.getImageView(30, BDIcon.SEARCH_DARK), BDDirection.LEFT, BDInSequence.AFTER, searchContent);

        // 构建内容区域 - 搜索侧边栏 (LEFT, AFTER) - 第二个搜索项
        BDSideContent searchContent2 = new BDSideContent();
        searchContent2.setTitle("高级搜索");
        BDTextArea searchTextArea2 = new BDTextArea("高级搜索功能测试\n- 正则表达式搜索\n- 文件类型过滤\n- 大小写敏感\n- 搜索范围设置");
        searchContent2.setContent(searchTextArea2);
        BDSideBarItem searchItem2 = new BDSideBarItem("高级搜索", Util.getImageView(30, BDIcon.SEARCH), Util.getImageView(30, BDIcon.SEARCH_DARK), BDDirection.LEFT, BDInSequence.AFTER, searchContent2);

        // 构建内容区域 - 书签侧边栏 (RIGHT, FRONT)
        BDSideContent bookmarkContent = new BDSideContent();
        bookmarkContent.setTitle("常用书签");
        BDTextArea bookmarkTextArea = new BDTextArea("书签管理\n- 文档链接\n- API参考\n- 学习资源\n- 工具网站");
        bookmarkContent.setContent(bookmarkTextArea);
        BDSideBarItem bookmarkItem = new BDSideBarItem("书签", Util.getImageView(30, BDIcon.BOOKMARK), Util.getImageView(30, BDIcon.BOOKMARK_DARK), BDDirection.RIGHT, BDInSequence.FRONT, bookmarkContent);

        // 构建内容区域 - 设置侧边栏 (RIGHT, AFTER)
        BDSideContent settingContent = new BDSideContent();
        settingContent.setTitle("系统设置");
        BDTextArea settingTextArea = new BDTextArea("系统配置\n- 外观设置\n- 键盘快捷键\n- 编辑器选项\n- 插件管理\n- 用户偏好");
        settingContent.setContent(settingTextArea);
        BDSideBarItem settingItem = new BDSideBarItem("设置", Util.getImageView(30, BDIcon.SETTINGS), Util.getImageView(30, BDIcon.SETTINGS_DARK), BDDirection.RIGHT, BDInSequence.AFTER, settingContent);

        // 构建内容区域 - 帮助侧边栏 (RIGHT, FRONT) - 第一个帮助项
        BDSideContent helpContent = new BDSideContent();
        helpContent.setTitle("帮助信息");
        BDTextArea helpTextArea = new BDTextArea("帮助文档\n- 快速入门\n- 功能说明\n- 常见问题\n- 联系支持");
        helpContent.setContent(helpTextArea);
        BDSideBarItem helpItem = new BDSideBarItem("帮助", Util.getImageView(30, BDIcon.HELP), Util.getImageView(30, BDIcon.HELP_DARK), BDDirection.RIGHT, BDInSequence.FRONT, helpContent);

        // 构建内容区域 - 帮助侧边栏 (RIGHT, FRONT) - 第二个帮助项
        BDSideContent helpContent2 = new BDSideContent();
        helpContent2.setTitle("教程指南");
        BDTextArea helpTextArea2 = new BDTextArea("教程指南\n- 入门教程\n- 高级功能\n- 最佳实践\n- 视频教程");
        helpContent2.setContent(helpTextArea2);
        BDSideBarItem helpItem2 = new BDSideBarItem("教程", Util.getImageView(30, BDIcon.HELP), Util.getImageView(30, BDIcon.HELP_DARK), BDDirection.RIGHT, BDInSequence.FRONT, helpContent2);

        // 构建内容区域 - 控制台侧边栏 (BOTTOM, FRONT)
        BDSideContent consoleContent = new BDSideContent();
        consoleContent.setTitle("控制台输出");
        BDTextArea consoleTextArea = new BDTextArea("控制台信息展示\n- 系统日志\n- 错误信息\n- 调试输出\n- 应用状态");
        consoleContent.setContent(consoleTextArea);
        BDSideBarItem consoleItem = new BDSideBarItem("控制台", Util.getImageView(30, BDIcon.CONSOLE_RUN), Util.getImageView(30, BDIcon.CONSOLE_RUN_DARK), BDDirection.BOTTOM, BDInSequence.FRONT, consoleContent);

        // 构建内容区域 - 输出侧边栏 (BOTTOM, AFTER)
        BDSideContent outputContent = new BDSideContent();
        outputContent.setTitle("构建输出");
        BDTextArea outputTextArea = new BDTextArea("构建和输出信息\n- 编译结果\n- 执行日志\n- 性能统计\n- 构建历史");
        outputContent.setContent(outputTextArea);
        BDSideBarItem outputItem = new BDSideBarItem("输出", Util.getImageView(30, BDIcon.DBMS_OUTPUT), Util.getImageView(30, BDIcon.DBMS_OUTPUT_DARK), BDDirection.BOTTOM, BDInSequence.AFTER, outputContent);

        // 构建内容区域 - 通知侧边栏 (BOTTOM, FRONT)
        BDSideContent notificationContent = new BDSideContent();
        notificationContent.setTitle("系统通知");
        BDTextArea notificationTextArea = new BDTextArea("通知和消息中心\n- 系统提醒\n- 更新通知\n- 任务完成\n- 错误警告");
        notificationContent.setContent(notificationTextArea);
        BDSideBarItem notificationItem = new BDSideBarItem("通知", Util.getImageView(30, BDIcon.WARNING), Util.getImageView(30, BDIcon.WARNING_DARK), BDDirection.BOTTOM, BDInSequence.FRONT, notificationContent);

        // 创建中心内容区域 - 搜索文本区域
        BDTextArea centerTextArea = new BDTextArea("""
                欢迎使用 BDStage 演示程序！
                此程序展示了完整的界面组件功能：
                1. 标题栏包含图标、标题和窗口控制按钮
                2. 左侧侧边栏包含项目、搜索(前后顺序)
                3. 右侧侧边栏包含书签、设置、帮助(前后顺序)
                4. 底部侧边栏包含控制台、输出、通知(前后顺序)
                5. 中心区域支持搜索和文本编辑
                
                功能测试点：
                - 侧边栏展开/收起
                - 文本区域编辑功能
                - 搜索功能
                - 窗口最小化/最大化/关闭
                - 主题样式切换
                - 响应式布局
                - 所有方向和顺序的侧边栏测试""");
        BDTextAreaSearch centerSearchArea = new BDTextAreaSearch(centerTextArea);

        BDContentBuilder contentBuilder = new BDContentBuilder()
                .addSideNode(BDDirection.TOP,BDInSequence.FRONT,new TextField("顶部前"))
                .addSideNode(BDDirection.TOP,BDInSequence.AFTER,new TextField("顶部后"))
                .addSideNode(BDDirection.LEFT, BDInSequence.FRONT, new BDButton("左侧前"))
                .addSideNode(projectItem, searchItem, searchItem2, bookmarkItem, helpItem, helpItem2, settingItem)
                .addSideNode(consoleItem, outputItem, notificationItem)
                .addCenterNode(centerSearchArea)
                .addSideNode(BDDirection.BOTTOM, BDInSequence.FRONT, new TextField("底部前"))
                .addSideNode(BDDirection.BOTTOM, BDInSequence.AFTER, new TextField("底部后"));

        // 构建主窗口
        BDStageBuilder stageBuilder = new BDStageBuilder()
                .setContent(contentBuilder.build())
                .setStyle(Util.getResourceUrl("/css/cupertino-light.css"))
                .setHeaderBar(headerBarBuilder);

        // 显示窗口
        stageBuilder.build().show();
        
        // 添加窗口事件监听器进行测试
        stage.setOnCloseRequest(event -> {
            System.out.println("窗口关闭事件触发");
        });
        
        stage.setOnShown(event -> {
            System.out.println("窗口显示事件触发");
        });
    }

}
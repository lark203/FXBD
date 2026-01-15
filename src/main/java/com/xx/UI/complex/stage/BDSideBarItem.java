package com.xx.UI.complex.stage;

import com.xx.UI.basic.BDButton;
import com.xx.UI.util.LazyValue;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Skin;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Objects;

import static com.xx.UI.complex.stage.BDSidebar.BD_SIDE_BAR_FORMAT;

public class BDSideBarItem extends BDButton {
    final SimpleObjectProperty<BDSidebar> sidebar = new SimpleObjectProperty<>();
    final LazyValue<Stage> stage = new LazyValue<>(Stage::new);
    private final String name;
    private final BDSideContent sideContent;
    private final SimpleBooleanProperty windowOpen = new SimpleBooleanProperty(false);
    private final SimpleObjectProperty<BDDirection> direction = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<BDInSequence> inSequence = new SimpleObjectProperty<>();
    boolean tempAnimation = true;
    Integer tempIndex = 0;
    double cachedWidth = -1;
    double cachedHeight = -1;
    private String shortcutKey;
    // 缓存拖拽图像
    private WritableImage cachedDragImage;

    public BDSideBarItem(String name, ImageView defaultIcon, ImageView selectIcon, BDDirection direction, BDInSequence inSequence, BDSideContent sideContent) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(defaultIcon);
        Objects.requireNonNull(selectIcon);
        Objects.requireNonNull(direction);
        Objects.requireNonNull(inSequence);
        this.name = name;
        this.sideContent = sideContent;
        setDirection(direction);
        this.inSequence.set(inSequence);
        setDefaultGraphic(defaultIcon);
        setSelectedGraphic(selectIcon);
    }

    public BDSideBarItem(String name, String shortcutKey, ImageView defaultIcon, ImageView selectIcon, BDDirection direction, BDInSequence inSequence, BDSideContent sideContent) {
        this(name, defaultIcon, selectIcon, direction, inSequence, sideContent);
        this.shortcutKey = shortcutKey;
    }

    public String getName() {
        return name;
    }

    public String getShortcutKey() {
        return shortcutKey;
    }

    public BDSideContent getSideContent() {
        return sideContent;
    }

    public boolean isWindowOpen() {
        return windowOpen.get();
    }

    public void setWindowOpen(boolean windowOpen) {
        this.windowOpen.set(windowOpen);
    }

    public BooleanProperty windowOpenProperty() {
        return windowOpen;
    }

    public BDDirection getDirection() {
        return direction.get();
    }

    public void setDirection(BDDirection direction) {
        if (direction.equals(BDDirection.TOP))
            throw new IllegalArgumentException("The direction cannot be TOP");
        this.direction.set(direction);
    }

    public ObjectProperty<BDDirection> directionProperty() {
        return direction;
    }

    public BDInSequence getInSequence() {
        return inSequence.get();
    }

    public void setInSequence(BDInSequence inSequence) {
        this.inSequence.set(inSequence);
    }

    public ObjectProperty<BDInSequence> inSequenceProperty() {
        return inSequence;
    }

    void drag() {
        sidebar.get().removeItemNode(this);

    }

    void dragEnd() {
        if (sidebar.get() instanceof BDSidebar bar) {
            bar.addItemNode(this, tempIndex);
            if (isSelected()) bar.showSideBarItem(this);
        } else
            stage.get().show();
        clearCachedDragImage();
    }

    /**
     * 处理拖拽检测事件，创建高质量的拖拽图像
     */
    void handleDragDetected(MouseEvent e) {
        if (!e.getButton().equals(MouseButton.PRIMARY)) return;

        e.setDragDetect(true);

        // 确保控件完全渲染
        applyCss();
        layout();

        // 创建高质量的拖拽图像
        WritableImage dragImage = createHighQualityDragImage();

        // 启动拖拽
        Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
        ClipboardContent cd = new ClipboardContent();

        // 设置拖拽图像（使用高质量图像）
        if (dragImage != null) {
            dragboard.setDragView(dragImage,
                    dragImage.getWidth() / 2,
                    dragImage.getHeight() / 2);
        }

        // 设置拖拽内容
        cd.put(BD_SIDE_BAR_FORMAT, getUserData() == null ? toString() : getUserData().toString());
        dragboard.setContent(cd);
        drag();
    }

    /**
     * 创建高质量的拖拽图像
     */
    private WritableImage createHighQualityDragImage() {
        double currentWidth = getWidth();
        double currentHeight = getHeight();

        // 检查是否需要重新创建缓存图像
        if (cachedDragImage != null &&
                Math.abs(currentWidth - cachedWidth) < 0.5 &&
                Math.abs(currentHeight - cachedHeight) < 0.5) {
            return cachedDragImage;
        }

        // 确保有有效的尺寸
        if (currentWidth <= 0 || currentHeight <= 0) {
            currentWidth = prefWidth(-1);
            currentHeight = prefHeight(-1);

            if (currentWidth <= 0 || currentHeight <= 0) {
                currentWidth = 100;
                currentHeight = 30;
            }
        }

        // 创建图像
        WritableImage image = createScaledSnapshot(1.25);

        // 缓存结果
        cachedDragImage = image;
        cachedWidth = currentWidth;
        cachedHeight = currentHeight;

        return image;
    }


    /**
     * 创建缩放后的截图
     */
    private WritableImage createScaledSnapshot(double scaleFactor) {
        double originalWidth = Math.max(1, getWidth());
        double originalHeight = Math.max(1, getHeight());

        // 计算目标尺寸
        int targetWidth = (int) Math.max(1, originalWidth * scaleFactor);
        int targetHeight = (int) Math.max(1, originalHeight * scaleFactor);

        // 限制最大尺寸，避免性能问题
        targetWidth = Math.min(targetWidth, 500);
        targetHeight = Math.min(targetHeight, 200);

        // 创建图像
        WritableImage image = new WritableImage(targetWidth, targetHeight);

        try {
            // 配置快照参数
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.WHITE); // 透明背景
            parameters.setDepthBuffer(true); // 启用深度缓冲

            // 如果缩放因子不是1，应用变换
            if (Math.abs(scaleFactor - 1.0) > 0.01) {
                parameters.setTransform(javafx.scene.transform.Transform.scale(
                        scaleFactor, scaleFactor));
            }

            // 截取快照
            snapshot(parameters, image);
            return image;
        } catch (Exception e) {
            return createSimpleSnapshot();
        }
    }

    /**
     * 创建简单截图（后备方案）
     */
    private WritableImage createSimpleSnapshot() {
        double width = Math.max(1, getWidth());
        double height = Math.max(1, getHeight());

        int imageWidth = (int) width;
        int imageHeight = (int) height;

        WritableImage image = new WritableImage(imageWidth, imageHeight);
        snapshot(new SnapshotParameters(), createImageWithShadowUsingImageView(image));

        return image;
    }


    /**
     * 使用ImageView和效果API添加阴影
     */
    private WritableImage createImageWithShadowUsingImageView(WritableImage original) {
        // 创建ImageView来显示原始图像
        javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(original);

        // 设置阴影效果
        javafx.scene.effect.DropShadow shadow = new javafx.scene.effect.DropShadow();

        // 阴影参数配置
        shadow.setColor(javafx.scene.paint.Color.rgb(0, 0, 0, 0.3)); // 半透明黑色阴影
        shadow.setRadius(3); // 阴影模糊半径，值越大阴影越模糊
        shadow.setOffsetX(1); // 水平偏移量
        shadow.setOffsetY(1); // 垂直偏移量
        shadow.setSpread(0.05); // 阴影扩展范围

        // 应用效果到ImageView
        imageView.setEffect(shadow);

        // 设置背景透明
        javafx.scene.layout.Pane container = new javafx.scene.layout.Pane(imageView);
        container.setStyle("-fx-background-color: transparent;");

        // 由于阴影会超出原图边界，需要增加容器大小来容纳阴影
        double shadowMargin = shadow.getRadius() + Math.max(Math.abs(shadow.getOffsetX()), Math.abs(shadow.getOffsetY()));
        int containerWidth = (int) (original.getWidth() + shadowMargin * 2);
        int containerHeight = (int) (original.getHeight() + shadowMargin * 2);

        // 调整ImageView在容器中的位置，确保图像居中
        imageView.setLayoutX(shadowMargin);
        imageView.setLayoutY(shadowMargin);

        // 创建快照参数
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(javafx.scene.paint.Color.TRANSPARENT);
        params.setDepthBuffer(true);

        // 渲染带阴影的图像
        return container.snapshot(params, new WritableImage(containerWidth, containerHeight));
    }

    /**
     * 清理缓存的拖拽图像
     */
    void clearCachedDragImage() {
        cachedDragImage = null;
        cachedWidth = -1;
        cachedHeight = -1;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new BDSideBarItemSkin(this);
    }
}


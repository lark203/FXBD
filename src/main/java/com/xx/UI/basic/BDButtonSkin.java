package com.xx.UI.basic;

import com.xx.UI.ui.BDUI;
import com.xx.UI.util.BDMapping;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class BDButtonSkin extends ButtonSkin implements BDUI {
    private final BDMapping mapping;
    private final BDButton control;
    private final SimpleBooleanProperty pressed = new SimpleBooleanProperty(false);

    public BDButtonSkin(final BDButton button) {
        super(button);
        control = button;
        mapping = button.getMapping();
        initUI();
        initEvent();
        initProperty();
    }

    @Override
    public void initEvent() {
        mapping.addEventFilter(control, ActionEvent.ACTION, _ -> {
                    if (control.isSelectable())
                        control.setSelected(!control.isSelected());
                })
                .addEventFilter(control, MouseEvent.MOUSE_RELEASED, _ -> pressed.set(false))
                .addEventFilter(control, MouseEvent.MOUSE_PRESSED, _ -> pressed.set(true))
                .addEventFilter(control, MouseEvent.MOUSE_EXITED_TARGET, _ -> pressed.set(false));
    }

    @Override
    public void initProperty() {
        Background background = control.getBackground();
        mapping.binding(control.backgroundProperty(),
                        Bindings.createObjectBinding(() -> {
                                    if (control.isSelected()) {
                                        if (pressed.get())
                                            return new Background(new BackgroundFill(control.getSelectedPressedFill(), background == null ? CornerRadii.EMPTY : background.getFills().getFirst().getRadii(), background == null ? Insets.EMPTY : background.getFills().getFirst().getInsets()));
                                        else if (control.isHover())
                                            return new Background(
                                                    new BackgroundFill(control.getSelectedHoverFill(), background == null ? CornerRadii.EMPTY : background.getFills().getFirst().getRadii(), background == null ? Insets.EMPTY : background.getFills().getFirst().getInsets()));
                                        return new Background(new BackgroundFill(control.getSelectedFill(), background == null ? CornerRadii.EMPTY : background.getFills().getFirst().getRadii(), background == null ? Insets.EMPTY : background.getFills().getFirst().getInsets()));
                                    } else {
                                        if (pressed.get())
                                            return new Background(new BackgroundFill(control.getPressedFill(), background == null ? CornerRadii.EMPTY : background.getFills().getFirst().getRadii(), background == null ? Insets.EMPTY : background.getFills().getFirst().getInsets()));
                                        else if (control.isHover())
                                            return new Background(new BackgroundFill(control.getHoverFill(), background == null ? CornerRadii.EMPTY : background.getFills().getFirst().getRadii(), background == null ? Insets.EMPTY : background.getFills().getFirst().getInsets()));
                                        else
                                            return new Background(new BackgroundFill(control.getDefaultFill(), background == null ? CornerRadii.EMPTY : background.getFills().getFirst().getRadii(), background == null ? Insets.EMPTY : background.getFills().getFirst().getInsets()));
                                    }
                                },
                                control.defaultFillProperty()
                                , control.hoverFillProperty()
                                , control.pressedFillProperty()
                                , control.selectedFillProperty()
                                , control.selectedHoverFillProperty()
                                , control.selectedPressedFillProperty()
                                , control.selectedProperty()
                                , control.hoverProperty()
                                , pressed))
                .addListener(() -> {
                            if (control.isSelected()) {
                                if (pressed.get()) {
                                    if (control.getSelectedGraphic() != null)
                                        control.setGraphic(control.getSelectedPressGraphic());
                                    else if (control.getPressGraphic() != null)
                                        control.setGraphic(control.getPressGraphic());
                                    else control.setGraphic(control.getDefaultGraphic());
                                }
                            }else if (pressed.get()){
                                    if (control.getPressGraphic() != null)
                                        control.setGraphic(control.getPressGraphic());
                                    else if (control.getDefaultGraphic() != null)
                                        control.setGraphic(control.getDefaultGraphic());
                                    else control.setGraphic(null);
                                } else
                                    control.setGraphic(control.getDefaultGraphic());
                        }, true,
                        pressed,
                        control.selectedProperty(),
                        control.defaultGraphicProperty(),
                        control.selectedGraphicProperty(),
                        control.selectedPressGraphicProperty(),
                        control.pressGraphicProperty());
    }
}

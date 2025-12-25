package com.xx.demo;

import com.xx.UI.util.BDMapping;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class BDMappingDemo {
    static void main() {
        bdMapping();
        normal();
    }

    private static void normal() {
        Text t = new Text();
        Text t1 = new Text();
        Text t2 = new Text();
//        绑定属性    监听属性变化    添加事件
        t2.textProperty().bindBidirectional(t.textProperty());
        t1.textProperty().bind(t2.textProperty());
        ChangeListener<String> stringChangeListener = (obs, oldVal, newVal) -> System.out.println("监听到属性变化：" + newVal);
        t2.textProperty().addListener(stringChangeListener);
        EventHandler<MouseEvent> eventHandler = _ -> System.out.println("点击了t");
        t.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
//        解绑，移除等一切操作
        t2.textProperty().unbindBidirectional(t.textProperty());
        t1.textProperty().unbind();
        t2.textProperty().removeListener(stringChangeListener);
        t.removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }

    private static void bdMapping() {
        Text t = new Text();
        Text t1 = new Text();
        Text t2 = new Text();
        BDMapping mapping = new BDMapping();
//        绑定属性    监听属性变化    添加事件
        mapping.bindBidirectional(t2.textProperty(), t.textProperty())
                .bindProperty(t1.textProperty(), t2.textProperty())
                .addListener(t2.textProperty(), (_, _, nv) -> System.out.println("监听到属性变化：" + nv))
                .addEventHandler(t, MouseEvent.MOUSE_CLICKED, _ -> System.out.println("点击了t"));
//        解绑，移除等一切操作
        mapping.dispose();
    }
}

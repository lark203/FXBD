package com.xx.UI.ui;

public interface BDUI {
    /** 初始化界面*/
    default void initUI() {
    }
    /** 初始化参数*/
    default void initProperty() {
    }
    /** 初始化事件*/
    default void initEvent() {
    }
}

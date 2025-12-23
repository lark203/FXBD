package com.xx.UI.complex.textArea.content;

import java.io.Serializable;

public interface Command extends Serializable {
    void execute();
    void unexecute();
    /** 将两个命令的效果合并到一起,返回一个boolean值表示是否合并成功 。*/
    boolean merge(Command command);
}


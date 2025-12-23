package com.xx.UI.util;

import java.util.function.Supplier;

public class LazyValue<T> {
    private final Supplier<T> supplier;
    private volatile T value;

    public LazyValue(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (value == null) {  // 第一次检查（无锁）
            synchronized (this) {
                if (value == null) {  // 第二次检查（加锁后）
                    value = supplier.get();
                }
            }
        }
        return value;
    }

    public void applyIfNotNone(Apply<T> apply) {
        if (!isNone())
            apply.handle(value);
    }

    public boolean isNone() {
        return value == null;
    }

}
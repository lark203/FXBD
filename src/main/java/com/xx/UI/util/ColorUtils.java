package com.xx.UI.util;

import javafx.scene.paint.Color;

public class ColorUtils {

    /**
     * 调整颜色的透明度，使其在指定背景上保持相同的视觉效果
     * 
     * @param originalColor 原始颜色
     * @param backgroundColor 背景颜色
     * @param targetOpacity 目标透明度 (0.0 - 1.0)
     * @return 调整后的新颜色
     */
    public static Color adjustTransparencyWithBackground(
            Color originalColor, 
            Color backgroundColor, 
            double targetOpacity) {
        
        // 处理完全透明的情况
        if (targetOpacity == 0.0) {
            return Color.TRANSPARENT;
        }
        
        // 提取原始颜色分量
        double or = originalColor.getRed();
        double og = originalColor.getGreen();
        double ob = originalColor.getBlue();
        double oa = originalColor.getOpacity();
        
        // 提取背景颜色分量
        double br = backgroundColor.getRed();
        double bg = backgroundColor.getGreen();
        double bb = backgroundColor.getBlue();
        
        // 计算当前实际显示的颜色（考虑原始透明度）
        double currentR = or * oa + br * (1 - oa);
        double currentG = og * oa + bg * (1 - oa);
        double currentB = ob * oa + bb * (1 - oa);
        
        // 计算新颜色的RGB分量（解混合方程）
        double newR = (currentR - br * (1 - targetOpacity)) / targetOpacity;
        double newG = (currentG - bg * (1 - targetOpacity)) / targetOpacity;
        double newB = (currentB - bb * (1 - targetOpacity)) / targetOpacity;
        
        // 确保颜色分量在有效范围内 [0, 1]
        newR = clamp(newR);
        newG = clamp(newG);
        newB = clamp(newB);
        
        return new Color(newR, newG, newB, targetOpacity);
    }
    
    /**
     * 简化版：假设白色背景
     */
    public static Color adjustTransparency(Color originalColor, double targetOpacity) {
        return adjustTransparencyWithBackground(originalColor, Color.WHITE, targetOpacity);
    }
    
    // 确保值在 [0, 1] 范围内
    private static double clamp(double value) {
        return Math.min(1, Math.max(0, value));
    }
    
    // 示例用法
    public static void main(String[] args) {
        // 在蓝色背景上调整红色的透明度
        Color original = Color.RED;
        Color background = Color.BLUE;
        double newOpacity = 0.6;
        
        Color adjusted = adjustTransparencyWithBackground(original, background, newOpacity);
        
        System.out.println("原始颜色: " + original);
        System.out.println("背景颜色: " + background);
        System.out.printf("新颜色: RGB(%.2f, %.2f, %.2f), 透明度: %.2f%n",
                adjusted.getRed(), 
                adjusted.getGreen(),
                adjusted.getBlue(),
                adjusted.getOpacity());
    }
}
package com.xx;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;

public class SvgToPngConverter {

    static void main() {
        try {
            convertSvgFolderToPng("E:\\download\\img","F:\\project\\java\\FXEditor\\src\\main\\resources\\texture", 500);
            System.out.println("转换完成！");
        } catch (Exception e) {
            System.err.println("转换出错: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void convertSvgFolderToPng(String inputPath, String outputPath, int targetWidth) {
        
        // 获取所有SVG文件
        Collection<File> svgFiles = FileUtils.listFiles(
                new File(inputPath),
                new String[]{"svg"},
                true
        );

        if (svgFiles.isEmpty()) {
            System.out.println("未找到SVG文件");
            return;
        }

        // 创建PNG转换器
        PNGTranscoder transcoder = new PNGTranscoder();
        
        // 设置目标宽度（高度自动保持比例）
        transcoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, (float) targetWidth);
        
        for (File svgFile : svgFiles) {
            // 准备输出路径
            String fileName = FilenameUtils.removeExtension(svgFile.getName()) + ".png";
            File pngFile = new File(outputPath, fileName);
            
            System.out.println("转换: " + svgFile.getName() + " → " + fileName);
            
            try (OutputStream outStream = new FileOutputStream(pngFile)) {
                // 配置转换器
                TranscoderInput input = new TranscoderInput(svgFile.toURI().toURL().toString());
                TranscoderOutput output = new TranscoderOutput(outStream);
                
                // 执行转换
                transcoder.transcode(input, output);
            } catch (Exception e) {
                System.err.println("转换失败 [" + svgFile.getName() + "]: " + e.getMessage());
            }
        }
    }
}
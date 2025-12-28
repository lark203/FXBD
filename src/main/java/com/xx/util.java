package com.xx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collectors;

import static com.xx.CompressionUtils.decompressAllInFolder;

//临时工具
public class util {
    public static void main() throws IOException {
//        decompressAllInFolder("F:\\project\\python\\jetbrainsIconCrawl");
//        transfer("F:\\project\\python\\jetbrainsIconCrawl\\downloads", "F:\\FXBD\\src\\main\\resources\\texture");
        generateBDIconEnum();
    }

    private static void transfer(String dirFrom, String dirTo) throws IOException {
        File fileFrom = new File(dirFrom);
        File fileTo = new File(dirTo);
        // 确保目标目录存在
        if (!fileTo.exists()) {
            fileTo.mkdirs();
        }
        Stack<File> stack = new Stack<>();
        stack.push(fileFrom);
        while (!stack.isEmpty()) {
            File currentFile = stack.pop();
            if (currentFile.isFile()) {
                String fileName = currentFile.getName();
                if (fileName.endsWith(".svg")) {
                    File newFile = new File(fileTo, fileName);
                    if (!newFile.exists())
                        Files.copy(currentFile.toPath(), newFile.toPath());
                }
            } else {
                for (File child : Objects.requireNonNull(currentFile.listFiles()))
                    stack.push(child);
            }
        }
    }

    private static void generateBDIconEnum() {
        // 配置路径 - 可根据需要修改
        String textureDirPath = "F:\\project\\java\\FXEditor\\src\\main\\resources\\texture";
        String outputFilePath = "C:\\Users\\X.....X\\Desktop\\BDIcon.java";

        File textureDir = new File(textureDirPath);
        File[] imageFiles = textureDir.listFiles();

        if (imageFiles == null || imageFiles.length == 0) {
            System.err.println("No image files found in directory: " + textureDirPath);
            return;
        }

        // 生成枚举常量部分
        String enumConstants = Arrays.stream(imageFiles)
                .filter(file -> !file.isDirectory())
                .map(file -> {
                    String fileName = file.getName();
                    String baseName = fileName.substring(0, fileName.lastIndexOf('.'));
                    String constantName = convertToConstantCase(baseName);
                    return String.format("    %s(\"/texture/%s\")", constantName, fileName);
                })
                .sorted() // 按字母顺序排序，使生成的代码更整洁
                .collect(Collectors.joining(",\n"));

        // 生成完整枚举类
        String enumClass = String.format("package com.xx.UI.ui;\n\n" +
                "public enum BDIcon {\n" +
                "%s;\n\n" +
                "    private final String path;\n\n" +
                "    BDIcon(String path) {\n" +
                "        this.path = path;\n" +
                "    }\n\n" +
                "    public String getIconPath() {\n" +
                "        return path;\n" +
                "    }\n" +
                "}", enumConstants);

        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            writer.write(enumClass);
            System.out.println("BDIcon enum successfully generated at: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing enum file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String convertToConstantCase(String input) {
        // 1. 替换非字母数字字符为下划线
        String s = input.replaceAll("[^a-zA-Z0-9]", "_");

        // 2. 处理驼峰命名边界
        // a) 小写字母后跟大写字母: myVariable -> my_Variable
        s = s.replaceAll("(?<=[a-z])(?=[A-Z])", "_");
        // b) 大写字母序列后跟小写字母: HTTPRequest -> HTTP_Request
        s = s.replaceAll("(?<=[A-Z])(?=[A-Z][a-z])", "_");

        // 3. 合并连续下划线并清理首尾
        s = s.replaceAll("_+", "_");
        s = s.replaceAll("^_|_$", "");

        // 4. 转换为大写
        s = s.toUpperCase();

        // 5. 特殊尺寸标识处理
        s = s.replace("20X20", "_20X20");
        s = s.replace("14X14", "_14X14");

        // 6. 再次清理可能产生的连续下划线
        s = s.replaceAll("_+", "_");
        s = s.replaceAll("^_|_$", "");

        return s;
    }
}
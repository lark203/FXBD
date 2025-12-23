//import com.xx.UI.complex.textArea.content.BDTextAreaContent;
//import com.xx.UI.complex.textArea.content.listener.ContentChangeEvent;
//import com.xx.UI.complex.textArea.content.listener.ContentChangeListener;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import java.util.Random;
//
//class BDTextAreaContentStressTest {
//
//    // 1亿字符性能测试 (约200MB内存)
//    @Test
//    void testLargeTextPerformance() {
//        BDTextAreaContent content = new BDTextAreaContent();
//        int targetSize = 101_000_000; // 1亿字符
//
//        // 阶段1: 批量追加文本
//
//        StringBuilder sb = new StringBuilder();
//        int batchSize = 100_000;
//        int totalInserted = 0;
//
//        while (totalInserted < targetSize) {
//            int remaining = targetSize - totalInserted;
//            int currentBatch = Math.min(batchSize, remaining);
//
//            // 生成随机文本（90%普通字符+10%换行）
//            Random random = new Random();
//            for (int i = 0; i < currentBatch; i++) {
//                if (random.nextInt(10) == 0) {
//                    sb.append('\n'); // 10%概率换行
//                } else {
//                    sb.append((char) ('a' + random.nextInt(26)));
//                }
//            }
//            totalInserted += currentBatch;
//        }
//        long startTime = System.currentTimeMillis();
//        System.out.println(sb.length());
//        content.insert(0, sb.toString(), false);
//        long insertTime = System.currentTimeMillis() - startTime;
//        System.out.printf("Insert %d chars: %d ms%n", targetSize, insertTime);
//
//        // 阶段2: 随机访问测试
//        startTime = System.currentTimeMillis();
//        Random random = new Random();
//        int sampleCount = 1000;
//        for (int i = 0; i < sampleCount; i++) {
//            int start = random.nextInt(targetSize / 2);
//            int length = random.nextInt(1000);
//            int end = Math.min(start + length, targetSize - 1);
//            content.get(start,end);
//        }
//        long accessTime = System.currentTimeMillis() - startTime;
//        System.out.printf("Random access %d times: %d ms%n", sampleCount, accessTime);
//
//        // 阶段3: 删除后半部分内容
//        startTime = System.currentTimeMillis();
//        int deleteStart = targetSize / 2;
//        content.delete(deleteStart, targetSize, true);
//        long deleteTime = System.currentTimeMillis() - startTime;
//        System.out.printf("Delete %d chars: %d ms%n", targetSize/2, deleteTime);
//
//        // 验证最终状态
//        Assertions.assertEquals(deleteStart, content.length());
//        Assertions.assertTrue(content.paragraphs.size() > 1000); // 验证段落数量合理
//    }
//}
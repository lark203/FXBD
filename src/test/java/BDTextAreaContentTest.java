//import com.xx.UI.complex.textArea.content.BDTextAreaContent;
//import com.xx.UI.complex.textArea.content.Content;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class BDTextAreaContentTest {
//    private BDTextAreaContent content;
//
//    @BeforeEach
//    void setUp() {
//        content = new BDTextAreaContent();
//    }
//
//    // ================== 基础功能测试 ==================
//
//    @Test
//    void testInitialState() {
//        assertEquals(0, content.length());
//        assertEquals("", content.get(0, 0));
//        assertThrows(IndexOutOfBoundsException.class, () -> content.get(0, 1));
//    }
//
//    @Test
//    void testInsertSingleParagraph() {
//        content.insert(0, "Hello", true);
//        assertEquals(5, content.length());
//        assertEquals("Hello", content.get(0, 5));
//    }
//
//    @Test
//    void testInsertMultipleParagraphs() {
//        content.insert(0, "First\nSecond\nThird", true);
//        assertEquals(18, content.length()); // 5 + 1 + 6 + 1 + 5
//        assertEquals("First\nSecond\nThird", content.get(0, 18));
//        assertEquals("Second", content.get(6, 12));
//    }
//
//    @Test
//    void testDeleteSingleCharacter() {
//        content.insert(0, "Hello", true);
//        content.delete(1, 2, true); // 删除 'e'
//        assertEquals(4, content.length());
//        assertEquals("Hllo", content.get(0, 4));
//    }
//
//    @Test
//    void testDeleteMultipleParagraphs() {
//        content.insert(0, "First\nSecond\nThird", true);
//        content.delete(6, 12,true); // 删除 "Second"
//        assertEquals(12, content.length()); // 5 + 1 + 5
//        assertEquals("First\n\nThird", content.get(0, 12));
//    }
//
//    // ================== 边界条件测试 ==================
//
//    @Test
//    void testInsertAtStart() {
//        content.insert(0, "World", true);
//        content.insert(0, "Hello ", true);
//        assertEquals(11, content.length());
//        assertEquals("Hello World", content.get(0, 11));
//    }
//
//    @Test
//    void testInsertAtEnd() {
//        content.insert(0, "Hello", true);
//        content.insert(5, " World", true);
//        assertEquals(11, content.length());
//        assertEquals("Hello World", content.get(0, 11));
//    }
//
//    @Test
//    void testInsertInMiddle() {
//        content.insert(0, "Hllo", true);
//        content.insert(1, "e", true);
//        assertEquals(5, content.length());
//        assertEquals("Hello", content.get(0, 5));
//    }
//
//    @Test
//    void testDeleteAtStart() {
//        content.insert(0, "Hello World", true);
//        content.delete(0, 6, true); // 删除 "Hello "
//        assertEquals(5, content.length());
//        assertEquals("World", content.get(0, 5));
//    }
//
//    @Test
//    void testDeleteAtEnd() {
//        content.insert(0, "Hello World", true);
//        content.delete(6, 11, true); // 删除 "World"
//        assertEquals(6, content.length());
//        assertEquals("Hello ", content.get(0, 6));
//    }
//
//    @Test
//    void testDeleteNewline() {
//        content.insert(0, "First\nSecond", true);
//        content.delete(5, 6, true); // 删除换行符
//        assertEquals(11, content.length());
//        assertEquals("FirstSecond", content.get(0, 11));
//    }
//
//    // ================== 极端情况测试 ==================
//
//    @Test
//    void testInsertEmptyText() {
//        content.insert(0, "Hello", true);
//        content.insert(2, "", true);
//        assertEquals(5, content.length());
//        assertEquals("Hello", content.get(0, 5));
//    }
//
//    @Test
//    void testDeleteZeroLength() {
//        content.insert(0, "Hello", true);
//        content.delete(3, 3, true);
//        assertEquals(5, content.length());
//        assertEquals("Hello", content.get(0, 5));
//    }
//
//    @Test
//    void testDeleteEntireContent() {
//        content.insert(0, "Hello\nWorld", true);
//        content.delete(0, content.length(), true);
//        assertEquals(0, content.length());
//        assertEquals("", content.get(0, 0));
//        assertEquals(1, content.paragraphs.size()); // 应保留一个空段落
//    }
//
//    @Test
//    void testInsertWithFiltering() {
//        // 测试过滤控制字符和非法字符
//        content.insert(0, "H\0e\tl\nl\ro", true);
//        assertEquals(7, content.length()); // H e l l o (包含换行)
//        assertEquals("He\tl\nlo", content.get(0, 7));
//    }
//
//    @Test
//    void testInsertAfterDeletion() {
//        content.insert(0, "Hello", true);
//        content.delete(0, 5, true);
//        content.insert(0, "World", true);
//        assertEquals(5, content.length());
//        assertEquals("World", content.get(0, 5));
//    }
//
//    @Test
//    void testDeleteAcrossMultipleParagraphs() {
//        content.addContentChangeListener(System.out::println);
//        content.insert(0, "Paragraph1\nParagraph2\nParagraph3", true);
//        content.delete(10, 20, true); // 删除 "Paragraph2\n" -> "Paragraph1\nParagraph3"
//        assertEquals(22, content.length()); // Paragraph1 + \n + Paragraph3
//        assertEquals("Paragraph12\nParagrap", content.get(0, 20));
//    }
//
//    @Test
//    void testInsertAtNewlinePosition() {
//        content.insert(0, "First\nSecond", true);
//        assertEquals("First\nSecond", content.get(0, 12));
//        content.insert(5, " Inserted", true);
//        assertEquals("First Inserted\nSecond",content.get(0, 21));
//        assertEquals(21, content.length());
//    }
//
//    // ================== 错误处理测试 ==================
//
//    @Test
//    void testInvalidInsertPosition() {
//        assertThrows(IndexOutOfBoundsException.class, () -> content.insert(-1, "Text", true));
//        assertThrows(IndexOutOfBoundsException.class, () -> content.insert(1, "Text", true));
//    }
//
//    @Test
//    void testInvalidDeleteRange() {
//        content.insert(0, "Hello", true);
//        assertThrows(IndexOutOfBoundsException.class, () -> content.delete(-1, 3, true));
//        assertThrows(IndexOutOfBoundsException.class, () -> content.delete(3, 10, true));
//        assertThrows(IndexOutOfBoundsException.class, () -> content.delete(4, 3, true));
//    }
//
//    @Test
//    void testInvalidGetRange() {
//        content.insert(0, "Hello", true);
//        assertThrows(IndexOutOfBoundsException.class, () -> content.get(-1, 3));
//        assertThrows(IndexOutOfBoundsException.class, () -> content.get(3, 10));
//        assertThrows(IndexOutOfBoundsException.class, () -> content.get(4, 3));
//    }
//
//    @Test
//    void testInsertNullText() {
//        assertThrows(IllegalArgumentException.class, () -> content.insert(0, (String) null, false));
//    }
//
//    // ================== 性能相关测试 ==================
//
//    @Test
//    void testLargeContentInsertion() {
//        StringBuilder largeText = new StringBuilder();
//        for (int i = 0; i < 1000; i++) {
//            largeText.append("Line ").append(i).append("\n");
//        }
//        content.insert(0, largeText.toString(), true);
//        assertEquals(1001, content.paragraphs.size());
//        assertEquals(largeText.length(), content.length());
//    }
//
//    @Test
//    void testLargeContentDeletion() {
//        StringBuilder largeText = new StringBuilder();
//        for (int i = 0; i < 1000; i++) {
//            largeText.append("Line ").append(i).append("\n");
//        }
//        content.insert(0, largeText.toString(), true);
//        content.delete(0, content.length(), true);
//
//        assertEquals(0, content.length());
//        assertEquals(1, content.paragraphs.size());
//    }
//
//    // ================== 混合操作测试 ==================
//
//    @Test
//    void testComplexOperations() {
//        // 初始插入
//        content.insert(0, "Start", true);
//        assertEquals(5, content.length());
//
//        // 插入换行和更多文本
//        content.insert(5, "\nMiddle\n", true);
//        assertEquals(13, content.length());
//
//        // 插入结尾
//        content.insert(13, "End", true);
//        assertEquals(16, content.length());
//
//        // 验证内容
//        assertEquals("Start\nMiddle\nEnd", content.get(0, 16));
//
//        // 删除中间部分
//        content.delete(6, 12, true); // 删除 "Middle"
//        assertEquals(10, content.length());
//        assertEquals("Start\n\nEn", content.get(0, 9));
//
//        // 删除空段落
//        content.delete(5, 6, true); // 删除空段落
//        assertEquals(9, content.length());
//        assertEquals("Start\nEn", content.get(0, 8));
//        // 插入到删除的位置
//        content.insert(5, " Inserted", true);
//        assertEquals(18, content.length());
//        assertEquals("Start Inserted\nEnd", content.get(0, 18));
//    }
//
//    // ================== 段落操作测试 ==================
//
//    @Test
//    void testParagraphManagement() {
//        content.insert(0, "P1\nP2\nP3", true);
//        assertEquals(3, content.paragraphs.size());
//
//        // 验证段落内容
//        assertEquals("P1", content.paragraphs.get(0).toString());
//        assertEquals("P2", content.paragraphs.get(1).toString());
//        assertEquals("P3", content.paragraphs.get(2).toString());
//
//        // 删除中间段落
//        content.delete(3, 6, true); // 删除 "P2\n"
//        assertEquals(2, content.paragraphs.size());
//        assertEquals("P1", content.paragraphs.get(0).toString());
//        assertEquals("P3", content.paragraphs.get(1).toString());
//
//        // 合并段落
//        content.delete(2, 3, true); // 删除换行符
//        assertEquals(1, content.paragraphs.size());
//        assertEquals("P1P3", content.paragraphs.getFirst().toString());
//    }
//
//    @Test
//    void testEmptyParagraphHandling() {
//        content.insert(0, "\n\n", true); // 插入两个空段落
//        assertEquals(2, content.length()); // 两个换行符
//        assertEquals(3, content.paragraphs.size()); // 三个空段落
//
//        // 删除一个空段落
//        content.delete(0, 1, true); // 删除第一个换行符
//        assertEquals(1, content.length()); // 一个换行符
//        assertEquals(2, content.paragraphs.size()); // 两个段落
//
//        // 删除所有内容
//        content.delete(0, content.length(), true);
//        assertEquals(0, content.length());
//        assertEquals(1, content.paragraphs.size()); // 保留一个空段落
//    }
//
//    // ================== 位置定位测试 ==================
//
//    @Test
//    void testPositionLocating() {
//        content.insert(0, "First\nSecond", true);
//
//        // 第一段内
//        BDTextAreaContent.Point pos1 = content.locatePosition(2); // 'r' in "First"
//        assertEquals(0, pos1.paragraph());
//        assertEquals(2, pos1.offset());
//
//        // 换行符位置
//        BDTextAreaContent.Point pos2 = content.locatePosition(5); // newline
//        assertEquals(0, pos2.paragraph());
//        assertEquals(5, pos2.offset());
//
//        // 第二段内
//        BDTextAreaContent.Point pos3 = content.locatePosition(7); // 'c' in "Second"
//        assertEquals(1, pos3.paragraph());
//        assertEquals(1, pos3.offset());
//
//        // 文档末尾
//        BDTextAreaContent.Point pos4 = content.locatePosition(11); // end of "Second"
//        assertEquals(1, pos4.paragraph());
//        assertEquals(5, pos4.offset());
//    }
//
//    @Test
//    void testInvalidPositionLocating() {
//        assertThrows(IndexOutOfBoundsException.class, () -> content.locatePosition(-1));
//    }
//
//    // ================== 过滤功能测试 ==================
//
//    @Test
//    void testInputFiltering() {
//        // 测试控制字符过滤
//        String filtered = Content.filterInput("H\0e\tl\nl\ro", true, false);
//        assertEquals("He\tllo", filtered);
//
//        // 测试不允许换行
//        filtered = Content.filterInput("Line1\nLine2", true, false);
//        assertEquals("Line1Line2", filtered);
//
//        // 测试不允许制表符
//        filtered = Content.filterInput("Text\tText", false, true);
//        assertEquals("TextText", filtered);
//    }
//
//}
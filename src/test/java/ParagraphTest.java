import com.xx.UI.complex.textArea.content.segment.NodeSegment;
import com.xx.UI.complex.textArea.content.segment.Paragraph;
import com.xx.UI.complex.textArea.content.segment.TextSegment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;

import static org.junit.jupiter.api.Assertions.*;

class ParagraphTest {
    private Paragraph paragraph;

    @BeforeEach
    void setUp() {
        paragraph = new Paragraph();
    }

    // ================== 空段落测试 ==================
    @Test
    void testEmptyParagraphOperations() {
        assertEquals(0, paragraph.getLength());
        assertEquals("", paragraph.toString());

        // 空段落插入
        paragraph.insertString(0, "Text");
        assertEquals("Text", paragraph.toString());

        // 空段落插入节点
        paragraph = new Paragraph();
        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        assertEquals(" ", paragraph.toString());
    }

    // ================== 边界测试 ==================
    @Test
    void testBoundaryInsertions() {
        paragraph.appendString("Hello");

        // 左边界插入
        paragraph.insertString(0, "Start ");
        assertEquals("Start Hello", paragraph.toString());

        // 右边界插入
        paragraph.insertString(paragraph.getLength(), " End");
        assertEquals("Start Hello End", paragraph.toString());

        // 中间插入
        paragraph.insertString(6, "Mid ");
        assertEquals("Start Mid Hello End", paragraph.toString());
    }

    @Test
    void testNegativeIndexThrows() {
        paragraph.appendString("Text");
        assertThrows(IndexOutOfBoundsException.class,
                () -> paragraph.insertString(-1, "Invalid"));
    }

    @Test
    void testIndexBeyondLengthThrows() {
        paragraph.appendString("Text");
        assertThrows(IndexOutOfBoundsException.class,
                () -> paragraph.insertString(10, "Invalid"));
    }

    // ================== 节点操作测试 ==================
    @Test
    void testNodeInsertionAndReplacement() {
        paragraph.appendString("Start");
        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        paragraph.appendString("End");

        assertEquals("Start End", paragraph.toString());

        // 节点段替换为文本
        paragraph.replaceString(5, 6, "Middle");
        assertEquals("StartMiddleEnd", paragraph.toString());

        // 文本中插入节点
        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        assertEquals("StartMiddleEnd ", paragraph.toString());
    }

    // ================== 跨段操作测试 ==================
    @Test
    void testCrossSegmentReplace() {
        // 创建多段结构: [Text][Node][Text][Node][Text]
        paragraph.appendString("A");
        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        paragraph.appendString("B");
        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        paragraph.appendString("C");

        // 跨三段替换 (A, N1, B)
        paragraph.replaceString(0, 3, "X");
        assertEquals("X C", paragraph.toString().replace("  ", " "));

        // 跨节点替换
        paragraph.replaceString(1, 3, "Y");
        assertEquals("XY", paragraph.toString().replace(" ", ""));
    }

    @Test
    void testFullParagraphReplacement() {
        paragraph.appendString("Original");
        paragraph.insertSegment(0, paragraph.getLength(), new TextSegment("Replaced"));
        assertEquals("Replaced", paragraph.toString());
    }

    // ================== 空操作测试 ==================
    @Test
    void testZeroLengthOperations() {
        paragraph.appendString("Text");
        int length = paragraph.getLength();

        // 零长度替换
        paragraph.replaceString(2, 2, "Inserted");
        assertEquals("TeInsertedxt", paragraph.toString());

        // 零长度删除
        paragraph.remove(3, 3);
        assertEquals("TeInsertedxt", paragraph.toString());

        // 空字符串追加
        paragraph.appendString("");
        assertEquals(length + "Inserted".length(), paragraph.getLength());
    }

    // ================== 段拆分测试 ==================
    @Test
    void testSegmentSplitting() {
        paragraph.appendString("ABCDE");

        // 中间插入节点
        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        assertEquals("ABCDE ", paragraph.toString());

        paragraph.replaceString(1, 4, "X");
        assertEquals("AXE ", paragraph.toString());
    }

    // ================== 并发修改测试 ==================
    @Test
    void testConsecutiveOperations() {
        // 初始状态
        paragraph.appendString("Start");

        // 序列操作
        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        paragraph.insertString(6, "Mid");
        paragraph.replaceString(5, 9, "");
        paragraph.appendString("End");

        assertEquals("StartEnd", paragraph.toString());
    }

    // ================== 极端范围测试 ==================
    @Test
    void testFullRangeDeletion() {
        paragraph.appendString("Content");
        paragraph.remove(0, paragraph.getLength());
        assertEquals("", paragraph.toString());
        assertEquals(0, paragraph.getLength());
    }

    @Test
    void testInvalidRangeThrows() {
        paragraph.appendString("Text");
        assertThrows(IndexOutOfBoundsException.class,
                () -> paragraph.replaceString(3, 10, "Invalid"));

        assertThrows(IndexOutOfBoundsException.class,
                () -> paragraph.insertSegment(-1, 5, new TextSegment("")));
    }

    // ================== 节点边界测试 ==================
    @Test
    void testNodeBoundaryOperations() {
        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        paragraph.appendString("Text");

        // 节点左边界插入
        paragraph.insertString(0, "Start");
        assertEquals("Start Text", paragraph.toString());

        // 节点右边界插入
        paragraph.insertString(6, "End");
        assertEquals("Start EndText", paragraph.toString());
    }

    // ================== 混合类型测试 ==================
    @Test
    void testMixedTypeInsertion() {
        paragraph.insertSegment(0, new TextSegment("A"));
        paragraph.insertSegment(1, new NodeSegment<>("N", _ -> null));
        paragraph.insertSegment(2, new TextSegment("B"));

        assertEquals("A B", paragraph.toString());
        assertEquals(3, paragraph.getSegments().size());

        // 混合类型替换
        paragraph.insertSegment(1, 2, new TextSegment("X"));
        assertEquals("AXB", paragraph.toString());
    }

    // ================== 性能相关测试 ==================
    @Test
    void testLengthCacheConsistency() {
        paragraph.appendString("123");
        int initialLength = paragraph.getLength();

        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        assertNotEquals(initialLength, paragraph.getLength());

        paragraph.remove(2, 4);
        assertEquals(2, paragraph.getLength());
    }

    // ================== 空输入测试 ==================
    @Test
    void testNullInputHandling() {
        assertThrows(NullPointerException.class,
                () -> paragraph.appendString(null));

        assertThrows(NullPointerException.class,
                () -> paragraph.insertSegment(0, null));

        assertThrows(NullPointerException.class,
                () -> paragraph.replaceString(0, 1, null));
    }

    @Test
    void testGetTextEdgeCases() {
        // 空段落
        assertEquals("", paragraph.getText(0, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> paragraph.getText(0, 1));

        // 单文本段 Hello
        paragraph.appendString("Hello");
        assertEquals("Hello", paragraph.getText(0, 5));
        assertEquals("ell", paragraph.getText(1, 4));
        assertEquals("o", paragraph.getText(4, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> paragraph.getText(4, 6));
        //    Hello (node为空格)
        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        assertEquals("H", paragraph.getText(0, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> paragraph.getText(0, 7));

        // 混合段落 Hello Text (node为空格)End
        paragraph.appendString("Text");
        paragraph.insertSegment(paragraph.getLength(),new NodeSegment<>("t", _ -> null));
        paragraph.appendString("End");

        // 跨段提取
        assertEquals("Hello ", paragraph.getText(0, 6));
        assertEquals("llo ", paragraph.getText(2, 6));

        // 部分节点+文本
        assertEquals(" T", paragraph.getText(5, 7));

        // 全范围提取
        assertEquals("Hello Tex", paragraph.getText(0, 9));

        // 零长度提取
        assertEquals("", paragraph.getText(3, 3));

        // 无效范围
        assertThrows(IndexOutOfBoundsException.class, () -> paragraph.getText(-1, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> paragraph.getText(3, 15));
        assertThrows(IndexOutOfBoundsException.class, () -> paragraph.getText(5, 4));
    }
}
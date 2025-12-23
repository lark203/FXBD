//import com.xx.UI.complex.textArea.content.BDTextAreaContent;
//import com.xx.UI.complex.textArea.content.Command;
//import com.xx.UI.complex.textArea.content.HistoryMemos;
//import com.xx.UI.complex.textArea.content.segment.NodeSegment;
//import com.xx.UI.complex.textArea.content.segment.TextSegment;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.Serializable;
//import java.util.Objects;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class BDTextAreaContentCommandTest {
//    private BDTextAreaContent content;
//    private HistoryMemos history;
//    private final AtomicInteger changeCount = new AtomicInteger(0);
//    private long testStartTime;
//
//    @BeforeEach
//    void setUp() {
//        content = new BDTextAreaContent();
//        history = content.historyMemos;
//        changeCount.set(0);
//        content.addChangeRunnable(changeCount::incrementAndGet);
//        testStartTime = System.currentTimeMillis();
//    }
//
//    // 辅助方法：等待超过命令合并间隔
//    private void waitForMergeInterval() throws InterruptedException {
//        Thread.sleep(BDTextAreaContent.CHANGED__INTERVALS_TIME + 100);
//    }
//
//    // 1. 基础撤销重做测试
//    @Test
//    void testBasicUndoRedo() {
//        // 初始状态验证
//        assertEquals("", content.toString());
//        assertEquals(0, changeCount.get());
//
//        // 插入文本
//        content.insert(0, "Hello", true);
//        assertEquals("Hello", content.toString());
//        assertEquals(1, changeCount.get());
//
//        // 撤销
//        history.undo();
//        assertEquals("", content.toString());
//        assertEquals(2, changeCount.get());
//
//        // 重做
//        history.redo();
//        assertEquals("Hello", content.toString());
//        assertEquals(3, changeCount.get());
//    }
//
//    // 2. 连续操作合并测试
//    @Test
//    void testCommandMerging() throws InterruptedException {
//        // 连续插入应合并
//        content.insert(0, "A", true);
//        waitForMergeInterval(); // 等待超过合并间隔
//        content.insert(1, "B", true);
//        assertEquals(2, history.getCommandHistory().size());
//        assertEquals("AB", content.toString());
//
//        // 换行符中断合并
//        content.insert(2, "\n", true);
//        content.insert(3, "C", true);
//        assertEquals(4, history.getCommandHistory().size());
//        assertEquals("AB\nC", content.toString());
//
//        content.insert(4, "D", true);
//        waitForMergeInterval(); // 等待超过合并间隔
//        content.insert(5, "E", true);
//        assertEquals(6, history.getCommandHistory().size());
//        assertEquals("AB\nCDE", content.toString());
//
//        // 混合操作不合并
//        content.delete(0, 1, true); // 删除'A'
//        content.insert(0, "X", true); // 插入'X'
//        assertEquals(8, history.getCommandHistory().size());
//        assertEquals("XB\nCDE", content.toString());
//    }
//
//    // 3. 段落边界测试
//    @Test
//    void testParagraphBoundaries() {
//        // 创建多段落内容
//        content.insert(0, "Line1\nLine2\nLine3", true);
//        assertEquals(3, content.paragraphs.size());
//        assertEquals("Line1\nLine2\nLine3", content.toString());
//
//        // 删除段落边界
//        content.delete(5, 6, true); // 删除第一个换行符
//        assertEquals(2, content.paragraphs.size());
//        assertEquals("Line1Line2\nLine3", content.toString());
//
//        // 撤销段落合并
//        history.undo();
//        assertEquals(3, content.paragraphs.size());
//        assertEquals("Line1\nLine2\nLine3", content.toString());
//
//        // 在段落开头插入
//        content.insert(6, "Start:", true); // 在Line2开头插入
//        assertEquals("Line1\nStart:Line2\nLine3", content.toString());
//
//        // 在段落末尾插入
//        content.insert(content.length(), "End", true);
//        assertEquals("Line1\nStart:Line2\nLine3End", content.toString());
//
//        history.undo();
//        history.undo();
//        assertEquals("Line1\nLine2\nLine3", content.toString());
//    }
//
//    // 4. 极端位置操作测试
//    @Test
//    void testEdgeCases() {
//        // 空文档操作
//        assertDoesNotThrow(() -> content.delete(0, 0, true));
//
//        // 文档开头插入
//        content.insert(0, "Start", true);
//
//        // 文档末尾插入
//        content.insert(content.length(), "End", true);
//        assertEquals("StartEnd", content.toString());
//
//        // 越界操作
//        assertThrows(IndexOutOfBoundsException.class,
//            () -> content.insert(-1, "Invalid", true));
//        assertThrows(IndexOutOfBoundsException.class,
//            () -> content.delete(100, 200, true));
//
//        // 插入空文本
//        assertDoesNotThrow(() -> content.insert(0, "", true));
//        assertEquals("StartEnd", content.toString()); // 内容不变
//    }
//
//    // 5. 混合操作测试
//    @Test
//    void testMixedOperations() {
//        // 初始内容
//        content.insert(0, "Initial", true);
//        assertEquals("Initial", content.toString());
//        // 操作序列：删除->插入->替换
//        content.delete(0, 4, true);  // 删除 "Init"
//        assertEquals("ial", content.toString());
//        content.insert(0, "New", true); // 插入 "New"
//        assertEquals("Newial", content.toString());
//        content.replace(3, 6, "Text", true); // 替换 "ial" 为 "Text"
//        assertEquals("NewText", content.toString());
//
//        // 逐步撤销
//        history.undo(); // 撤销删除
//        assertEquals("New", content.toString());
//        history.undo(); // 撤销插入
//        assertEquals("Newial", content.toString());
//
//        history.undo(); // 撤销插入
//        assertEquals("ial", content.toString());
//
//        history.undo(); // 撤销删除
//        assertEquals("Initial", content.toString());
//
//        // 重做序列
//        history.redo(); // 重做删除
//        assertEquals("ial", content.toString());
//
//        history.redo(); // 重做插入
//        assertEquals("Newial", content.toString());
//
//        history.redo(); // 重做替换
//        assertEquals("New", content.toString());
//
//        // 中途执行新操作
//        content.insert(0, "Prefix:", true);
//        assertEquals("Prefix:New", content.toString());
//
//        // 再次撤销应回到最新状态
//        history.undo();
//        assertEquals("New", content.toString());
//    }
//
//    // 6. NodeSegment操作测试
//    @Test
//    void testNodeSegmentOperations() {
//        // 创建节点段
//        NodeSegment<String> segment = new NodeSegment<>(" ",_->null);
//
//        // 插入节点
//        content.insert(0, segment, true);
//        assertEquals(" ", content.toString());
//
//        // 替换节点为文本
//        content.replace(0, 1, "TEXT", true);
//        assertEquals("TEXT", content.toString());
//
//        // 撤销替换
//        history.undo();
//        history.undo();
//        assertEquals(" ", content.toString());
//
//        // 删除节点
//        content.delete(0, 1, true);
//        assertEquals("", content.toString());
//
//        // 重做删除
//        history.undo();
//        assertEquals(" ", content.toString());
//
//        // 撤销删除
//        history.redo();
//        assertEquals("", content.toString());
//
//        // 混合节点和文本
//        content.insert(0, "Start-", true);
//        content.insert(content.length(), "-End", true);
//        assertEquals("Start--End", content.toString());
//
//        // 撤销混合操作
//        history.undo();
//        history.undo();
//        assertEquals("", content.toString());
//    }
//
//    // 7. 压力测试
//    @Test
//    void testStressOperations() {
//        // 创建初始内容
//        content.insert(0, "Base", true);
//
//        // 执行大量操作
//        for (int i = 0; i < 1000; i++) {
//            if (i % 10 == 0) {
//                content.insert(0, "\n", true); // 每10次插入换行
//            } else {
//                content.insert(0, i % 10 + "", true);
//            }
//
//            // 随机执行撤销/重做
//            if (i % 7 == 0 && history.canUndo()) {
//                history.undo();
//            } else if (i % 5 == 0 && history.canRedo()) {
//                history.redo();
//            }
//        }
//
//        final int finalCommandCount = history.getCommandHistory().size();
//        final String finalContent = content.toString();
//
//        // 连续撤销到初始状态
//        while (history.canUndo()) {
//            history.undo();
//        }
//        history.redo();
//        assertEquals("Base", content.toString());
//
//        // 连续重做到最终状态
//        while (history.canRedo()) {
//            history.redo();
//        }
//        String[] finalLines = finalContent.split("\n");
//        String[] newLines = content.toString().split("\n");
//        for (int i = 0; i < newLines.length; i++) {
//            if (!Objects.equals(finalLines[i], newLines[i])) System.out.println(i + ": " + finalLines[i] + " -> " + newLines[i]);
//        }
//        assertEquals(finalContent, content.toString());
//        assertEquals(finalCommandCount, history.getCommandHistory().size());
//
//        // 内容长度验证
//        System.out.println("Content length: " + content.length());
//    }
//
//    // 8. 撤销重做边界测试
//    @Test
//    void testUndoRedoBoundaries() {
//        // 无操作时边界检查
//        assertFalse(history.canUndo());
//        assertFalse(history.canRedo());
//
//        // 单操作撤销边界
//        content.insert(0, "Test", true);
//        assertTrue(history.canUndo());
//        assertFalse(history.canRedo());
//
//        // 撤销后重做边界
//        history.undo();
//        assertFalse(history.canUndo());
//        assertTrue(history.canRedo());
//
//        // 重做后边界
//        history.redo();
//        assertTrue(history.canUndo());
//        assertFalse(history.canRedo());
//
//        // 新操作后重做栈清空
//        content.insert(0, "New", true);
//        assertTrue(history.canUndo());
//        assertFalse(history.canRedo());
//    }
//
//    // 9. 复杂替换操作测试
//    @Test
//    void testComplexReplaceOperations() {
//        // 准备多段落内容
//        content.insert(0, "Para1\nPara2\nPara3", true);
//
//        // 跨段落替换
//        content.replace(3, 10, "REPLACED", true); // 替换"a1\nPar"
//        assertEquals("ParREPLACED2\nPara3", content.toString());
//
//        // 撤销替换
//        history.undo();
//        history.undo();
//        assertEquals("Para1\nPara2\nPara3", content.toString());
//
//        // 替换为节点段
//        NodeSegment<String> segment = new NodeSegment<>(" ",_->null);
//        content.replace(6, 11, segment, true); // 替换"Para2"
//        assertEquals("Para1\n \nPara3", content.toString());
//
//        // 跨段落删除
//        content.delete(0, content.length(), true);
//        assertEquals("", content.toString());
//
//        // 多次撤销
//        history.undo(); // 撤销删除
//        assertEquals("Para1\n \nPara3", content.toString());
//        history.undo(); // 撤销节点替换
//        history.undo(); // 撤销节点替换
//        assertEquals("Para1\nPara2\nPara3", content.toString());
//    }
//}
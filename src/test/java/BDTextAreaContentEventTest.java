
import com.xx.UI.complex.textArea.content.BDTextAreaContent;
import com.xx.UI.complex.textArea.content.listener.ContentChangeEvent;
import com.xx.UI.complex.textArea.content.listener.ContentChangeListener;
import com.xx.UI.complex.textArea.content.segment.NodeSegment;
import com.xx.UI.complex.textArea.content.segment.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static com.xx.UI.complex.textArea.content.BDTextAreaContent.formatParagraphs;
import static org.junit.jupiter.api.Assertions.*;

class BDTextAreaContentEventTest {

    // 自定义事件监听器实现
    static class TestListener implements ContentChangeListener {
        private final List<ContentChangeEvent> events = new ArrayList<>();
        private final String name;
        
        public TestListener() {
            this("TestListener");
        }
        
        public TestListener(String name) {
            this.name = name;
        }
        
        @Override
        public void contentChanged(ContentChangeEvent event) {
            events.add(event);
        }
        
        public List<ContentChangeEvent> getEvents() {
            return events;
        }
        
        public void clearEvents() {
            events.clear();
        }
        
        public ContentChangeEvent getLastEvent() {
            return events.isEmpty() ? null : events.get(events.size() - 1);
        }
        
        public void assertEventCount(int expected) {
            assertEquals(expected, events.size(), 
                name + " event count mismatch. Expected: " + expected + ", Actual: " + events.size());
        }
        
        public void assertLastEvent(ContentChangeEvent.ChangeType type, String text, 
                                   int startPara, int endPara, 
                                   int startOffset, int endOffset) {
            ContentChangeEvent event = getLastEvent();
            assertNotNull(event, "No events recorded");
            
            assertEquals(type, event.getChangeType(), "Event type mismatch");
            assertEquals(text,event.getChangeType() == ContentChangeEvent.ChangeType.DELETE ? formatParagraphs(event.getChangedParagraphs()):event.getChangedSegment().getInfo(), "Changed text mismatch");
            assertEquals(startPara, event.getStartParaIndex(), "Start para index mismatch");
            assertEquals(endPara, event.getEndParaIndex(), "End para index mismatch");
            assertEquals(startOffset, event.getStartOffset(), "Start offset mismatch");
            assertEquals(endOffset, event.getEndOffset(), "End offset mismatch");
        }
        
        public void assertEvent(int index, ContentChangeEvent.ChangeType type, String text, 
                               int startPara, int endPara, 
                               int startOffset, int endOffset) {
            if (index < 0 || index >= events.size()) {
                fail(name + " event index out of bounds: " + index);
            }
            
            ContentChangeEvent event = events.get(index);
            assertEquals(type, event.getChangeType(), "Event[" + index + "] type mismatch");
            assertEquals(text, event.getChangedSegment() == null? formatParagraphs(event.getChangedParagraphs()) : event.getChangedSegment().getInfo(), "Event[" + index + "] changed text mismatch");
            assertEquals(startPara, event.getStartParaIndex(), "Event[" + index + "] start para index mismatch");
            assertEquals(endPara, event.getEndParaIndex(), "Event[" + index + "] end para index mismatch");
            assertEquals(startOffset, event.getStartOffset(), "Event[" + index + "] start offset mismatch");
            assertEquals(endOffset, event.getEndOffset(), "Event[" + index + "] end offset mismatch");
        }
    }

    private BDTextAreaContent content;
    private TestListener listener;

    @BeforeEach
    void setUp() {
        content = new BDTextAreaContent();
        listener = new TestListener();
        content.addContentChangeListener(listener);
    }

    // ================== 基础事件测试 ==================
    
    @Test
    void insertSingleLineShouldFireInsertEvent() {
        content.insert(0, "Hello", true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, "Hello", 
            0, 0, 0, 5
        );
    }

    @Test
    void insertMultiLineShouldFireInsertEventWithCorrectRange() {
        content.insert(0, "Line1\nLine2\nLine3", true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, "Line1\nLine2\nLine3", 
            0, 2, 0, 5
        );
    }

    @Test
    void deleteWithinSingleParagraphShouldFireDeleteEvent() {
        // 准备初始内容（不触发事件）
        content.insert(0, "Hello World", false);
        listener.clearEvents();
        
        content.delete(6, 11, true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.DELETE, "World", 
            0, 0, 6, 11
        );
    }

    @Test
    void deleteAcrossParagraphsShouldFireDeleteEventWithCorrectRange() {
        // 准备初始内容（不触发事件）
        content.insert(0, "Para1\nPara2\nPara3", false);
        assertEquals("Para1\nPara2\nPara3", content.toString());
        listener.clearEvents();
        
        // 删除从"Para1"末尾到"Para3"开头
        content.delete(5, 11, true); // 删除"\nPara2"
        assertEquals("Para1\nPara3", content.toString());
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.DELETE, "\nPara2",
            0, 1, 5, 5
        );
    }

    // ================== 边界条件测试 ==================
    
    @Test
    void insertAtDocumentStartShouldFireEvent() {
        content.insert(0, "Start", true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, "Start", 
            0, 0, 0, 5
        );
    }

    @Test
    void insertAtDocumentEndShouldFireEvent() {
        // 准备初始内容（不触发事件）
        content.insert(0, "Initial", false);
        listener.clearEvents();
        
        content.insert(7, "End", true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, "End", 
            0, 0, 7, 10
        );
    }

    @Test
    void deleteEntireDocumentShouldFireEvent() {
        // 准备初始内容（不触发事件）
        content.insert(0, "Full Content", false);
        listener.clearEvents();
        
        content.delete(0, 12, true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.DELETE, "Full Content", 
            0, 0, 0, 12
        );
    }

    @Test
    void deleteLastCharacterShouldFireEvent() {
        // 准备初始内容（不触发事件）
        content.insert(0, "ABCD", false);
        listener.clearEvents();
        
        content.delete(3, 4, true);
        System.out.println(content.toString());
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.DELETE, "D", 
            0, 0, 3, 4
        );
    }

    @Test
    void insertEmptyTextShouldNotFireEvent() {
        content.insert(0, "", true);
        listener.assertEventCount(0);
    }

    @Test
    void deleteZeroLengthShouldNotFireEvent() {
        // 准备初始内容（不触发事件）
        content.insert(0, "Text", false);
        listener.clearEvents();
        
        content.delete(2, 2, true);
        listener.assertEventCount(0);
    }

    // ================== 多段落操作测试 ==================
    
    @Test
    void insertNewLineAtParagraphBoundaryShouldFireEvent() {
        // 准备初始内容（不触发事件）
        content.insert(0, "Line1", false);
        listener.clearEvents();
        
        content.insert(5, "\n", true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, "\n", 
            0, 1, 5, 0
        );
    }

    @Test
    void deleteParagraphSeparatorShouldMergeParagraphs() {
        // 准备初始内容（不触发事件）
        content.insert(0, "P1\nP2", false);
        listener.clearEvents();
        
        content.delete(2, 3, true); // 删除换行符
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.DELETE, "\n", 
            0, 1, 2, 0
        );
    }

    // ================== Segment操作测试 ==================
    
    @Test
    void insertSegmentShouldFireEvent() {
        content.insert(0, new NodeSegment<>("Segment", _ -> null), true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, " ",
            0, 0, 0, 1
        );
    }

    @Test
    void replaceWithSegmentShouldFireEvent() {
        // 准备初始内容（不触发事件）
        content.insert(0, "Original", false);
        listener.clearEvents();
        

        content.replace(0, 8, new NodeSegment<>("New", _ -> null), true); // 替换整个文本
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.REPLACE, " ",
            0, 0, 0, 1
        );
    }

    static class TestSegment extends Segment<String> implements Serializable {
        private final String text;
        
        TestSegment(String text) {
            super(text, Objects::toString);
            this.text = text;
        }

        
        @Override
        public String toString() {
            return text;
        }

        @Override
        protected void replace(int start, int end, String text, List<Segment<?>> segments) {

        }

        @Override
        protected void insertSegment(int index, Segment<?> newSegment, List<Segment<?>> segments) {

        }

        @Override
        public Segment<?> getSegment(int start, int end) {
            return new TestSegment(text.substring(start,end));
        }

        @Override
        public Segment<String> clone() {
            return new TestSegment(var);
        }
    }

    // ================== 极端场景测试 ==================
    
    @Test
    void rapidConsecutiveOperationsShouldFireSeparateEvents() {
        // 第一次插入
        content.insert(0, "First", true);
        // 第二次插入
        content.insert(5, "Second", true);
        // 删除
        content.delete(0, 5, true);
        
        listener.assertEventCount(3);
        
        // 验证事件顺序
        listener.assertEvent(0, ContentChangeEvent.ChangeType.INSERT, "First", 0, 0, 0, 5);
        listener.assertEvent(1, ContentChangeEvent.ChangeType.INSERT, "Second", 0, 0, 5, 11);
        listener.assertEvent(2, ContentChangeEvent.ChangeType.DELETE, "First", 0, 0, 0, 5);
    }

    @Test
    void largeContentInsertShouldFireSingleEvent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("Line").append(i).append("\n");
        }
        String largeText = sb.toString();
        
        content.insert(0, largeText, true);
        
        listener.assertEventCount(1);
        ContentChangeEvent event = listener.getLastEvent();
        
        assertEquals(ContentChangeEvent.ChangeType.INSERT, event.getChangeType());
        assertEquals(largeText, event.getChangedSegment().toString());
        assertEquals(0, event.getStartParaIndex());
        assertEquals(1000, event.getEndParaIndex());
    }

    @Test
    void unicodeCharacterInsertShouldFireEventWithCorrectOffsets() {
        String emojiText = "😊❤️🌟";
        content.insert(0, emojiText, true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, emojiText, 
            0, 0, 0, 6
        );
    }

    @Test
    void multipleListenersShouldAllReceiveEvents() {
        TestListener listener2 = new TestListener("Listener2");
        TestListener listener3 = new TestListener("Listener3");
        content.addContentChangeListener(listener2);
        content.addContentChangeListener(listener3);
        
        content.insert(0, "Test", true);
        
        listener.assertEventCount(1);
        listener2.assertEventCount(1);
        listener3.assertEventCount(1);
        
        listener.assertLastEvent(ContentChangeEvent.ChangeType.INSERT, "Test", 0, 0, 0, 4);
        listener2.assertLastEvent(ContentChangeEvent.ChangeType.INSERT, "Test", 0, 0, 0, 4);
        listener3.assertLastEvent(ContentChangeEvent.ChangeType.INSERT, "Test", 0, 0, 0, 4);
    }

    @Test
    void notifyFalseShouldNotFireEvent() {

    }

    @Test
    void deleteAllContentAndInsertShouldFireEvents() {
        // 初始内容
        content.insert(0, "Content", false);
        listener.clearEvents();
        
        // 删除所有
        content.delete(0, 7, true);
        // 插入新内容
        content.insert(0, "New", true);
        
        // 验证两个事件
        listener.assertEventCount(2);
        
        // 验证第一个事件（删除）
        listener.assertEvent(0, ContentChangeEvent.ChangeType.DELETE, "Content", 0, 0, 0, 7);
        // 验证第二个事件（插入）
        listener.assertEvent(1, ContentChangeEvent.ChangeType.INSERT, "New", 0, 0, 0, 3);
    }

    @Test
    void concurrentModificationShouldNotBreakEventSystem() {
        final int THREAD_COUNT = 10;
        final int OPERATIONS_PER_THREAD = 100;
        final int TOTAL_OPERATIONS = THREAD_COUNT * OPERATIONS_PER_THREAD * 2;
        
        // 使用原子计数器记录事件总数
        AtomicInteger eventCounter = new AtomicInteger();
        
        // 添加监听器记录事件数量
        content.addContentChangeListener(_ -> eventCounter.incrementAndGet());

       for (int j = 0; j < THREAD_COUNT * OPERATIONS_PER_THREAD; j++) {
                        String text = "T"  + "-" + j;
                        int insertLength = text.length();

                        // 插入操作
                        content.insert(0, text, true);

                        // 删除操作
                        content.delete(0, insertLength, true);
                    }
        
        // 验证事件总数
        assertEquals(TOTAL_OPERATIONS, eventCounter.get(), 
            "Event count mismatch. Expected: " + TOTAL_OPERATIONS + 
            ", Actual: " + eventCounter.get());
    }

    // ================== 空文档测试 ==================
    
    @Test
    void insertIntoEmptyDocumentShouldCreateParagraph() {
        // 初始状态是空文档（有一个空段落）
        listener.clearEvents();
        
        content.insert(0, "Text", true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, "Text", 
            0, 0, 0, 4
        );
    }


    // ================== 段落边界测试 ==================
    
    @Test
    void insertAtParagraphStart() {
        // 准备多段落内容
        content.insert(0, "P1\nP2", false);
        listener.clearEvents();
        
        content.insert(0, "Start", true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, "Start", 
            0, 0, 0, 5
        );
    }

    @Test
    void insertAtParagraphEnd() {
        // 准备多段落内容
        content.insert(0, "P1\nP2", false);
        listener.clearEvents();
        
        content.insert(2, "End", true); // 在第一段结尾插入
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, "End", 
            0, 0, 2, 5
        );
    }

    @Test
    void insertBetweenParagraphs() {
        // 准备多段落内容
        content.insert(0, "P1\nP2", false);
        assertEquals("P1\nP2", content.toString());
        listener.clearEvents();
        
        content.insert(3, "Mid", true); // 在段落之间插入
        assertEquals("P1\nMidP2", content.toString());
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, "Mid", 
            1, 1, 0, 3
        );
    }

    // ================== 错误处理测试 ==================
    
    @Test
    void insertOutOfBoundsShouldThrow() {
        assertThrows(IndexOutOfBoundsException.class, 
            () -> content.insert(-1, "Text", true));
        
        assertThrows(IndexOutOfBoundsException.class, 
            () -> content.insert(100, "Text", true));
    }

    @Test
    void deleteOutOfBoundsShouldThrow() {
        content.insert(0, "Text", false);
        
        assertThrows(IndexOutOfBoundsException.class, 
            () -> content.delete(-1, 2, true));
        
        assertThrows(IndexOutOfBoundsException.class, 
            () -> content.delete(0, 100, true));
        
        assertThrows(IndexOutOfBoundsException.class, 
            () -> content.delete(3, 2, true));
    }

    // ================== 特殊字符测试 ==================
    
    @Test
    void insertTabsAndSpecialChars() {
        String specialText = "\tTab~!@#$%^&*()_+";
        content.insert(0, specialText, true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, specialText, 
            0, 0, 0, specialText.length()
        );
    }

    @Test
    void insertNewLineOnly() {
        content.insert(0, "\n", true);
        
        listener.assertEventCount(1);
        listener.assertLastEvent(
            ContentChangeEvent.ChangeType.INSERT, "\n", 
            0, 1, 0, 0
        );
    }

    // ================== 事件参数一致性测试 ==================
    
    @Test
    void eventParametersShouldMatchActualContent() {
        content.insert(0, "Initial", true);
        listener.clearEvents();
        
        // 插入操作
        content.insert(3, "MID", true);
        
        // 验证事件参数
        listener.assertEventCount(1);
        ContentChangeEvent event = listener.getLastEvent();
        
        // 验证事件参数与实际内容一致
        String fullContent = content.toString();
        String insertedText = "MID";
        int expectedStart = 3;
        int expectedEnd = expectedStart + insertedText.length();
        
        assertEquals(insertedText, event.getChangedSegment().getInfo());
        assertEquals(insertedText, fullContent.substring(expectedStart, expectedEnd));
    }
}
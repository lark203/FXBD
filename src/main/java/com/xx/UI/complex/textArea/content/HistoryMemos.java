package com.xx.UI.complex.textArea.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryMemos implements Serializable {

    public static int MAX_HISTORY_SIZE = 5000; // 默认历史记录最大数量
    private final List<Command> commandHistory = new ArrayList<>();
    private Command currentActiveCommand;
    private Long lastExecutionTimestamp;

    public HistoryMemos() {
    }

    /**
     * 获取当前历史记录最大数量
     */
    public int getMAX_HISTORY_SIZE() {
        return MAX_HISTORY_SIZE;
    }

    /**
     * 设置历史记录最大数量
     *
     * @param maxSize 最大历史记录数量，必须大于0
     */
    public void setMAX_HISTORY_SIZE(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("最大历史记录大小必须大于0");
        }
        MAX_HISTORY_SIZE = maxSize;
        trimHistoryToSize(); // 应用新的大小限制
    }

    /**
     * 执行新命令并更新历史记录
     */
    public void execute(Command newCommand) {
        Objects.requireNonNull(newCommand, "命令不能为空");
        newCommand.execute();

        removeFutureRedoCommands();
        if (shouldCreateNewHistoryEntry()) {
            addAsNewHistoryEntry(newCommand);
        } else {
            mergeWithCurrentCommand(newCommand);
        }

        updateLastExecutionTime();
        trimHistoryToSize(); // 确保不超过最大限制
    }

    /**
     * 清除当前命令之后的所有命令（重做分支）
     */
    private void removeFutureRedoCommands() {
        if (currentActiveCommand == null || commandHistory.isEmpty()) return;
        int currentIndex = commandHistory.indexOf(currentActiveCommand);
        if (currentIndex >= 0 && currentIndex < commandHistory.size() - 1) {
            commandHistory.subList(currentIndex + 1, commandHistory.size()).clear();
        }
    }

    /**
     * 判断是否需要创建新的历史记录项
     */
    private boolean shouldCreateNewHistoryEntry() {
        return currentActiveCommand == null ||
                lastExecutionTimestamp == null ||
                System.currentTimeMillis() - lastExecutionTimestamp > BDTextAreaContent.CHANGED__INTERVALS_TIME;
    }

    /**
     * 添加新命令到历史记录
     */
    private void addAsNewHistoryEntry(Command command) {
        currentActiveCommand = command;
        commandHistory.add(command);
    }

    /**
     * 尝试合并到当前命令
     */
    private void mergeWithCurrentCommand(Command command) {
        if (currentActiveCommand != null && currentActiveCommand.merge(command)) return;
        addAsNewHistoryEntry(command);
    }

    /**
     * 更新最后执行时间戳
     */
    private void updateLastExecutionTime() {
        lastExecutionTimestamp = System.currentTimeMillis();
    }

    /**
     * 确保历史记录不超过最大限制
     */
    private void trimHistoryToSize() {
        while (commandHistory.size() > MAX_HISTORY_SIZE) {
            // 移除最旧的命令
            Command removedCommand = commandHistory.removeFirst();

            // 如果移除的是当前活动命令，需要更新状态
            if (removedCommand == currentActiveCommand) {
                if (!commandHistory.isEmpty()) {
                    // 如果还有命令，指向下一个命令
                    currentActiveCommand = commandHistory.getFirst();
                } else {
                    currentActiveCommand = null;
                }
            }
        }
    }

    /**
     * 撤销当前命令
     */
    public void undo() {
        if (currentActiveCommand == null) return;
        if (currentActiveCommand instanceof ContentCommand contentCommand) {
            if (contentCommand instanceof DeleteCommand deleteCommand && !deleteCommand.startCaret.equals(contentCommand.content.getCaretPosition())) {
                contentCommand.content.setCaretPosition(deleteCommand.startCaret);
                return;
            } else if (contentCommand instanceof InsertCommand insertCommand && !insertCommand.endCaret.equals(contentCommand.content.getCaretPosition())) {
                contentCommand.content.setCaretPosition(insertCommand.endCaret);
                return;
            }
        }
        currentActiveCommand.unexecute();
        if (hasPreviousCommand()) moveToPreviousCommand();
        else currentActiveCommand = null;
    }

    /**
     * 重做下一个命令
     */
    public void redo() {
        if (!canRedo()) return;
        if (currentActiveCommand == null)
            currentActiveCommand = commandHistory.getFirst();
        else
            currentActiveCommand = getNextCommand();
        currentActiveCommand.execute();
    }

    private boolean hasPreviousCommand() {
        return commandHistory.indexOf(currentActiveCommand) > 0;
    }

    private void moveToPreviousCommand() {
        int currentIndex = commandHistory.indexOf(currentActiveCommand);
        currentActiveCommand = commandHistory.get(currentIndex - 1);
    }

    private Command getNextCommand() {
        int currentIndex = commandHistory.indexOf(currentActiveCommand);
        return commandHistory.get(currentIndex + 1);
    }

    public boolean canUndo() {
        return currentActiveCommand != null;
    }

    public boolean canRedo() {
        if (commandHistory.isEmpty()) return false;
        return currentActiveCommand != commandHistory.getLast();
    }

    public void clear() {
        commandHistory.clear();
        currentActiveCommand = null;
        lastExecutionTimestamp = null;
    }

    // 获取历史命令列表（仅供测试/调试使用）
    public List<Command> getCommandHistory() {
        return new ArrayList<>(commandHistory);
    }

    /**
     * 获取当前活动命令在历史记录中的索引
     * 用于调试和测试
     */
    public int getCurrentCommandIndex() {
        if (currentActiveCommand == null) return -1;
        return commandHistory.indexOf(currentActiveCommand);
    }
}
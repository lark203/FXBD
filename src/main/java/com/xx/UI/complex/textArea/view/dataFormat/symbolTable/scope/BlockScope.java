package com.xx.UI.complex.textArea.view.dataFormat.symbolTable.scope;

public class BlockScope extends BaseScope {
    public enum BlockType { GENERAL, FOR, WHILE, IF, TRY, CATCH }
    private final BlockType blockType;
    
    public BlockScope(BlockType blockType, Scope enclosingScope) {
        super("Block:" + blockType.name(), enclosingScope);
        this.blockType = blockType;
    }
}
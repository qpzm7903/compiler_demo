package com.qpzm7903.compiler;

import java.util.List;

/**
 * AST的节点。
 * 属性包括AST的类型、文本值、下级子节点和父节点
 */
public interface ASTNode{
    //父节点
    ASTNode getParent();

    void setParent(ASTNode child);

    //子节点
    List<ASTNode> getChildren();

    void addChild(ASTNode node);

    //AST类型
    ASTNodeType getType();

    //文本值
    String getText();
}
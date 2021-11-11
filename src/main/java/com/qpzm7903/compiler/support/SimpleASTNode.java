package com.qpzm7903.compiler.support;

import com.qpzm7903.compiler.ASTNode;
import com.qpzm7903.compiler.ASTNodeType;
import com.qpzm7903.compiler.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 一个简单的AST节点的实现。
 * 属性包括：类型、文本值、父节点、子节点。
 */
public class SimpleASTNode implements ASTNode {
    ASTNode parent = null;
    List<ASTNode> children = new ArrayList<>();
    List<ASTNode> readonlyChildren = Collections.unmodifiableList(children);
    ASTNodeType nodeType = null;
    String text = null;


    public SimpleASTNode(ASTNodeType nodeType, String text) {
        this.nodeType = nodeType;
        this.text = text;
    }

    @Override
    public ASTNode getParent() {
        return parent;
    }

    @Override
    public void setParent(ASTNode child) {
        this.parent = child;
    }

    @Override
    public List<ASTNode> getChildren() {
        return readonlyChildren;
    }

    @Override
    public ASTNodeType getType() {
        return nodeType;
    }

    @Override
    public String getText() {
        return text;
    }


    @Override
    public void addChild(ASTNode child) {
        children.add(child);
        child.setParent(this);
    }

    @Override
    public String toString() {
        return "SimpleASTNode{" +
                "nodeType=" + nodeType +
                ", text='" + text + '\'' +
                '}';
    }
}

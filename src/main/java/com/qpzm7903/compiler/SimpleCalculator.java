package com.qpzm7903.compiler;

import com.qpzm7903.compiler.support.SimpleASTNode;

import java.util.Objects;

/**
 * 实现一个计算器，但计算的结合性是有问题的。因为它使用了下面的语法规则：
 * <p>
 * additive -> multiplicative | multiplicative + additive
 * multiplicative -> primary | primary * multiplicative    //感谢@Void_seT，原来写成+号了，写错了。
 * <p>
 * 递归项在右边，会自然的对应右结合。我们真正需要的是左结合。
 */
public class SimpleCalculator {

    /**
     * 执行脚本，并打印输出AST和求值过程。
     *
     * @param script
     */
    int evaluate(String script) throws Exception {
        ASTNode rootNode = parse(script);
        dumpAST(rootNode, "");
        return evaluate(rootNode, "");
    }

    private int evaluate(ASTNode node, String indent) throws Exception {
        int result = 0;
        System.out.println(indent + "Calculating: " + node.getType());
        switch (node.getType()) {
            case Programm:
                for (ASTNode child : node.getChildren()) {
                    result = evaluate(child, indent + "\t");
                }
                break;
            case Additive:
                ASTNode leftNode = node.getChildren().get(0);
                int leftResult = evaluate(leftNode, indent + "\t");
                ASTNode rightNode = node.getChildren().get(1);
                int rightResult = evaluate(rightNode, indent + "\t");
                if (Objects.equals(node.getText(), "+")) {
                    result = leftResult + rightResult;
                } else {
                    result = leftResult - rightResult;
                }
                break;
            case Multiplicative:
                leftNode = node.getChildren().get(0);
                leftResult = evaluate(leftNode, indent + "\t");
                rightNode = node.getChildren().get(1);
                rightResult = evaluate(rightNode, indent + "\t");
                if (Objects.equals(node.getText(), "*")) {
                    result = leftResult * rightResult;
                } else {
                    result = leftResult / rightResult;
                }
                break;
            case IntLiteral:
                result = Integer.parseInt(node.getText());
                break;
            default:
                throw new Exception("not support node type " + node.getType());
        }
        System.out.println(indent + "Result: " + result);
        return result;
    }

    /**
     * 解析脚本，并返回根节点
     *
     * @param code
     * @return
     * @throws Exception
     */
    public ASTNode parse(String code) throws Exception {
        SimpleLexer lexer = new SimpleLexer();
        TokenReader tokens = lexer.tokenize(code);
        return programNode(tokens);
    }

    /**
     * 语法解析：根节点
     *
     * @return
     * @throws Exception
     */

    private SimpleASTNode programNode(TokenReader tokens) throws Exception {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "Calculator");

        SimpleASTNode child = additiveNode(tokens);

        if (child != null) {
            node.addChild(child);
        }
        return node;
    }

    /**
     * 语法解析：加法表达式
     *
     * @return
     * @throws Exception
     */
    private SimpleASTNode additiveNode(TokenReader tokens) throws Exception {
        SimpleASTNode child1 = multiplicativeNode(tokens);
        SimpleASTNode rootNode = child1;
        if (child1 != null) {
            while (true) {
                Token token = tokens.peek();
                if (token != null && (token.getType() == TokenType.Plus || token.getType() == TokenType.Minus)) {
                    token = tokens.read();
                    SimpleASTNode child2 = multiplicativeNode(tokens);
                    if (child2 != null) {
                        rootNode = new SimpleASTNode(ASTNodeType.Additive, token.getText());
                        rootNode.addChild(child1);
                        rootNode.addChild(child2);
                        child1 = rootNode;
                    }
                } else {
                    break;
                }
            }
        }

        return rootNode;
    }


    /**
     * 语法解析：乘法表达式
     *
     * @return
     * @throws Exception
     */
    private SimpleASTNode multiplicativeNode(TokenReader tokens) throws Exception {
        SimpleASTNode child1 = primaryNode(tokens);
        SimpleASTNode rootNode = child1;
        if (child1 != null) {
            while (true) {
                Token peek = tokens.peek();
                if (peek != null && (peek.getType() == TokenType.Slash || peek.getType() == TokenType.Star)) {
                    Token token = tokens.read();
                    SimpleASTNode child2 = primaryNode(tokens);
                    if (child2 != null) {
                        rootNode = new SimpleASTNode(ASTNodeType.Multiplicative, token.getText());
                        rootNode.addChild(child1);
                        rootNode.addChild(child2);
                        child1 = rootNode;
                    }
                } else {
                    break;
                }
            }
        }
        return rootNode;
    }

    /**
     * 语法解析：基础表达式
     *
     * @return
     * @throws Exception
     */
    private SimpleASTNode primaryNode(TokenReader tokens) throws Exception {
        SimpleASTNode node = null;
        Token peek = tokens.peek();

        if (peek != null) {
            if (peek.getType() == TokenType.IntLiteral) {
                Token token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntLiteral, token.getText());
            } else {
                throw new Exception("语法有问题，请检查");
            }
        }

        return node;
    }

    /**
     * 打印输出AST的树状结构
     *
     * @param node
     * @param indent 缩进字符，由tab组成，每一级多一个tab
     */
    private void dumpAST(ASTNode node, String indent) {
        System.out.println(indent + node.getType() + " " + node.getText());
        for (ASTNode child : node.getChildren()) {
            dumpAST(child, indent + "\t");
        }
    }

}

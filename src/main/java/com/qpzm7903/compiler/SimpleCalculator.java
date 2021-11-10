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
        SimpleASTNode node = child1;
        Token token = tokens.peek();
        if (child1 != null && token != null) {
            if (token.getType() == TokenType.Plus || token.getType() == TokenType.Minus) {
                token = tokens.read();
                SimpleASTNode child2 = additiveNode(tokens);
                if (child2 != null) {
                    node = new SimpleASTNode(ASTNodeType.Additive, token.getText());
                    node.addChild(child1);
                    node.addChild(child2);
                } else {
                    throw new Exception("语法错误，请检查");
                }
            }
        }

        return node;
    }


    /**
     * 语法解析：乘法表达式
     *
     * @return
     * @throws Exception
     */
    private SimpleASTNode multiplicativeNode(TokenReader tokens) throws Exception {
        SimpleASTNode child1 = primaryNode(tokens);
        SimpleASTNode node = child1;
        Token peek = tokens.peek();
        if (child1 != null && peek != null) {
            if (peek.getType() == TokenType.Slash || peek.getType() == TokenType.Star) {
                Token token = tokens.read();
                SimpleASTNode child2 = multiplicativeNode(tokens);
                if (child2 != null) {
                    node = new SimpleASTNode(ASTNodeType.Multiplicative, token.getText());
                    node.addChild(child1);
                    node.addChild(child2);
                } else {
                    throw new Exception("语法有错误，请检查");
                }
            }
        }
        return node;
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


}

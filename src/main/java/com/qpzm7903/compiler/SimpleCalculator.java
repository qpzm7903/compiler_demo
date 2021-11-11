package com.qpzm7903.compiler;

import com.qpzm7903.compiler.support.SimpleASTNode;
import com.qpzm7903.compiler.support.SimpleToken;

import java.util.HashMap;
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
    private HashMap<String, Integer> variables = new HashMap<String, Integer>();

    /**
     * 执行脚本，并打印输出AST和求值过程。
     *
     * @param script
     */
    int evaluate(String script) {
        ASTNode rootNode = parse(script);
        dumpAST(rootNode, "");
        return evaluate(rootNode, "");
    }

    private int evaluate(ASTNode node, String indent) {
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
            case IntDeclaration:
                String varName = node.getText();
                Integer value = null;
                if (node.getChildren().size() > 0) {
                    ASTNode child = node.getChildren().get(0);
                    value = evaluate(child, indent + "\t");
                    value = Integer.valueOf(value);
                    result = value;

                }
                variables.put(varName, value);
                break;
            default:
                throw new RuntimeException("not support node type " + node.getType());
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
    public ASTNode parse(String code) {
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

    private SimpleASTNode programNode(TokenReader tokens) {
        SimpleASTNode node = new SimpleASTNode(ASTNodeType.Programm, "Calculator");

        while (tokens.peek() != null) {
            SimpleASTNode child = intDeclareNode(tokens);
            if (child == null) {
                child = expressionStatement(tokens);
            }
            if (child == null) {
                child = assignmentStatement(tokens);
            }
            if (child != null) {
                node.addChild(child);
            }
        }
        return node;
    }

    private SimpleASTNode assignmentStatement(TokenReader tokenReader) {
        Token peek = tokenReader.peek();
        if (peek != null && peek.isTypeOf(TokenType.Identifier)) {
            Token idToken = tokenReader.read();
            SimpleASTNode assignNode = new SimpleASTNode(ASTNodeType.AssignmentStmt, idToken.getText());
            peek = tokenReader.peek();
            if (peek != null && peek.isTypeOf(TokenType.Assignment)) {
                Token assignmentToken = tokenReader.read();
                SimpleASTNode additiveNode = additiveNode(tokenReader);
                if (additiveNode == null) {
                    throw new RuntimeException("invalid assignment, excepting an expression");
                } else {
                    assignNode.addChild(additiveNode);
                    peek = tokenReader.peek();
                    if (peek != null && peek.isTypeOf(TokenType.SemiColon)) {
                        tokenReader.read();
                        return assignNode;
                    } else {
                        throw new RuntimeException("missing a semicolon in assignment statement");
                    }
                }
            } else {
                tokenReader.unread();
            }
        }
        return null;
    }

    private SimpleASTNode expressionStatement(TokenReader tokenReader) {
        int pos = tokenReader.getPosition();
        SimpleASTNode node = additiveNode(tokenReader);
        if (node != null) {
            Token read = tokenReader.peek();
            if (read != null && read.isTypeOf(TokenType.SemiColon)) {
                tokenReader.read();
            } else {
                node = null;
                // 回溯
                tokenReader.setPosition(pos);
            }

        }
        return node; // TODO 这里简化了？ 因为直接返回的是加法，不是表达式
    }

    private SimpleASTNode intDeclareNode(TokenReader tokenReader) {
        SimpleASTNode node = null;
        Token token = tokenReader.peek();

        if (token != null && token.getType() == TokenType.Int) {
            token = tokenReader.read();
            if (tokenReader.peek() != null && tokenReader.peek().getType() == TokenType.Identifier) {
                token = tokenReader.read();
                node = new SimpleASTNode(ASTNodeType.IntDeclaration, token.getText());
                token = tokenReader.peek();
                if (token != null && token.getType() == TokenType.Assignment) {
                    token = tokenReader.read();
                    SimpleASTNode child = additiveNode(tokenReader);
                    if (child == null) {
                        throw new RuntimeException("int declare statement error");
                    } else {
                        node.addChild(child);

                    }
                }
            } else {
                throw new RuntimeException("int declare statement error");
            }
            token = tokenReader.peek();
            if (token != null && token.getType() == TokenType.SemiColon) {
                tokenReader.read();
            } else {
                throw new RuntimeException("missing semiColon in int declare");
            }
        }

        return node;
    }

    /**
     * 语法解析：加法表达式
     *
     * @return
     * @throws Exception
     */
    private SimpleASTNode additiveNode(TokenReader tokens) {
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
    private SimpleASTNode multiplicativeNode(TokenReader tokens) {
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
     */
    private SimpleASTNode primaryNode(TokenReader tokens) {
        SimpleASTNode node = null;
        Token peek = tokens.peek();

        if (peek != null) {
            if (peek.getType() == TokenType.IntLiteral) {
                Token token = tokens.read();
                node = new SimpleASTNode(ASTNodeType.IntLiteral, token.getText());
            } else {
                throw new RuntimeException("语法有问题，请检查");

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

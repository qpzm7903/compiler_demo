package com.qpzm7903.compiler;

import com.qpzm7903.compiler.support.SimpleToken;
import com.qpzm7903.compiler.support.SimpleTokenReader;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 一个简单的手写的词法分析器。
 * 能够为后面的简单计算器、简单脚本语言产生Token。
 */
public class SimpleLexer {

    //下面几个变量是在解析过程中用到的临时变量,如果要优化的话，可以塞到方法里隐藏起来
    private StringBuilder tokenText = null;   //临时保存token的文本
    private List<Token> tokens = null;       //保存解析出来的Token
    private SimpleToken token = null;        //当前正在解析的Token

    //是否是字母
    private boolean isAlpha(int ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }

    //是否是数字
    private boolean isDigit(int ch) {
        return ch >= '0' && ch <= '9';
    }

    //是否是空白字符
    private boolean isBlank(int ch) {
        return ch == ' ' || ch == '\t' || ch == '\n';
    }

    /**
     * 有限状态机进入初始状态。
     * 这个初始状态其实并不做停留，它马上进入其他状态。
     * 开始解析的时候，进入初始状态；某个Token解析完毕，也进入初始状态，在这里把Token记下来，然后建立一个新的Token。
     * <p>
     * 初始化token
     * 在每个token开始解析时都进行一次初始化
     * 初始化动作包括：
     * - 将上一个token生成
     * - 生成当前token的状态
     *
     * @param ch
     * @return
     */
    private DfaState initToken(char ch) {
        if (tokenText.length() > 0) {
            token.text = tokenText.toString();
            tokens.add(token);

            tokenText = new StringBuilder();
            token = new SimpleToken();
        }
        DfaState newDfaState = DfaState.Initial;
        if (isAlpha(ch)) {
            if (ch == 'i') {
                newDfaState = DfaState.Id_int1;
            } else {
                newDfaState = DfaState.Id;
            }
            token.type = TokenType.Identifier;
            tokenText.append(ch);
        } else if (isDigit(ch)) {
            newDfaState = DfaState.IntLiteral;
            token.type = TokenType.IntLiteral;
            tokenText.append(ch);
        } else if (ch == '>') {
            newDfaState = DfaState.GT;
            token.type = TokenType.GT;
            tokenText.append(ch);
        } else if (ch == ';') {
            newDfaState = DfaState.SemiColon;
            token.type = TokenType.SemiColon;
            tokenText.append(ch);
        } else if (ch == '+') {
            newDfaState = DfaState.Plus;
            token.type = TokenType.Plus;
            tokenText.append(ch);
        } else if (ch == '-') {
            newDfaState = DfaState.Minus;
            token.type = TokenType.Minus;
            tokenText.append(ch);
        } else if (ch == '*') {
            newDfaState = DfaState.Star;
            token.type = TokenType.Star;
            tokenText.append(ch);
        } else if (ch == '/') {
            newDfaState = DfaState.Slash;
            token.type = TokenType.Slash;
            tokenText.append(ch);
        }
        return newDfaState;
    }

    /**
     * 解析字符串，形成Token。
     * 这是一个有限状态自动机，通过在不同的状态中迁移中，遍历输入，得到token，每个token都有类型。
     *
     * @param code
     * @return
     */
    public SimpleTokenReader tokenize(String code) {
        tokens = new ArrayList<>();
        CharArrayReader reader = new CharArrayReader(code.toCharArray());
        tokenText = new StringBuilder();
        token = new SimpleToken();
        int ich = 0;
        char ch = 0;
        DfaState state = DfaState.Initial;
        try {

            while ((ich = reader.read()) != -1) {
                ch = (char) ich;
                switch (state) {
                    case Initial:
                        state = initToken(ch);
                        break;
                    case Id:
                        if (isAlpha(ch) || isDigit(ch)) {
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case GT:
                        if (ch == '=') {
                            token.type = TokenType.GE;
                            state = DfaState.GE;
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case GE:
                        state = initToken(ch);
                        break;
                    case IntLiteral:
                        if (isDigit(ch)) {
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case SemiColon:
                        state = initToken(ch);
                        break;

                    case Id_int1:
                        if (ch == 'n') {
                            state = DfaState.Id_int2;
                            tokenText.append(ch);
                        } else if (isDigit(ch) || isAlpha(ch)) {
                            state = DfaState.Id;
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;
                    case Id_int2:
                        if (ch == 't') {
                            state = DfaState.Id_int3;
                            tokenText.append(ch);
                        } else if (isDigit(ch) || isAlpha(ch)) {
                            state = DfaState.Id;
                            tokenText.append(ch);
                        } else {
                            state = initToken(ch);
                        }
                        break;

                    case Id_int3:
                        if (isBlank(ch)) {
                            token.type = TokenType.Int;
                            state = initToken(ch);
                        } else {
                            state = DfaState.Id;
                            tokenText.append(ch);
                        }
                        break;
                    case Plus:
                    case Minus:
                    case Star:
                    case Slash:
                        state = initToken(ch);
                        break;
                    default:

                }
            }
            // 最后一个token手动加进去
            if (tokenText.length() > 0) {
                initToken(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new SimpleTokenReader(tokens);


    }

    /**
     * 打印所有的Token
     *
     * @param tokenReader
     * @return
     */
    public String dump(SimpleTokenReader tokenReader) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("text    type\n");
        Token token;
        while ((token = tokenReader.read()) != null) {
            stringBuilder.append(token.getText())
                    .append("        ")
                    .append(token.getType())
                    .append("\n");
        }
        return stringBuilder.toString();
    }

}
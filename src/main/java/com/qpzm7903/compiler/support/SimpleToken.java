package com.qpzm7903.compiler.support;

import com.qpzm7903.compiler.Token;
import com.qpzm7903.compiler.TokenType;

/**
 * Token的一个简单实现。只有类型和文本值两个属性。
 *
 * @author qpzm7903
 * @since 2021-11-09-7:15
 */
public class SimpleToken implements Token {
    //Token类型
    public TokenType type = null;

    //文本值
    public String text = null;



    @Override
    public TokenType getType() {
        return type;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean isTypeOf(TokenType tokenType) {
        return this.type == tokenType;
    }

    @Override
    public String toString() {
        return "SimpleToken{" +
                "type=" + type +
                ", text='" + text + '\'' +
                '}';
    }
}

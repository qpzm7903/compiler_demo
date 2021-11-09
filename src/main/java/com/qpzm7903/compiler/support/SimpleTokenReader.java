package com.qpzm7903.compiler.support;

import com.qpzm7903.compiler.Token;
import com.qpzm7903.compiler.TokenReader;

import java.util.List;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-09-6:39
 */
public class SimpleTokenReader implements TokenReader {
    List<Token> tokens = null;
    int pos = 0;

    public SimpleTokenReader(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public Token read() {
        if (pos < tokens.size()) {
            return tokens.get(pos++);
        }
        return null;
    }

    @Override
    public Token peek() {
        if (pos < tokens.size()) {
            return tokens.get(pos);
        }
        return null;
    }

    @Override
    public void unread() {
        if (pos > 0) {
            pos--;
        }
    }

    @Override
    public int getPosition() {
        return pos;
    }

    @Override
    public void setPosition(int position) {
        if (position >= 0 && position < tokens.size()) {
            pos = position;
        }
    }

}

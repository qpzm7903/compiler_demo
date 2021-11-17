package com.qpzm7903.compiler.basic;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-16-21:30
 */
@Data
public class StackFrame {
    Scope scope;
    /**
     * 父栈，每个
     */
    StackFrame parentFrame;
    /**
     * 变量存在的地方
     */
    PlayObject object;

    protected Map<String, Object> variables = new HashMap<>();


    public StackFrame(Scope scope) {
        this.scope = scope;
        this.object = new PlayObject();
    }


}

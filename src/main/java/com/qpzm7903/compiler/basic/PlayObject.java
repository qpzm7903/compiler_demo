package com.qpzm7903.compiler.basic;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-16-21:31
 */
@Data
public class PlayObject {
    protected Map<Variable, Object> fields = new HashMap<>();
}

package com.qpzm7903.compiler.basic;

import lombok.Data;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-24-20:32
 */
@Data
public class PrimitiveType implements Type {
    private String name;

    public PrimitiveType(String name) {
        this.name = name;
    }

    public static PrimitiveType Integer = new PrimitiveType("Integer");
    public static PrimitiveType Long = new PrimitiveType("Long");
    public static PrimitiveType Float = new PrimitiveType("Float");
    public static PrimitiveType Double = new PrimitiveType("Double");
    public static PrimitiveType Boolean = new PrimitiveType("Boolean");
    public static PrimitiveType Byte = new PrimitiveType("Byte");
    public static PrimitiveType Char = new PrimitiveType("Char");
    public static PrimitiveType Short = new PrimitiveType("Short");
    public static PrimitiveType String = new PrimitiveType("String");
    public static PrimitiveType Null = new PrimitiveType("null");

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Scope getScope() {
        return null;
    }

    @Override
    public boolean isType(Type type) {
        return false;
    }
}

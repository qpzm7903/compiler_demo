package com.qpzm7903.compiler.basic;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-24-22:31
 */
public class VoidType implements Type {
    //只保留一个实例即可。
    public static VoidType voidType = new VoidType();

    public static VoidType instance() {
        return voidType;
    }

    @Override
    public String getName() {
        return "void";
    }

    @Override
    public Scope getScope() {
        return null;
    }

    @Override
    public boolean isType(Type type) {
        return this == type;
    }

    @Override
    public String toString() {
        return "void";
    }

}

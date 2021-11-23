package com.qpzm7903.compiler.basic;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用域也是一种符号
 *
 * @author qpzm7903
 * @since 2021-11-16-7:51
 */
public abstract class Scope extends Symbol {
    /**
     * 该scope中的成员，包括变量，方法，类等
     */
    protected List<Symbol> symbols = new ArrayList<>();

    protected void addSymbol(Symbol symbol) {
        symbols.add(symbol);
    }

    protected Variable getVariable(String name) {
        for (Symbol symbol : scope.symbols) {
            if (symbol instanceof Variable && symbol.name.equals(name)) {
                return (Variable) symbol;
            }
        }
        return null;
    }

    protected Function getFunction(String name, List<Type> paramTypes) {
        for (Symbol symbol : this.symbols) {
            if (symbol instanceof Function && symbol.name.equals(name)) {
                Function function = (Function) symbol;
                if (function.paramTypesMatch(paramTypes)) {
                    return function;
                }
            }
        }
        return null;
    }


    /**
     * 获取一个函数类型的变量，能匹配相应的参数类型
     * @param name
     * @param paramTypes
     * @return
     */
    protected Variable getFunctionVariable(String name, List<Type> paramTypes){
        return getFunctionVariable(this,name,paramTypes);
    }

    protected static Variable getFunctionVariable(Scope scope, String name, List<Type> paramTypes){
        Variable rtn = null;
        for (Symbol s : scope.symbols) {
            if (s instanceof Variable && ((Variable) s).type instanceof FunctionType && s.name.equals(name)) {
                Variable v = (Variable) s;
                FunctionType functionType = (FunctionType) v.type;
                if (functionType.matchParameterTypes(paramTypes)){
                    rtn = v;
                    break;
                }
            }
        }
        return rtn;
    }
}

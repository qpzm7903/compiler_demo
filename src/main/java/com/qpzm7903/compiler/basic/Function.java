package com.qpzm7903.compiler.basic;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-16-7:52
 */
public class Function extends Scope implements FunctionType {
    protected List<Variable> parameters = new LinkedList<>();

    protected Type returnType;

    protected Set<Variable> closureVariables;

    private List<Type> paramTypes;

    public Function(String name, Scope scope, ParserRuleContext context) {
        this.name = name;
        this.scope = scope;
        this.ctx = context;
    }

    public boolean paramTypesMatch(List<Type> paramTypes) {
        if (parameters.size() != paramTypes.size()) {
            return false;
        }
        for (int i = 0; i < paramTypes.size(); i++) {
            if (!parameters.get(i).type.isType(paramTypes.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Type getReturnType() {
        return null;
    }

    @Override
    public List<Type> getParamTypes() {
        if (paramTypes == null) {
            paramTypes = new LinkedList<>();
        }

        for (Variable param : parameters) {
            paramTypes.add(param.type);
        }

        return paramTypes;
    }

    @Override
    public boolean matchParameterTypes(List<Type> paramTypes) {
        return false;
    }

    @Override
    public boolean isType(Type type) {
        return false;
    }
}

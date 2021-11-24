package com.qpzm7903.compiler.basic;

import com.qpzm7903.compiler.antlr4.PlayScriptBaseListener;
import com.qpzm7903.compiler.antlr4.PlayScriptParser;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-23-22:25
 */
public class TypeResolver extends PlayScriptBaseListener {

    private AnnotatedTree at = null;

    //是否把本地变量加入符号表
    private boolean enterLocalVariable = false;

    public TypeResolver(AnnotatedTree at) {
        this.at = at;
    }

    public TypeResolver(AnnotatedTree at, boolean enterLocalVariable) {
        this.at = at;
        this.enterLocalVariable = enterLocalVariable;
    }


    @Override
    public void enterVariableDeclaratorId(PlayScriptParser.VariableDeclaratorIdContext ctx) {
        String idName = ctx.IDENTIFIER().getText();
        Scope scope = at.enclosingScopeOfNode(ctx);

        if (enterLocalVariable || ctx.parent instanceof PlayScriptParser.FormalParameterContext) {
            Variable variable = new Variable(idName, scope, ctx);
            if (scope.getVariable(idName) != null) {
                at.log("variable or paramter already Declared. " + idName, ctx);

            }
            scope.addSymbol(variable);
            System.out.println("enter variable declare " + variable);
            at.symbolOfNode.put(ctx, variable);
        }
    }

    @Override
    public void exitPrimitiveType(PlayScriptParser.PrimitiveTypeContext ctx) {
        Type type = null;
        if (ctx.BOOLEAN() != null) {
            type = PrimitiveType.Boolean;
        } else if (ctx.INT() != null) {
            type = PrimitiveType.Integer;
        } else if (ctx.LONG() != null) {
            type = PrimitiveType.Long;
        } else if (ctx.FLOAT() != null) {
            type = PrimitiveType.Float;
        } else if (ctx.DOUBLE() != null) {
            type = PrimitiveType.Double;
        } else if (ctx.BYTE() != null) {
            type = PrimitiveType.Byte;
        } else if (ctx.SHORT() != null) {
            type = PrimitiveType.Short;
        } else if (ctx.CHAR() != null) {
            type = PrimitiveType.Char;
        } else if (ctx.STRING() != null) {
            type = PrimitiveType.String;
        }

        System.out.println("exit primitive type of " + type);

        at.typeOfNode.put(ctx, type);
    }

    @Override
    public void exitTypeType(PlayScriptParser.TypeTypeContext ctx) {
        // 冒泡，将下级的属性标注在本级
        if (ctx.classOrInterfaceType() != null) {
            Type type = at.typeOfNode.get(ctx.classOrInterfaceType());
            at.typeOfNode.put(ctx, type);
        } else if (ctx.functionType() != null) {
            Type type = at.typeOfNode.get(ctx.functionType());
            at.typeOfNode.put(ctx, type);
        } else if (ctx.primitiveType() != null) {
            Type type = at.typeOfNode.get(ctx.primitiveType());
            at.typeOfNode.put(ctx, type);
        }
    }

    @Override
    public void exitTypeTypeOrVoid(PlayScriptParser.TypeTypeOrVoidContext ctx) {
        if (ctx.VOID() != null) {
            at.typeOfNode.put(ctx, VoidType.instance());
        } else if (ctx.typeType() != null) {
            at.typeOfNode.put(ctx, (Type) at.typeOfNode.get(ctx.typeType()));
        }
    }

    //设置函数的参数的类型，这些参数已经在enterVariableDeclaratorId中作为变量声明了，现在设置它们的类型
    @Override
    public void exitFormalParameter(PlayScriptParser.FormalParameterContext ctx) {
        // 设置参数类型
        Type type = at.typeOfNode.get(ctx.typeType());
        Variable variable = (Variable) at.symbolOfNode.get(ctx.variableDeclaratorId());
        variable.type = (Type) type;

        // 添加到函数的参数列表里
        Scope scope = at.enclosingScopeOfNode(ctx);
        if (scope instanceof Function) {    //TODO 从目前的语法来看，只有function才会使用FormalParameter
            ((Function) scope).parameters.add(variable);
            System.out.println("exit formal parameter of function" + ctx);
            System.out.println("formal parameter variable is " + variable);
        }
    }

    @Override
    public void exitFunctionDeclaration(PlayScriptParser.FunctionDeclarationContext ctx) {
        Function function = (Function) at.node2Scope.get(ctx);
        if (ctx.typeTypeOrVoid() != null) {
            function.returnType = at.typeOfNode.get(ctx.typeTypeOrVoid());
        }
        //函数查重，检查名称和参数（这个时候参数已经齐了）
        Scope scope = at.enclosingScopeOfNode(ctx);
        Function found = scope.getFunction(function.name, function.getParamTypes());
        if (found != null && found != function) {
            at.log("Function or method already Declared: " + ctx.getText(), ctx);
            System.out.println("function or method already declared " + function.name);

        }
    }


}

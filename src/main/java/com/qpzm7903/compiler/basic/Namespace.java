package com.qpzm7903.compiler.basic;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * todo description
 *
 * @author qpzm7903
 * @since 2021-11-23-6:57
 */
public class Namespace extends BlockScope {
    private Namespace parent;
    private List<Namespace> subNamespaces = new LinkedList<>();

    private String name;

    public Namespace(Scope scope, ParserRuleContext context,  String name) {
        super(scope, context);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Namespace> subNamespaces() {
        return Collections.unmodifiableList(subNamespaces);
    }

    public void addNamespace(Namespace namespace) {
        namespace.parent = this;
        this.subNamespaces.add(namespace);
    }

    public void removeNamespace(Namespace namespace) {
        namespace.parent = null;
        this.subNamespaces.remove(namespace);
    }
}

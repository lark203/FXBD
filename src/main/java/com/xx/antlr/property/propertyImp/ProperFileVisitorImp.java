package com.xx.antlr.property.propertyImp;

import com.xx.antlr.property.parser.PropertyFileBaseVisitor;
import com.xx.antlr.property.parser.PropertyFileParser;
import org.antlr.v4.misc.OrderedHashMap;

import java.util.Map;

public class ProperFileVisitorImp extends PropertyFileBaseVisitor<Void> {
    Map<String,String> props = new OrderedHashMap<>();

    @Override
    public Void visitProp(PropertyFileParser.PropContext ctx) {
        String id = ctx.ID().getText();
        String value = ctx.STRING().getText();
        props.put(id,value);
        return null;
    }

    public Map<String, String> getProps() {
        return props;
    }
}

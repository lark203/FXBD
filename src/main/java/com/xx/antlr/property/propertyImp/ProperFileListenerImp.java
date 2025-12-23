package com.xx.antlr.property.propertyImp;

import com.xx.antlr.property.parser.PropertyFileBaseListener;
import com.xx.antlr.property.parser.PropertyFileParser;
import org.antlr.v4.misc.OrderedHashMap;

import java.util.Map;

public class ProperFileListenerImp extends PropertyFileBaseListener {
    Map<String,String> props = new OrderedHashMap<>();

    @Override
    public void exitProp(PropertyFileParser.PropContext ctx) {
        String id = ctx.ID().getText();
        String value = ctx.STRING().getText();
        props.put(id,value);
        super.exitProp(ctx);
    }

    public Map<String, String> getProps() {
        return props;
    }
}

package com.wxmimperio.gson.deserializer.deserializer;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wxmimperio.gson.deserializer.beans.BaseColumn;
import com.wxmimperio.gson.deserializer.beans.OrderColumn;
import com.wxmimperio.gson.deserializer.common.DataType;
import com.wxmimperio.gson.deserializer.common.OrderType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BaseColumnSerialization {
    public static class Deserializer extends JsonDeserializer<BaseColumn> {
        @Override
        public BaseColumn deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ObjectCodec codec = jsonParser.getCodec();
            JsonNode node = codec.readTree(jsonParser);
            BaseColumn column = null;
            JsonNode orderTypeNode = node.get("orderType");
            String name = node.has("name") ? node.get("name").asText() : null;
            DataType dataType = codec.treeToValue(node.get("dataType"), DataType.class);
            String comment = node.has("comment") ? node.get("comment").asText() : null;
            List<BaseColumn> children = null;
            if (null != node.get("baseColumns") && !node.get("baseColumns").isNull()) {
                JsonNode childNode = node.get("baseColumns");
                children = new ArrayList<>(childNode.size());
                for (JsonNode child : childNode) {
                    children.add(codec.treeToValue(child, BaseColumn.class));
                }
            }
            if (null == orderTypeNode || orderTypeNode.isNull()) {
                column = new BaseColumn(name, dataType, comment, children);
            } else {
                OrderType orderType = codec.treeToValue(orderTypeNode, OrderType.class);
                column = new OrderColumn(name, dataType, comment, children, orderType);
            }
            return column;
        }
    }
}

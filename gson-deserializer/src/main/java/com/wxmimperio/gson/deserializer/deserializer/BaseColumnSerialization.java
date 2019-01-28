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
import com.wxmimperio.gson.deserializer.common.OrderType;

import java.io.IOException;

public class BaseColumnSerialization {
    public static class Deserializer extends JsonDeserializer<BaseColumn> {
        @Override
        public BaseColumn deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            ObjectCodec codec = jsonParser.getCodec();
            JsonNode node = codec.readTree(jsonParser);
            BaseColumn column = null;
            JsonNode direction = node.get("orderType");
            if (null == direction || direction.isNull()) {
                OrderType orderType = codec.treeToValue(node.get("orderType"), OrderType.class);
                System.out.println(orderType);
                column = new ObjectMapper().readValue(node.toString(), BaseColumn.class);
            } else {
                column = new ObjectMapper().readValue(node.toString(), OrderColumn.class);
            }
            return column;
        }
    }
}

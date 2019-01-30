package com.wxmimperio.gson.deserializer.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.wxmimperio.gson.deserializer.beans.BaseColumn;
import com.wxmimperio.gson.deserializer.beans.OrderColumn;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JacksonSerialization {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.readValue("{\"type\":\"date\"}", Map.class));

        SimpleModule module = new SimpleModule();
        module.addDeserializer(BaseColumn.class, new BaseColumnSerialization.Deserializer());
        mapper.registerModule(module);

        String jsonStr = "{\"name\":\"WXMIMPERIO\",\"comment\":\"comment\",\"dataType\":\"STRING\",\"baseColumns\":[{\"name\":\"child1\",\"comment\":\"child1\",\"dataType\":\"STRING\",\"orderType\":\"ASC\"},{\"name\":\"child2\",\"comment\":\"child2\",\"dataType\":\"STRING\",\"orderType\":\"DESC\"}]}";
        BaseColumn baseColumn = mapper.readValue(jsonStr, BaseColumn.class);

        System.out.println(baseColumn);
        System.out.println("name = " + baseColumn.getName());
        System.out.println("comment = " + baseColumn.getComment());
        System.out.println("dataType = " + baseColumn.getDataType());
        System.out.println("baseColumns = " + baseColumn.getBaseColumns());

        List<OrderColumn> lists = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            lists.add(mapper.readValue(jsonStr, OrderColumn.class));
        }

        List<OrderColumn> lists1 = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            lists1.add(mapper.readValue(jsonStr, OrderColumn.class));
        }

        Stream.concat(lists.stream(), lists1.stream()).collect(Collectors.toList()).forEach(col -> {
            col.setName(col.getName().toLowerCase());
        });

        System.out.println(lists);
        System.out.println(lists1);
    }

    private static void colNameToLowerCase(List<OrderColumn> columns) {
        if (!CollectionUtils.isEmpty(columns)) {

        }
    }
}

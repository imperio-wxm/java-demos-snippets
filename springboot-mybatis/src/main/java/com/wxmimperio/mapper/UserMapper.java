package com.wxmimperio.mapper;

import com.wxmimperio.pojo.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * Created by wxmimperio on 2017/4/20.
 */
@Mapper
@Component
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    @Results(value = {
            @Result(property = "id", column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Result(property = "name", column = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(property = "age", column = "age", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(property = "gender", column = "gender", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    User findByName(@Param("name") String name);

    @Insert("INSERT INTO USER(NAME, AGE, GENDER) VALUES(#{name}, #{age}, #{gender})")
    int insert(@Param("name") String name, @Param("age") Integer age, @Param("gender") String gender);

}

package com.wxmimperio.mapper;

import com.wxmimperio.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Created by wxmimperio on 2017/4/20.
 */
@Mapper
@Component
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE NAME = #{name}")
    User findByName(@Param("name") String name);

    @Insert("INSERT INTO USER(NAME, AGE, GENDER) VALUES(#{name}, #{age}, #{gender})")
    int insert(@Param("name") String name, @Param("age") Integer age, @Param("gender") String gender);

}

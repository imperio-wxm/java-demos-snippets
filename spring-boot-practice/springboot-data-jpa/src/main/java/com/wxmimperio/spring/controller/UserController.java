package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.bean.User;
import com.wxmimperio.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/save")
    public User save(String name, Integer age, String gender) {
        return userRepository.save(new User(name, age, gender));
    }

    @GetMapping("findByName/{name}")
    public List<User> findByName(@PathVariable String name) {
        return userRepository.findByName(name);
    }

    @GetMapping("findByNameAndAge/{name}/{age}")
    public User findByNameAndAge(@PathVariable String name,
                                 @PathVariable Integer age) {
        return userRepository.findByNameAndAge(name, age);
    }

    @GetMapping("withNameAndIdQuery/{name}/{id}")
    public User withNameAndIdQuery(@PathVariable String name,
                                   @PathVariable Integer id) {
        return userRepository.withNameAndIdQuery(name, id);
    }

    /**
     * 测试排序
     * @return
     */
    @GetMapping("/sortByAge")
    public List<User> sortByAge() {
        return userRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
    }

    /**
     * 测试分页
     * @return
     */
    @GetMapping("/page")
    public Page<User> page() {
        return userRepository.findAll(new PageRequest(1,2));
    }
}

package com.wxmimperio.spring.controller;

import com.wxmimperio.spring.bean.User;
import com.wxmimperio.spring.repository.UserRepository;
import com.wxmimperio.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping("/save")
    public User save(String name, Integer age, String gender, Integer id) {
        User user = new User(id, name, age, gender);
        if (null != user.getId() && null != userRepository.findOne(user.getId())) {
            throw new RuntimeException("exits");
        }
        return userRepository.save(new User(id, name, age, gender));
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
     *
     * @return
     */
    @GetMapping("/sortByAge")
    public List<User> sortByAge() {
        return userRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
    }

    /**
     * 测试分页
     *
     * @return
     */
    @GetMapping("/page")
    public Page<User> page() {
        return userRepository.findAll(new PageRequest(1, 2));
    }

    @PostMapping("/rollBack")
    public User rollBack(@RequestBody User user) {
        return userService.saveUserWithRollBack(user);
    }

    @PostMapping("/noRollBack")
    public User noRollBack(@RequestBody User user) {
        return userService.saveUserWithoutRollBack(user);
    }
}

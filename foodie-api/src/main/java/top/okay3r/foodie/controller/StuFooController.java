package top.okay3r.foodie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import top.okay3r.foodie.service.StuService;
import top.okay3r.foodie.pojo.Stu;

@ApiIgnore
@RestController
public class StuFooController {

    @Autowired
    private StuService stuService;

    @GetMapping("/getStu")
    public Stu sayHello(Integer id) {
        return this.stuService.getStuInfoById(id);
    }

    @GetMapping("/getStuByName")
    public Stu getStuByName(String name) {
        return this.stuService.getStuInfoByName(name);
    }
}

package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.Stu;

public interface StuService {
    Stu getStuInfoById(Integer id);

    Stu getStuInfoByName(String name);
}



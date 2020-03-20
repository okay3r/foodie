package top.okay3r.foodie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import top.okay3r.foodie.mapper.StuMapper;
import top.okay3r.foodie.pojo.Stu;
import top.okay3r.foodie.service.StuService;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public Stu getStuInfoById(Integer id) {
        return this.stuMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Stu getStuInfoByName(String name) {
        Example example = new Example(Stu.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", name);
        return this.stuMapper.selectByExample(example).get(0);
    }

}

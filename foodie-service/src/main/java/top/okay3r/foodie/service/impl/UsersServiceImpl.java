package top.okay3r.foodie.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import top.okay3r.foodie.enums.Sex;
import top.okay3r.foodie.mapper.UsersMapper;
import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.pojo.bo.UserBO;
import top.okay3r.foodie.service.UsersService;
import top.okay3r.foodie.utils.MD5Utils;

import java.util.Date;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    //默认头像
    private static final String USER_FACE = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1584688054108&di=72bcdf47e4f749e303e09d54231b0874&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01786557e4a6fa0000018c1bf080ca.png";

    /**
     * 校验用户名是否存在
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExists(String username) {

        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);

        Users one = this.usersMapper.selectOneByExample(example);
        return one != null;
    }

    /**
     * 创建用户
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBO) {

        //生成全局唯一id
        String userId = this.sid.nextShort();

        Users users = new Users();
        users.setId(userId);
        users.setUsername(userBO.getUsername());
        try {
            //密码需要加密
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //昵称默认为用户名
        users.setNickname(userBO.getUsername());
        //默认头像
        users.setFace(USER_FACE);
        //默认性别保密
        users.setSex(Sex.secret.type);
        Date now = new Date();
        users.setCreatedTime(now);
        users.setUpdatedTime(now);

        //插入到数据库
        this.usersMapper.insert(users);

        return users;
    }
}

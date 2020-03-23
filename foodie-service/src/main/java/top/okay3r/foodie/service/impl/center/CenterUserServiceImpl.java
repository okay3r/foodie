package top.okay3r.foodie.service.impl.center;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.okay3r.foodie.mapper.UsersMapper;
import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.pojo.bo.center.CenterUserBO;
import top.okay3r.foodie.service.center.CenterUserService;

import java.util.Date;

@Service
public class CenterUserServiceImpl implements CenterUserService {

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfoById(String userId) {
        return this.usersMapper.selectByPrimaryKey(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserInfo(String userId, CenterUserBO centerUserBO) {

        Users users = new Users();
        users.setId(userId);
        users.setUpdatedTime(new Date());
        BeanUtils.copyProperties(centerUserBO, users);

        this.usersMapper.updateByPrimaryKeySelective(users);

        return this.queryUserInfoById(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users updateUserFace(String userId, String finalUserFaceUrl) {
        Users users = new Users();
        users.setId(userId);
        users.setFace(finalUserFaceUrl);

        this.usersMapper.updateByPrimaryKeySelective(users);

        return queryUserInfoById(userId);
    }
}

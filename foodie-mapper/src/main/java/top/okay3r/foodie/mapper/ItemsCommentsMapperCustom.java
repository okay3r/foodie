package top.okay3r.foodie.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.okay3r.foodie.pojo.vo.MyCommentVo;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemsCommentsMapperCustom {

    void saveComments(Map<String, Object> map);

    List<MyCommentVo> queryMyComments(@Param("paramsMap") Map paramsMap);

}

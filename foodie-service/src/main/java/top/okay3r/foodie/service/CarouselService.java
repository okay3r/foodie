package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.Carousel;

import java.util.List;

public interface CarouselService {

    /**
     * 查询全部首页大图片
     */
    List<Carousel> queryAll(Integer isShow);
}

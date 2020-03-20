package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.Carousel;

import java.util.List;

public interface CarouselService {
    List<Carousel> queryAll(Integer isShow);
}

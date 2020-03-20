package top.okay3r.foodie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import top.okay3r.foodie.mapper.CarouselMapper;
import top.okay3r.foodie.pojo.Carousel;
import top.okay3r.foodie.service.CarouselService;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        Example.Criteria criteria = example.createCriteria();
        example.orderBy("sort").asc();
        criteria.andEqualTo("isShow", isShow);
        List<Carousel> carouselList = this.carouselMapper.selectByExample(example);
        return carouselList;
    }
}

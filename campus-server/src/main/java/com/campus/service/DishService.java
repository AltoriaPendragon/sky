package com.campus.service;

import com.campus.dto.DishDTO;
import com.campus.dto.DishPageQueryDTO;
import com.campus.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService {

    /**
     * 新增菜品
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 批量删除菜品
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}

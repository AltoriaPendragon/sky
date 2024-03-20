package com.campus.service;

import com.campus.dto.DishDTO;
import org.springframework.stereotype.Service;

@Service
public interface DishService {

    /**
     * 新增菜品
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);
}

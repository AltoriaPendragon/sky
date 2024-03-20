package com.campus.service.impl;

import com.campus.constant.MessageConstant;
import com.campus.constant.StatusConstant;
import com.campus.dto.DishDTO;
import com.campus.dto.DishPageQueryDTO;
import com.campus.entity.Dish;
import com.campus.entity.DishFlavor;
import com.campus.entity.SetmealDish;
import com.campus.exception.DeletionNotAllowedException;
import com.campus.mapper.DishFlavorMapper;
import com.campus.mapper.DishMapper;
import com.campus.mapper.SetmealDishMapper;
import com.campus.result.PageResult;
import com.campus.service.DishService;
import com.campus.vo.DishVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    /**
     * 新增菜品
     * @param dishDTO
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //插入一条菜品数据
        dishMapper.insert(dish);
        Long dishId = dish.getId();
        //插入多条口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除菜品
     * @param ids
     */
    @Transactional
    @Override
    public void deleteBatch(List<Long> ids) {
        //起售中的菜品不能删除
        for (Long id : ids) {
            Dish dish= dishMapper.getById(id);
            if (dish.getStatus().equals(StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //在套餐中的菜品不能删除
        List<Long> setmealIds = setmealDishMapper.getSetmealIdByDishId(ids);
        if (setmealIds != null && !setmealIds.isEmpty()){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        /*for (Long id : ids) {
            dishMapper.deleteById(id);
            dishFlavorMapper.deleteById(id);
        }*/

        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByIds(ids);

    }
}

package com.campus.service;

import com.campus.dto.SetmealDTO;
import com.campus.dto.SetmealPageQueryDTO;
import com.campus.entity.Setmeal;
import com.campus.result.PageResult;
import com.campus.vo.DishItemVO;
import com.campus.vo.SetmealVO;

import java.util.List;

public interface SetmealService {
    /**
     * 新增套餐
     * @param setmealDTO
     */
    void save(SetmealDTO setmealDTO);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除菜品
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据套餐Id查询套餐及菜品信息
     * @param id
     * @return
     */
    SetmealVO getByIdWithDishes(Long id);

    /**
     * 修改套餐及其菜品信息
     * @param setmealDTO
     */
    void updateWithDishes(SetmealDTO setmealDTO);

    /**
     * 起售/禁售套餐
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    List<DishItemVO> getDishItemById(Long id);
}

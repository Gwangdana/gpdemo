package com.dana.common.db;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * MyBatis-Plus Service 通用基类
 * <p>
 * 适配 MP 3.5+ 版本，封装常用 CRUD、批量操作、条件操作
 *
 * @param <M> 自定义 BaseMapper
 * @param <T> 实体类（继承 BaseEntity）
 */
public abstract class BaseService<M extends BaseMapper<T>, T extends BaseEntity>
        extends ServiceImpl<M, T> {

    // ======================================
    // 一、MP 原生方法重写（空值保护）
    // ======================================

    @Override
    public T getById(Serializable id) {
        if (Objects.isNull(id)) {
            return null;
        }
        return super.getById(id);
    }

    @Override
    public boolean saveOrUpdate(T entity) {
        if (Objects.isNull(entity)) {
            return false;
        }
        return super.saveOrUpdate(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        if (Objects.isNull(id)) {
            return false;
        }
        return super.removeById(id);
    }

    @Override
    public List<T> listByIds(Collection<? extends Serializable> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        return baseMapper.selectByIds(idList);
    }

    public boolean removeByIds(Collection<?> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return false;
        }
        return baseMapper.deleteByIds(idList) > 0;
    }

    // ======================================
    // 二、自定义扩展方法
    // ======================================

    /**
     * 根据条件查询单条数据
     */
    public T getOneByCondition(LambdaQueryWrapper<T> queryWrapper) {
        return super.getOne(queryWrapper, false);
    }

    /**
     * 根据条件查询列表
     */
    public List<T> listByCondition(LambdaQueryWrapper<T> queryWrapper) {
        if (Objects.isNull(queryWrapper)) {
            return Collections.emptyList();
        }
        return super.list(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<T> pageByCondition(long pageNum, long pageSize, LambdaQueryWrapper<T> queryWrapper) {
        Page<T> page = new Page<>(pageNum, pageSize);
        return super.page(page, queryWrapper);
    }

    /**
     * 统计条数
     */
    public long countByCondition(LambdaQueryWrapper<T> queryWrapper) {
        if (Objects.isNull(queryWrapper)) {
            return 0;
        }
        return super.count(queryWrapper);
    }

    /**
     * 判断数据是否存在（兼容 MySQL / Oracle 等多种数据库）
     */
    public boolean exists(LambdaQueryWrapper<T> queryWrapper) {
        if (Objects.isNull(queryWrapper)) {
            return false;
        }
        return super.count(queryWrapper) > 0;
    }

    // ======================================
    // 三、条件更新/删除（防全表操作）
    // ======================================

    /**
     * 按条件更新（实体 + 条件）
     */
    public boolean updateByCondition(T entity, LambdaUpdateWrapper<T> updateWrapper) {
        if (Objects.isNull(entity) || Objects.isNull(updateWrapper)) {
            return false;
        }
        if (updateWrapper.isEmptyOfWhere()) {
            throw new IllegalArgumentException("更新条件不能为空，禁止全表更新！");
        }
        return super.update(entity, updateWrapper);
    }

    /**
     * 纯条件更新（无需实体）
     */
    public boolean updateByWrapper(LambdaUpdateWrapper<T> updateWrapper) {
        if (Objects.isNull(updateWrapper)) {
            return false;
        }
        if (updateWrapper.isEmptyOfWhere()) {
            throw new IllegalArgumentException("更新条件不能为空，禁止全表更新！");
        }
        return super.update(null, updateWrapper);
    }

    /**
     * 按条件删除
     */
    public boolean removeByCondition(LambdaQueryWrapper<T> queryWrapper) {
        if (Objects.isNull(queryWrapper)) {
            return false;
        }
        if (queryWrapper.isEmptyOfWhere()) {
            throw new IllegalArgumentException("删除条件不能为空，禁止全表删除！");
        }
        return super.remove(queryWrapper);
    }

    // ======================================
    // 四、批量操作
    // ======================================

    /**
     * 通用批量插入（默认 1000 条/批）
     */
    public boolean batchInsert(List<T> entityList) {
        return batchInsert(entityList, 1000);
    }

    /**
     * 通用批量插入（自定义批次大小）
     */
    public boolean batchInsert(List<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            return false;
        }
        return super.saveBatch(entityList, batchSize);
    }

    /**
     * 批量更新（按主键）
     */
    public boolean batchUpdate(List<T> entityList) {
        return batchUpdate(entityList, 1000);
    }

    /**
     * 批量更新（自定义批次大小）
     */
    public boolean batchUpdate(List<T> entityList, int batchSize) {
        if (CollectionUtils.isEmpty(entityList)) {
            return false;
        }
        return super.updateBatchById(entityList, batchSize);
    }
}

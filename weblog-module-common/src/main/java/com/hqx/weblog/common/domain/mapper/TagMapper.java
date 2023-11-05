package com.hqx.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hqx.weblog.common.domain.dos.TagDO;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface TagMapper extends BaseMapper<TagDO> {

    /**
     * 分页查询
     * @param current
     * @param size
     * @param name
     * @param startDate
     * @param endDate
     * @return
     */
    default Page<TagDO> selectPageList(long current, long size, String name, LocalDate startDate, LocalDate endDate) {
        // 分页对象
        Page<TagDO> tagDOPage = new Page<>(current, size);
        // 构建查询对象
        LambdaQueryWrapper<TagDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(Objects.nonNull(name), TagDO::getName, name)
                .ge(Objects.nonNull(startDate), TagDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), TagDO::getCreateTime, endDate)
                .orderByDesc(TagDO::getCreateTime); // order by create_time desc

        return selectPage(tagDOPage, queryWrapper);

    }

    /**
     * 根据标签模糊查询
     * @param key
     * @return
     */
    default List<TagDO> selectByKey(String key) {
        // 构建查询对象
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        // 构造模糊查询条件
        wrapper.like(TagDO::getName, key).orderByDesc(TagDO::getCreateTime);

        return this.selectList(wrapper);

    }

}
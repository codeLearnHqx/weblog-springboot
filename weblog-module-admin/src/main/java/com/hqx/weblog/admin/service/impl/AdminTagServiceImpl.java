package com.hqx.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hqx.weblog.admin.model.vo.tag.*;
import com.hqx.weblog.admin.service.AdminTagService;
import com.hqx.weblog.common.domain.dos.TagDO;
import com.hqx.weblog.common.domain.mapper.TagMapper;
import com.hqx.weblog.common.enums.ResponseCodeEnum;
import com.hqx.weblog.common.model.vo.SelectRspVO;
import com.hqx.weblog.common.utils.PageResponse;
import com.hqx.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {
    @Resource
    private TagMapper tagMapper;

    @Override
    public Response addTag(AddTagReqVO addTagReqVO) {

        // vo 转 do
        List<TagDO> tagDOS = addTagReqVO.getTags()
                .stream().map(tagName -> TagDO.builder()
                        .name(tagName.trim())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        try {
            // 批量添加
            this.saveBatch(tagDOS);
        } catch (Exception e) {
            // 标签名不能重复
            return Response.fail(ResponseCodeEnum.TAG_CANT_DUPLICATE);
        }
        return Response.success();
    }

    @Override
    public PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO) {
        // 分页参数、条件参数
        Long current = findTagPageListReqVO.getCurrent();
        Long size = findTagPageListReqVO.getSize();
        String name = findTagPageListReqVO.getName();
        LocalDate startDate = findTagPageListReqVO.getStartDate();
        LocalDate endDate = findTagPageListReqVO.getEndDate();

        // 分页查询
        Page<TagDO> tagDOPage = tagMapper.selectPageList(current, size, name, startDate, endDate);
        List<TagDO> records = tagDOPage.getRecords();
        // do 转vo
        List<FindTagPageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(records)) {
            vos = records
                    .stream()
                    .map(tagDO -> FindTagPageListRspVO.builder()
                            .id(tagDO.getId())
                            .name(tagDO.getName())
                            .createTime(tagDO.getCreateTime())
                            .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(tagDOPage, vos);
    }

    @Override
    public Response deleteTag(DeleteTagReqVO deleteTagReqVO) {
        // 标签 id
        Long tagId = deleteTagReqVO.getId();
        // 根据标签id删除
        int result = tagMapper.deleteById(tagId);
        return result == 1? Response.success() : Response.fail(ResponseCodeEnum.TAG_NOT_EXISTED);
    }

    @Override
    public Response searchTag(SearchTagReqVO searchTagReqVO) {
        String key = searchTagReqVO.getKey();
        // 执行模糊查询
        List<TagDO> tagDOS = tagMapper.selectByKey(key);
        // do 转 vo
        List<SelectRspVO> vos = null;
        if (!CollectionUtils.isEmpty(tagDOS)) {
            vos = tagDOS
                    .stream()
                    .map(tagDO -> SelectRspVO
                            .builder()
                            .label(tagDO.getName())
                            .value(tagDO.getId())
                            .build())
                    .collect(Collectors.toList());
        }

        return Response.success(vos);
    }
}
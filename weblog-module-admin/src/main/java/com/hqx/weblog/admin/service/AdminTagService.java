package com.hqx.weblog.admin.service;

import com.hqx.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.hqx.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.hqx.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.hqx.weblog.admin.model.vo.tag.AddTagReqVO;
import com.hqx.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.hqx.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.hqx.weblog.admin.model.vo.tag.SearchTagReqVO;
import com.hqx.weblog.common.utils.PageResponse;
import com.hqx.weblog.common.utils.Response;

public interface AdminTagService {

    /**
     * 添加标签集合
     * @param addTagReqVO
     * @return
     */
    Response addTag(AddTagReqVO addTagReqVO);

    /**
     * 查询标签分页
     * @param findTagPageListReqVO
     * @return
     */
    PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO);

    /**
     * 删除标签
     * @param deleteTagReqVO
     * @return
     */
    Response deleteTag(DeleteTagReqVO deleteTagReqVO);

    /**
     * 根据标签关键词模糊查询
     * @param searchTagReqVO
     * @return
     */
    Response searchTag(SearchTagReqVO searchTagReqVO);
}
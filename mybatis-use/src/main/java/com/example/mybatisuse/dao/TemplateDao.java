package com.example.mybatisuse.dao;

import com.example.mybatisuse.entity.Template;
import com.example.mybatisuse.queryDto.TemplateQueryDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TemplateDao {
    List<Template> getAllTemplate();

    int insert(Template template);

    Template getTemplate(Object id);

    List<Template> getTemplateByIds(@Param("projectIds") List<String> ids,@Param("queryDto") TemplateQueryDto queryDto);
}

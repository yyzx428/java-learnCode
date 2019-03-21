package com.example.mybatisuse.mybatis.typeHandler;


import com.example.mybatisuse.entity.Content;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomTypeHandler extends BaseTypeHandler<Content> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Content parameter, JdbcType jdbcType) throws SQLException {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createGenerator(sw);
            objectMapper.writeValue(gen, parameter);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != gen) {
                try {
                    gen.close();
                } catch (IOException e) {

                }
            }
        }
        ps.setString(i, sw.toString());
    }

    @Override
    public Content getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return getContent(rs.getString(columnName));
    }

    @Override
    public Content getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return getContent(rs.getString(columnIndex));
    }

    @Override
    public Content getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return getContent(cs.getString(columnIndex));
    }

    private Content getContent(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, Content.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

/*
 * Copyright (c) 2015 by east.
 * All right reserved.
 */
package com.east.framework.core.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.east.framework.core.persistence.dao.page.PageInfo;
import com.east.framework.core.persistence.dao.page.PageStatement;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * CommonMapperDAO 구현체 EgovAbstractMapper 를 상속 함.
 *
 * Updated on : 2015-09-30 Updated by : love.
 */
public class CommonMapperDAOImpl extends EgovAbstractMapper implements CommonMapperDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonMapperDAOImpl.class);

    private String jdbcType;

    public final static String DB_TYPE_MYSQL = "mysql";

    /**
     * @return the jdbcType
     */
    public String getJdbcType() {
        return jdbcType;
    }

    /**
     * @param jdbcType the jdbcType to set
     */
    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

    /**
     * 단건조회 처리 SQL mapping 을 실행한다.
     *
     * @param <T> generic type class
     * @param queryId - 단건 조회 처리 SQL mapping 쿼리 ID
     * @param clazz generic type class
     *
     * @return 결과 객체 - SQL mapping 파일에서 지정한 resultType/resultMap 에 의한 단일 결과 객체(보통 VO 또는 Map)
     */
    @Override
    public <T> T selectOne(String queryId, Class<T> clazz) {
        return clazz.cast(getSqlSession().selectOne(queryId));
    }

    /**
     * 단건조회 처리 SQL mapping 을 실행한다.
     * 
     * @param <T> generic type class
     *
     * @param queryId - 단건 조회 처리 SQL mapping 쿼리 ID
     * @param parameterObject - 단건 조회 처리 SQL mapping 입력 데이터(key)를 세팅한 파라메터 객체(보통 VO 또는 Map)
     * @param clazz generic type class
     *
     * @return 결과 객체 - SQL mapping 파일에서 지정한 resultType/resultMap 에 의한 단일 결과 객체(보통 VO 또는 Map)
     */
    @Override
    public <T> T selectOne(String queryId, Object parameterObject, Class<T> clazz) {
        return clazz.cast(getSqlSession().selectOne(queryId, parameterObject));
    }

    /**
     * 부분 범위 리스트 조회 처리 SQL mapping 을 실행한다. (부분 범위 - pageIndex 와 pageSize 기반으로 현재 RowBounds 사용을 지양, EgovAbstractMapper 의 해당 메소드를 override 한 후 사용 하지 않도록 조치.
     * 
     * @param <T> generic type class
     * @param statement - 페이지조회를 위한 객체.
     * @param parameterObject - 리스트 조회 처리 SQL mapping 입력 데이터(조회 조건)를 세팅한 파라메터 객체(보통 VO 또는 Map)
     *
     * @return PageInfo
     * @throws Exception Exception
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> PageInfo<T> listWithPaging(PageStatement statement, Object parameterObject) {
        int pageIndex;
        int pageSize;
        try {
            pageIndex = Integer.parseInt((parameterObject.getClass().getMethod("getPageIndex")).invoke(parameterObject).toString());
            pageSize = Integer.parseInt((parameterObject.getClass().getMethod("getPageSize")).invoke(parameterObject).toString());
            (parameterObject.getClass().getMethod("setPageIndex", int.class)).invoke(parameterObject, pageIndex);
            (parameterObject.getClass().getMethod("setPageSize", int.class)).invoke(parameterObject, pageSize);

        } catch (Exception e) {
            LOGGER.error("페이징 쿼리 중 에러가 발생 했습니다. 페이징 쿼리의 객체는  접근 가능한 int pageIndex, int pageSize 두개의  필수 필드가 필요 합니다. \\n{}", e);
            throw new RuntimeException(e);
        }

        int totalCount = this.selectOne(statement.getTotalCountStatementId(), parameterObject, Integer.class);

        List<T> list = null;

        if (totalCount > 0) {
            if (StringUtils.equals(DB_TYPE_MYSQL, jdbcType)) {
                try {
                    (parameterObject.getClass().getMethod("setMysqlForPageTotalCount", int.class)).invoke(parameterObject, totalCount);
                    (parameterObject.getClass().getMethod("setMysqlForPageLimitS", int.class)).invoke(parameterObject, pageSize * (pageIndex - 1));
                } catch (Exception e) {
                    LOGGER.error("페이징 쿼리 중 에러가 발생 했습니다. mysql 페이징을 위해서는  int mysqlForPageTotalCount, int mysqlForPageLimitS 두개의  필수 필드가 필요 합니다. \\n{}", e);
                    throw new RuntimeException(e);
                }
            }

            list = (List<T>) this.selectList(statement.getDataStatementId(), parameterObject);
        }

        PageInfo<T> pageInfo = new PageInfo<T>(pageIndex, pageSize, totalCount, list == null ? new ArrayList<T>(0) : list);

        return pageInfo;
    }

    /**
     * 부분 범위 리스트 조회 처리 SQL mapping 을 실행한다. (부분 범위 - pageIndex 와 pageSize 기반으로 현재 RowBounds 사용을 지양, EgovAbstractMapper 의 해당 메소드를 override 한 후 사용 하지 않도록 조치.
     * 
     * @param <T> generic type class
     * @param statement - 페이지조회를 위한 객체.
     * @param parameterObject - 리스트 조회 처리 SQL mapping 입력 데이터(조회 조건)를 세팅한 파라메터 객체(보통 VO 또는 Map)
     * @param pageIndex - 현재 페이지 번호
     * @param pageSize - 한 페이지 조회 수(pageSize)
     *
     * @return PageInfo
     * @throws Exception Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> PageInfo<T> listWithPaging(PageStatement statement, Object parameterObject, int pageIndex, int pageSize) {

        try {
            (parameterObject.getClass().getMethod("setPageIndex", int.class)).invoke(parameterObject, pageIndex);
            (parameterObject.getClass().getMethod("setPageSize", int.class)).invoke(parameterObject, pageSize);
        } catch (Exception e) {
            LOGGER.error("페이징 쿼리 중 에러가 발생 했습니다. 페이징 쿼리의 객체는  접근 가능한 int pageIndex, int pageSize 두개의  필수 필드가 필요 합니다. \\n{}", e);
            throw new RuntimeException(e);
        }

        int totalCount = this.selectOne(statement.getTotalCountStatementId(), parameterObject, Integer.class);

        List<T> list = null;

        if (totalCount > 0) {
            list = (List<T>) this.selectList(statement.getDataStatementId(), parameterObject);
        }

        PageInfo<T> pageInfo = new PageInfo<T>(pageIndex, pageSize, totalCount, list == null ? new ArrayList<T>(0) : list);

        return pageInfo;
    }

}

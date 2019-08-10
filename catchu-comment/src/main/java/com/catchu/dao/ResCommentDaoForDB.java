package com.catchu.dao;

import com.catchu.beans.model.ResCommentRecordModel;
import com.catchu.common.beans.PageQuery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

@Mapper
public interface ResCommentDaoForDB {

    @Insert("insert into ${tableName} (id,resourceId,resourceType,userId,userType,contentOriginal,contentShow,verifyStatus,verifyChannel,stickStatus,recordStatus,upvoteNum,contentType,createTime) values(#{rsr.id},#{rsr.resourceId},#{rsr.resourceType},#{rsr.userId},#{rsr.userType},#{rsr.contentOriginal},#{rsr.contentShow},#{rsr.verifyStatus},#{rsr.verifyChannel},#{rsr.stickStatus},#{rsr.recordStatus},#{rsr.upvoteNum},#{rsr.contentType},#{rsr.createTime})")
    int insert(@Param("tableName") String tableName, @Param("rsr") ResCommentRecordModel rsr);

    @Insert("<script>" +
            "insert into ${tableName} (id,resourceId,resourceType,userId,userType,contentOriginal,contentShow,verifyStatus,verifyChannel,stickStatus,recordStatus,upvoteNum,contentType,createTime) values" +
            "<foreach collection='rcrs' item='rsr' separator=','>" +
            "(#{rsr.id},#{rsr.resourceId},#{rsr.resourceType},#{rsr.userId},#{rsr.userType},#{rsr.contentOriginal},#{rsr.contentShow},#{rsr.verifyStatus},#{rsr.verifyChannel},#{rsr.stickStatus},#{rsr.recordStatus},#{rsr.upvoteNum},#{rsr.contentType},#{rsr.createTime})" +
            "</foreach>" +
            "</script>")
    int insertMulti(@Param("tableName") String tableName, @Param("rcrs") List<ResCommentRecordModel> rcrs);

    @Select("<script>" +
            " select * from ${tableName} where resourceId = #{resourceId} and resourceType = #{resourceType} and recordStatus = #{recordStatus} and verifyStatus in " +
            "<foreach collection='verifyStatus' item='vs' separator=',' open='(' close=')'>" +
            " #{vs} " +
            "</foreach>" +
            " order by stickStatus desc,createTime desc limit #{pageQuery.skipSize},#{pageQuery.pageSize}" +
            "</script>")
    List<ResCommentRecordModel> list1(@Param("tableName") String tableName,
                                      @Param("pageQuery") PageQuery pageQuery,
                                      @Param("resourceId") long resourceId,
                                      @Param("resourceType") int resourceType,
                                      @Param("verifyStatus") Set<Integer> verifyStatus,
                                      @Param("recordStatus") int recordStatus);

    @Select("<script>" +
            " select * from ${tableName} where resourceId = #{resourceId} and resourceType = #{resourceType} and recordStatus = #{recordStatus} and verifyStatus in " +
            "<foreach collection='verifyStatus' item='vs' separator=',' open='(' close=')'>" +
            " #{vs} " +
            "</foreach>" +
            " order by upvoteNum desc limit #{pageQuery.skipSize},#{pageQuery.pageSize}" +
            "</script>")
    List<ResCommentRecordModel> list2(@Param("tableName") String tableName,
                                      @Param("pageQuery") PageQuery pageQuery,
                                      @Param("resourceId") long resourceId,
                                      @Param("resourceType") int resourceType,
                                      @Param("verifyStatus") Set<Integer> verifyStatus,
                                      @Param("recordStatus") int recordStatus);

    @Select("<script>" +
            " select * from ${tableName} where resourceId = #{resourceId} and resourceType = #{resourceType} and userId = #{userId} and recordStatus = #{recordStatus} and verifyStatus in " +
            "<foreach collection='verifyStatus' item='vs' separator=',' open='(' close=')'>" +
            " #{vs} " +
            "</foreach>" +
            " order by createTime desc" +
            "</script>")
    List<ResCommentRecordModel> lastByResourceAndUser(@Param("tableName") String tableName,
                                                      @Param("resourceId") long resourceId,
                                                      @Param("resourceType") int resourceType,
                                                      @Param("userId") long userId,
                                                      @Param("verifyStatus") Set<Integer> verifyStatus,
                                                      @Param("recordStatus") int recordStatus);

    @Select("<script>" +
            " select count(*) from ${tableName} where resourceId = #{resourceId} and resourceType = #{resourceType} and recordStatus = #{recordStatus} and verifyStatus in " +
            "<foreach collection='verifyStatus' item='vs' separator=',' open='(' close=')'>" +
            " #{vs} " +
            "</foreach>" +
            "</script>")
    int count(@Param("tableName") String tableName,
              @Param("resourceId") long resourceId,
              @Param("resourceType") int resourceType,
              @Param("verifyStatus") Set<Integer> verifyStatus,
              @Param("recordStatus") int recordStatus);

    @Update("update ${tableName} set upvoteNum = upvoteNum + #{incNum} where id = #{id}")
    int increaseUpvoteNum(@Param("tableName") String tableName, @Param("id") long id, @Param("incNum") long incNum);

    @Update("update ${tableName} set verifyStatus = #{verifyStatus} , verifyChannel = #{verifyChannel} where id = #{id}")
    int updateVerifyStatus(@Param("tableName") String tableName, @Param("id") long id, @Param("verifyStatus") int verifyStatus, @Param("verifyChannel") int verifyChannel);

    @Update("update ${tableName} set recordStatus = 0 where id = #{id}")
    int updateRecordStatus(@Param("tableName") String tableName, @Param("id") long id);

    @Update("update ${tableName} set stickStatus = #{stickStatus} where id = #{id}")
    int updateStickStatus(@Param("tableName") String tableName, @Param("id") long id, @Param("stickStatus") int stickStatus);
}

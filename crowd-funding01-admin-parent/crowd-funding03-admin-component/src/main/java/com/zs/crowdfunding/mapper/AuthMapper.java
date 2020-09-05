package com.zs.crowdfunding.mapper;

import com.zs.crowdfunding.entity.Auth;
import com.zs.crowdfunding.entity.AuthExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AuthMapper {
    int countByExample(AuthExample example);

    int deleteByExample(AuthExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    int insertSelective(Auth record);

    List<Auth> selectByExample(AuthExample example);

    Auth selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByExample(@Param("record") Auth record, @Param("example") AuthExample example);

    int updateByPrimaryKeySelective(Auth record);

    int updateByPrimaryKey(Auth record);

	List<Integer> selectAssignAuthIdByRoId(Integer roleId);

	void insertRoleAuthRelathInship(@Param("roleId") Integer roleId,@Param("authIdArray") List<Integer> authIdArray);

	void deleteOldRoleAuthRelathInship(Integer roleId);

	List<String> selectAssignedAuthNameByAdminId(Integer adminId);
}
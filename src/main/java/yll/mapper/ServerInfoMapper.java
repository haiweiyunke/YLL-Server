package yll.mapper;

import org.apache.ibatis.annotations.Mapper;

import yll.entity.ServerInfo;
import yll.mapper.basic.BasicMapper;

/**
 * 系统序列
 */
@Mapper
public interface ServerInfoMapper extends BasicMapper<ServerInfo, String> {
    //
}
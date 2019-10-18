package com.lee.uid.provider.mapper;

import com.baidu.fsg.uid.worker.entity.WorkerNodeEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author lee.li
 */
@Mapper
public interface WorkerNodeMapper {
    /**
     * Get {@link WorkerNodeEntity} by node host
     *
     * @param host
     * @param port
     * @return
     */
    @Select("SELECT" +
            " ID," +
            " HOST_NAME," +
            " PORT," +
            " TYPE," +
            " LAUNCH_DATE," +
            " MODIFIED," +
            " CREATED" +
            " FROM" +
            " WORKER_NODE" +
            " WHERE" +
            " HOST_NAME = #{host} AND PORT = #{port}")
    WorkerNodeEntity getWorkerNodeByHostPort(@Param("host") String host, @Param("port") String port);

    /**
     * Add {@link WorkerNodeEntity}
     *
     * @param workerNodeEntity
     */
    @Insert("INSERT INTO WORKER_NODE " +
            " (HOST_NAME, " +
            " PORT, " +
            " TYPE, " +
            " LAUNCH_DATE, " +
            " MODIFIED, " +
            " CREATED) " +
            " VALUES ( " +
            " #{hostName}, " +
            " #{port}, " +
            " #{type}, " +
            " #{launchDate}, " +
            " NOW(), " +
            " NOW())")
    void addWorkerNode(WorkerNodeEntity workerNodeEntity);
}

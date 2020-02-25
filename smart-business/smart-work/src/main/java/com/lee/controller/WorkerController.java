package com.lee.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lee.api.entity.SysUser;
import com.lee.api.feign.RemoteUserClient;
import com.lee.common.business.util.PaginationResponseUtil;
import com.lee.common.core.Pagination;
import com.lee.common.core.response.BaseResponse;
import com.lee.common.core.response.PaginationResponse;
import com.lee.common.core.util.WebUtil;
import com.lee.model.WorkerVO;
import com.lee.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.lee.model.WorkerDTO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lee.li
 * 伙伴
 */
@Slf4j
@RestController
@RequestMapping("/worker")
public class WorkerController {
    private static final String NEED_ID_OR_NAME_PARAM = "ID或者姓名参数无效";
    private static final String NEED_NAME_PARAM = "姓名参数无效";
    private static final String NEED_ID_PARAM = "ID参数无效";
    @Resource
    private WorkerService service;
    @Resource
    private ModelMapper modelMapper;
    @Resource
    private RemoteUserClient remoteUserClient;

    /**
     * 通过名称获取伙伴信息
     *
     * @param name 伙伴名称包含字段
     * @return 所有包含请求参数name的伙伴信息
     */
    @GetMapping
    public BaseResponse<List<WorkerVO>> getWorkersByName(@RequestHeader("authorization") String authorization,
                                                         @RequestParam("name") String name) {
        //通過token得到用戶信息
        String accessToken = WebUtil.getAccessToken(authorization);
        BaseResponse<SysUser> baseResponse = remoteUserClient.getCurrentUser(accessToken);
        Long userId = baseResponse.getData().getId();
        WorkerDTO workerDTO = new WorkerDTO();
        workerDTO.setName(name);
        return null;
    }

    /**
     * 查询雇员信息
     *
     * @param workerVO 雇员信息查询
     * @return 分页后对象
     */
    @GetMapping("page")
    public BaseResponse<PaginationResponse<WorkerVO>> pageList(Pagination pagination, WorkerVO workerVO) {

        WorkerDTO workerDTO = modelMapper.map(workerVO, WorkerDTO.class);

        IPage<WorkerDTO> iPage = service.pageList(pagination, workerDTO);

        PaginationResponse<WorkerVO> paginationResponse = convertToPageResponse(iPage);

        BaseResponse<PaginationResponse<WorkerVO>> baseResponse = BaseResponse.ok(paginationResponse);
        return baseResponse;
    }

    /**
     * 转换 service层数据 到 controller层数据
     *
     * @param ipage
     * @return
     */
    private PaginationResponse<WorkerVO> convertToPageResponse(IPage<WorkerDTO> ipage) {
        List<WorkerVO> returnList = new ArrayList<>();
        List<WorkerDTO> list = ipage.getRecords();
        if (list != null && list.size() > 0) {
            list.stream().forEach(dto -> {
                WorkerVO vo = modelMapper.map(dto, WorkerVO.class);
                returnList.add(vo);
            });
        }
        return PaginationResponseUtil.buildPagination(ipage.getCurrent(), ipage.getSize(), ipage.getTotal(), returnList);
    }

    /**
     * 新增雇员信息
     *
     * @param workerVO
     * @return
     */
    @PostMapping
    public BaseResponse<WorkerVO> create(@RequestBody WorkerVO workerVO) {
        if (log.isDebugEnabled()) {
            log.debug("[WorkerController] create,request param:{}", workerVO);
        }
        String name = workerVO.getName();
        if (StringUtils.isEmpty(name)) {
            return BaseResponse.error(String.valueOf(HttpStatus.BAD_REQUEST.value()), NEED_NAME_PARAM);
        }
        WorkerDTO workerDTO = modelMapper.map(workerVO, WorkerDTO.class);
        workerDTO = service.insert(workerDTO);
        workerVO = modelMapper.map(workerDTO, WorkerVO.class);
        BaseResponse<WorkerVO> baseResponse = BaseResponse.ok(workerVO);
        return baseResponse;
    }

    /**
     * 更新雇员信息
     *
     * @param workerVO
     * @return
     */
    @PutMapping
    public BaseResponse<Integer> updateById(@RequestBody WorkerVO workerVO) {
        int id = workerVO.getId();
        if (id <= 0) {
            return BaseResponse.error(String.valueOf(HttpStatus.BAD_REQUEST.value()), NEED_ID_PARAM);
        }
        WorkerDTO workerDTO = modelMapper.map(workerVO, WorkerDTO.class);
        Integer count = service.updateById(workerDTO);
        return BaseResponse.ok(count);
    }

    /**
     * 删除雇员信息
     *
     * @param workerVO
     * @return
     */
    @DeleteMapping
    public BaseResponse<Integer> deleteWorker(@RequestBody WorkerVO workerVO) {
        int id = workerVO.getId();
        if (id <= 0) {
            return BaseResponse.error(String.valueOf(HttpStatus.BAD_REQUEST.value()), NEED_ID_PARAM);
        }
        Integer count = service.deleteById(id);
        return BaseResponse.ok(count);
    }

    /**
     * 检验参数正确性，id & name 两者 必须输入一个
     *
     * @param workerVO 雇员信息
     * @return 参数校验正确性
     */
    private Boolean checkParam(WorkerVO workerVO) {
        Integer id = workerVO.getId();
        String name = workerVO.getName();
        if (id == null && StringUtils.isEmpty(name)) {
            return false;
        }
        return true;
    }


}

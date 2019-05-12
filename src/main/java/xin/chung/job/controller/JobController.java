package xin.chung.job.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import xin.chung.job.entity.Recruitment;
import xin.chung.job.entity.RecruitmentDTO;
import xin.chung.job.entity.ResponseDTO;
import xin.chung.job.entity.User;
import xin.chung.job.enums.Address;
import xin.chung.job.enums.Progress;
import xin.chung.job.enums.SubType;
import xin.chung.job.service.JobService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Author Chung
 * @Date 2018/09/22 18:36
 */
@CrossOrigin(origins = "http://192.168.0.7:8080", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/job")
@Api(value = "job", description = "数据相关API")
public class JobController {
    @Autowired
    public JobService jobService;

    /**
     * 获取所有已投
     *
     * @return
     */
    @GetMapping("/")
    public Page<RecruitmentDTO> getAll(HttpServletRequest request, @RequestParam int page, @RequestParam int pageSize) {
        Page<Recruitment> recruitments = jobService.findAll(getLoginUserId(request), page, pageSize);
        return recruitments.map(this::convert);
    }

    /**
     * 获取对应状态投递记录
     *
     * @param request
     * @param
     * @return
     */
    @GetMapping("/filter")
    public Page<RecruitmentDTO> getAllByParams(
            HttpServletRequest request, @RequestParam int page, @RequestParam int pageSize, @RequestParam int subSort,
            @RequestParam int updSort, @RequestParam String name, @RequestParam String addr, @RequestParam String progress
    ) {
        Page<Recruitment> recruitments = jobService.findAllByParams(
                page, pageSize, getLoginUserId(request), subSort, updSort, name, addr, progress
        );
        return recruitments.map(this::convert);
    }

    /**
     * 转换器
     *
     * @param item
     * @return
     */
    private RecruitmentDTO convert(Recruitment item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return RecruitmentDTO.builder()
                .id(item.getId())
                .companyName(item.getCompanyName())
                .companyAddress(Address.value2Desc(item.getCompanyAddress()))
                .submitTime(sdf.format(item.getSubmitTime()))
                .submitType(SubType.value2Desc(item.getSubmitType()))
                .progress(Progress.value2Desc(item.getProgress()))
                .comment(item.getComment())
                .updateTime(sdf.format(item.getUpdateTime()))
                .history(item.getHistory())
                .build();
    }

    /**
     * 新增投递记录
     *
     * @param param
     */
    @PostMapping("/")
    public ResponseDTO addJob(HttpServletRequest request, @RequestBody RecruitmentDTO param) throws ParseException {
        return jobService.addOrUpdateJob(param, getLoginUserId(request), true);
    }

    /**
     * 修改投递记录
     *
     * @param param
     */
    @PutMapping("/")
    public ResponseDTO update(HttpServletRequest request, @RequestBody RecruitmentDTO param) throws ParseException {
        return jobService.addOrUpdateJob(param, getLoginUserId(request), false);
    }

    /**
     * 删除投递记录
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseDTO delete(@PathVariable int id) {
        return jobService.delJob(id);
    }

    /**
     * 批量删除投递记录
     *
     * @param ids
     */
    @DeleteMapping("/list/{ids}")
    public ResponseDTO delList(@PathVariable String ids) {
        ResponseDTO result = null;
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) {
            result = jobService.delJob(Integer.parseInt(split[i]));
            if (result.code != 200) {
                break;
            }
        }
        return result;
    }

    /**
     * 获取当前登录用户ID
     *
     * @param request
     * @return
     */
    private int getLoginUserId(HttpServletRequest request) {
        User loginUser = (User) request.getSession().getAttribute("loginInfo");
        return loginUser.getId();
    }

    /**
     * 导出excel
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/excel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        jobService.exportExcel(getLoginUserId(request), response);
    }
}

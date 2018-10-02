package xin.chung.job.service;

import net.sf.json.JSONArray;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import xin.chung.job.entity.HistoryDTO;
import xin.chung.job.entity.Recruitment;
import xin.chung.job.entity.RecruitmentDTO;
import xin.chung.job.entity.ResponseDTO;
import xin.chung.job.enums.*;
import xin.chung.job.repository.JobRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author Chung
 * @Date 2018/09/22 19:04
 */
@Service
public class JobService {
    @Autowired
    public JobRepository jobRepository;

    public Page<Recruitment> findAll(int userId, int page, int pageSize) {
        Pageable pageable = new PageRequest(page - 1, pageSize, Sort.Direction.ASC, "id");
        return jobRepository.findByUserIdAndStatus(userId, DBStatus.NOR.value, pageable);
    }

    public ResponseDTO addOrUpdateJob(RecruitmentDTO dto, int userId, boolean isAdd) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String history = "[]";
        if (!isAdd) {
            Recruitment old = jobRepository.findByIdAndStatus(dto.getId(), DBStatus.NOR.value);
            boolean added = false;
            // 如果old进度不为空，且old进度和新进度不同
            if (null != old && !old.getProgress().equals(Progress.descToValue(dto.getProgress()))) {
                JSONArray jsonArray = JSONArray.fromObject(old.getHistory());
                HistoryDTO historyDTO = null;
                // 有新进度说明前一步通过了
                int proV = Progress.descToValue(dto.getProgress());
                if (proV != Progress.WAIT_HR_INTERVIEW.value
                        && proV != Progress.WAIT_THE_OFFER.value
                        && proV != Progress.ALL_FINISH.value
                        && proV != Progress.GOT_OFFER.value) {
                    historyDTO = new HistoryDTO(HistoryProgress.value2Desc(proV), sdfH.format(new Date()));
                } else {
                    // 如果进度变为等待HR面，则显示为旧进度通过
                    if (proV == Progress.WAIT_HR_INTERVIEW.value) {
                        historyDTO = new HistoryDTO(HistoryProgress.value2Desc(old.getProgress() + 1), sdfH.format(new Date()));
                    }

                    // 如果进度变为待通知，则存储为HR面通过
                    if (proV == Progress.WAIT_THE_OFFER.value) {
                        historyDTO = new HistoryDTO(HistoryProgress.HR_INTERVIEW_SUCCEED.desc, sdfH.format(new Date()));
                    }

                    // 如果进度变为结束[被拒]，则存储为old进度的未通过状态
                    if (proV == Progress.ALL_FINISH.value) {
                        int value = old.getProgress() + 10;
                        historyDTO = new HistoryDTO(HistoryProgress.value2Desc(value), sdfH.format(new Date()));
                    }

                    // 如果进度变为拿到OFFER，则存储为GOT OFFER
                    if (proV == Progress.GOT_OFFER.value) {
                        historyDTO = new HistoryDTO(HistoryProgress.GOT_OFFER.desc, sdfH.format(new Date()));
                    }
                }

                added = jsonArray.add(historyDTO);
                history = jsonArray.toString();
            }
            if (!added) {
                history = old.getHistory();
            }
        }

        Recruitment recruitment = Recruitment.builder()
                .id(isAdd ? null : dto.getId())
                .userId(userId)
                .companyName(dto.getCompanyName())
                .companyAddress(Address.descToValue(dto.getCompanyAddress()))
                .submitTime(sdf.parse(dto.getSubmitTime()))
                .submitType(SubType.descToValue(dto.getSubmitType()))
                .progress(Progress.descToValue(dto.getProgress()))
                .comment(dto.getComment())
                .updateTime(new Date())
                .status(DBStatus.NOR.value)
                .history(history)
                .build();
        Recruitment save = jobRepository.save(recruitment);
        return ResponseDTO.builder()
                .code(null == save.getId() ? 500 : 200)
                .msg(null == save.getId() ? "操作失败" : "操作成功")
                .build();
    }

    public ResponseDTO delJob(int id) {
        Recruitment del = jobRepository.findByIdAndStatus(id, DBStatus.NOR.value);
        if (null == del) {
            return ResponseDTO.builder()
                    .code(404)
                    .msg("删除失败")
                    .build();
        }
        del.setStatus(DBStatus.DEL.value);
        Recruitment save = jobRepository.save(del);
        return ResponseDTO.builder()
                .code(null == save.getId() ? 500 : 200)
                .msg(null == save.getId() ? "删除失败" : "删除成功")
                .build();
    }

    public Page<Recruitment> findAllByParams(int page, int pageSize, int userId, int subSort, int updSort, String name, String addr, String progress) {
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        if (subSort != -1) {
            if (subSort == 0) {
                sort = new Sort(Sort.Direction.ASC, "submitTime");
            }
            if (subSort == 1) {
                sort = new Sort(Sort.Direction.DESC, "submitTime");
            }
        }
        if (updSort != -1) {
            if (updSort == 0) {
                sort = new Sort(Sort.Direction.ASC, "updateTime");
            }
            if (updSort == 1) {
                sort = new Sort(Sort.Direction.DESC, "updateTime");
            }
        }

        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        return jobRepository.findAll((Specification<Recruitment>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("userId").as(Integer.class), userId));
            list.add(criteriaBuilder.equal(root.get("status").as(Integer.class), DBStatus.NOR.value));
            if (!StringUtils.isEmpty(name)) {
                list.add(criteriaBuilder.like(root.get("companyName").as(String.class), "%" + name + "%"));
            }

            if (!StringUtils.isEmpty(progress)) {
                list.add(criteriaBuilder.equal(root.get("progress").as(Integer.class), Progress.descToValue(progress)));
            }

            if (!StringUtils.isEmpty(addr)) {
                list.add(criteriaBuilder.equal(root.get("companyAddress").as(Integer.class), Address.descToValue(addr)));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
    }

    public void exportExcel(int userId, HttpServletResponse response) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Recruitment> allRecords = jobRepository.findAllByUserIdAndStatus(userId, DBStatus.NOR.value);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("记录");
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd"));
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        for (int i = 0; i < allRecords.size() + 1; i++) {
            sheet.addMergedRegion(new CellRangeAddress(i, i, 6, 10));
        }

        //headers表示excel表中第一行的表头
        String[] headers = {"公司", "地点", "投递时间", "投递类型", "当前进度", "更新时间", "备注"};
        //在excel表中添加表头
        HSSFRow row = sheet.createRow(0);
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        for (int i = 0; i < headers.length; i++) {
            style.setFont(font);
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
            cell.setCellStyle(style);
        }


        int rowNum = 1;
        //在表中存放查询到的数据放入对应的列
        for (Recruitment item : allRecords) {
            HSSFRow hssfRow = sheet.createRow(rowNum);
            hssfRow.createCell(0).setCellValue(item.getCompanyName());
            hssfRow.createCell(1).setCellValue(Address.value2Desc(item.getCompanyAddress()));
            hssfRow.createCell(2).setCellValue(sdf.format(item.getSubmitTime()));
            hssfRow.createCell(3).setCellValue(SubType.value2Desc(item.getSubmitType()));
            hssfRow.createCell(4).setCellValue(Progress.value2Desc(item.getProgress()));
            hssfRow.createCell(5).setCellValue(sdf.format(item.getUpdateTime()));
            hssfRow.createCell(6).setCellValue(item.getComment());
            rowNum++;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=JobRecord.xls");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}

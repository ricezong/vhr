package com.gz.vhr.utils;

import com.gz.vhr.bean.*;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName POIUtils
 * @Description Excel工具类
 * @Author 孔明灯
 * @Data 2022/1/19 11:00
 * @Version 1.0
 */
public class POIUtils {

    /**
     * 文件下载
     * @param list
     * @return
     */
    public static ResponseEntity<byte[]> employee2Excel(List<Employee> list) {
        //创建一个Excel文档
        HSSFWorkbook workbook=new HSSFWorkbook();
        //创建文档摘要
        workbook.createInformationProperties();
        //配置文件摘要信息
        DocumentSummaryInformation docInfo=workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("员工信息");
        //文档管理员
        docInfo.setManager("gaozong");
        //设置公司信息
        docInfo.setCompany("no company");
        //配置文档摘要信息
        SummaryInformation sumInfo = workbook.getSummaryInformation();
        //文档标题
        sumInfo.setTitle("员工信息表");
        //文档作者
        sumInfo.setAuthor("gaozong");
        //文档备注
        sumInfo.setComments("本文档由粽提供");
        //创建表单
        HSSFSheet sheet = workbook.createSheet("员工信息表");
        //创建标题行样式
        HSSFCellStyle headStyle=workbook.createCellStyle();
        //设置日期样式
        HSSFCellStyle dateStyle=workbook.createCellStyle();
        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        //设置填充前景色
        headStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        //设置填充样式
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //创建标题行
        HSSFRow rowHead = sheet.createRow(0);
        //创建标题行首列内容
        HSSFCell cell0 = rowHead.createCell(0);
        cell0.setCellValue("编号");
        cell0.setCellStyle(headStyle);;

        HSSFCell cell1 = rowHead.createCell(1);
        cell1.setCellValue("姓名");
        cell1.setCellStyle(headStyle);

        HSSFCell cell2 = rowHead.createCell(2);
        cell2.setCellValue("工号");
        cell2.setCellStyle(headStyle);

        HSSFCell cell3 = rowHead.createCell(3);
        cell3.setCellValue("性别");
        cell3.setCellStyle(headStyle);

        HSSFCell cell4 = rowHead.createCell(4);
        cell4.setCellValue("出生日期");
        cell4.setCellStyle(headStyle);

        HSSFCell cell5 = rowHead.createCell(5);
        cell5.setCellValue("身份证号码");
        cell5.setCellStyle(headStyle);

        HSSFCell cell6 = rowHead.createCell(6);
        cell6.setCellValue("婚烟状况");
        cell6.setCellStyle(headStyle);

        HSSFCell cell7 = rowHead.createCell(7);
        cell7.setCellValue("民族");
        cell7.setCellStyle(headStyle);

        HSSFCell cell8 = rowHead.createCell(8);
        cell8.setCellValue("籍贯");
        cell8.setCellStyle(headStyle);

        HSSFCell cell9 = rowHead.createCell(9);
        cell9.setCellValue("政治面貌");
        cell9.setCellStyle(headStyle);

        HSSFCell cell10 = rowHead.createCell(10);
        cell10.setCellValue("电子邮件");
        cell10.setCellStyle(headStyle);

        HSSFCell cell11 = rowHead.createCell(11);
        cell11.setCellValue("电话号码");
        cell11.setCellStyle(headStyle);

        HSSFCell cell12 = rowHead.createCell(12);
        cell12.setCellValue("联系地址");
        cell12.setCellStyle(headStyle);

        HSSFCell cell13 = rowHead.createCell(13);
        cell13.setCellValue("所属部门");
        cell13.setCellStyle(headStyle);

        HSSFCell cell14 = rowHead.createCell(14);
        cell14.setCellValue("职位");
        cell14.setCellStyle(headStyle);

        HSSFCell cell15 = rowHead.createCell(15);
        cell15.setCellValue("职称");
        cell15.setCellStyle(headStyle);

        HSSFCell cell16 = rowHead.createCell(16);
        cell16.setCellValue("聘用形式");
        cell16.setCellStyle(headStyle);

        HSSFCell cell17 = rowHead.createCell(17);
        cell17.setCellValue("最高学历");
        cell17.setCellStyle(headStyle);

        HSSFCell cell18 = rowHead.createCell(18);
        cell18.setCellValue("专业");
        cell18.setCellStyle(headStyle);

        HSSFCell cell19 = rowHead.createCell(19);
        cell19.setCellValue("毕业院校");
        cell19.setCellStyle(headStyle);

        HSSFCell cell20 = rowHead.createCell(20);
        cell20.setCellValue("入职日期");
        cell20.setCellStyle(headStyle);

        HSSFCell cell21 = rowHead.createCell(21);
        cell21.setCellValue("转正日期");
        cell21.setCellStyle(headStyle);

        HSSFCell cell22 = rowHead.createCell(22);
        cell22.setCellValue("合同起始日期");
        cell22.setCellStyle(headStyle);

        HSSFCell cell23 = rowHead.createCell(23);
        cell23.setCellValue("合同截止日期");
        cell23.setCellStyle(headStyle);

        HSSFCell cell24 = rowHead.createCell(24);
        cell24.setCellValue("合同期限");
        cell24.setCellStyle(headStyle);

        for (int i = 0; i < list.size(); i++) {
            Employee emp=list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(emp.getId());
            row.createCell(1).setCellValue(emp.getName());
            row.createCell(2).setCellValue(emp.getWorkID());
            row.createCell(3).setCellValue(emp.getGender());
            HSSFCell c4 = row.createCell(4);
            c4.setCellStyle(dateStyle);
            c4.setCellValue(emp.getBirthday());
            row.createCell(5).setCellValue(emp.getIdCard());
            row.createCell(6).setCellValue(emp.getWedlock());
            row.createCell(7).setCellValue(emp.getNation().getName());
            row.createCell(8).setCellValue(emp.getNativePlace());
            row.createCell(9).setCellValue(emp.getPoliticsStatus().getName());
            row.createCell(10).setCellValue(emp.getEmail());
            row.createCell(11).setCellValue(emp.getPhone());
            row.createCell(12).setCellValue(emp.getAddress());
            row.createCell(13).setCellValue(emp.getDepartment().getName());
            row.createCell(14).setCellValue(emp.getPosition().getName());
            row.createCell(15).setCellValue(emp.getJobLevel().getName());
            row.createCell(16).setCellValue(emp.getEngageForm());
            row.createCell(17).setCellValue(emp.getTiptopDegree());
            row.createCell(18).setCellValue(emp.getSpecialty());
            row.createCell(19).setCellValue(emp.getSchool());
            HSSFCell c20 = row.createCell(20);
            c20.setCellStyle(dateStyle);
            c20.setCellValue(emp.getBeginDate());
            HSSFCell c21 = row.createCell(21);
            c21.setCellStyle(dateStyle);
            c21.setCellValue(emp.getConversionTime());
            HSSFCell c22 = row.createCell(22);
            c22.setCellStyle(dateStyle);
            c22.setCellValue(emp.getBeginContract());
            HSSFCell c23 = row.createCell(23);
            c23.setCellStyle(dateStyle);
            c23.setCellValue(emp.getEndContract());
            row.createCell(24).setCellValue(emp.getContractTerm());
        }
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        HttpHeaders headers=new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment",new String("员工表.xls".getBytes("UTF-8"),"ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(baos.toByteArray(),headers, HttpStatus.CREATED);
    }

    /**
     * Excel解析成员工集合
     * @param file
     * @param allNations
     * @param allPoliticsStatus
     * @param allDepartments
     * @param allPositions
     * @param allJobLevel
     * @return
     */
    public static List<Employee> excel2Employee(MultipartFile file, List<Nation> allNations, List<PoliticsStatus> allPoliticsStatus, List<Department> allDepartments, List<Position> allPositions, List<JobLevel> allJobLevel) {
        Employee employee=null;
        List<Employee> list=new ArrayList<>();
        try {
            //创建一个workbook对象
            HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
            //获取workbook中的表单数量
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                //获取表单
                HSSFSheet sheet = workbook.getSheetAt(i);
                //获取表单中的行数
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    if (j == 0) {
                        continue;//跳过标题行
                    }
                    //获取行
                    HSSFRow row = sheet.getRow(j);
                    if (row == null) {
                        continue;//防止数据中间有空行
                    }
                    //获取总列数
                    int physicalNumberOfCells = row.getPhysicalNumberOfCells();
                    employee=new Employee();
                    for (int k = 0; k < physicalNumberOfCells; k++) {
                        HSSFCell cell = row.getCell(k);
                        switch (cell.getCellType()) {
                            case STRING:
                                String cellValue = cell.getStringCellValue();
                                switch (k){
                                    case 1:
                                        employee.setName(cellValue);
                                        break;
                                    case 2:
                                        employee.setWorkID(cellValue);
                                        break;
                                    case 3:
                                        employee.setGender(cellValue);
                                        break;
                                    case 5:
                                        employee.setIdCard(cellValue);
                                        break;
                                    case 6:
                                        employee.setWedlock(cellValue);
                                        break;
                                    case 7:
                                        Nation nation=null;
                                        for (int l = 0; l <allNations.size(); l++) {
                                            nation=allNations.get(l);
                                            if (cellValue.equals(nation.getName())){
                                                employee.setNationId(nation.getId());
                                                break;
                                            }
                                        }
                                    case 8:
                                        employee.setNativePlace(cellValue);
                                        break;
                                    case 9:
                                        PoliticsStatus politicsStatus=null;
                                        for (int l = 0; l < allPoliticsStatus.size(); l++) {
                                            politicsStatus=allPoliticsStatus.get(l);
                                            if (cellValue.equals(politicsStatus.getName())){
                                                employee.setPoliticId(politicsStatus.getId());
                                                break;
                                            }
                                        }
                                    case 10:
                                        employee.setEmail(cellValue);
                                        break;
                                    case 11:
                                        employee.setPhone(cellValue);
                                        break;
                                    case 12:
                                        employee.setAddress(cellValue);
                                        break;
                                    case 13:
                                        Department department=null;
                                        for (int l = 0; l < allDepartments.size(); l++) {
                                            department=allDepartments.get(l);
                                            if (cellValue.equals(department.getName())){
                                                employee.setDepartmentId(department.getId());
                                                break;
                                            }
                                        }
                                    case 14:
                                        Position position=null;
                                        for (int l = 0; l < allPositions.size(); l++) {
                                            position=allPositions.get(l);
                                            if (cellValue.equals(position.getName())){
                                                employee.setPosId(position.getId());
                                                break;
                                            }
                                        }
                                    case 15:
                                        JobLevel jobLevel=null;
                                        for (int l = 0; l < allJobLevel.size(); l++) {
                                            jobLevel=allJobLevel.get(l);
                                            if (cellValue.equals(jobLevel.getName())){
                                                employee.setJobLevelId(jobLevel.getId());
                                                break;
                                            }
                                        }
                                    case 16:
                                        employee.setEngageForm(cellValue);
                                        break;
                                    case 17:
                                        employee.setTiptopDegree(cellValue);
                                        break;
                                    case 18:
                                        employee.setSpecialty(cellValue);
                                        break;
                                    case 19:
                                        employee.setSchool(cellValue);
                                        break;
                                }
                                break;
                            default:{
                                Date dateCellValue=cell.getDateCellValue();
                                switch (k){
                                    case 4:
                                        employee.setBirthday(dateCellValue);
                                        break;
                                    case 20:
                                        employee.setBeginDate(dateCellValue);
                                        break;
                                    case 21:
                                        employee.setConversionTime(dateCellValue);
                                        break;
                                    case 22:
                                        employee.setBeginContract(dateCellValue);
                                        break;
                                    case 23:
                                        employee.setEndContract(dateCellValue);
                                        break;
                                    case 24:
                                        employee.setContractTerm(cell.getNumericCellValue());
                                        break;
                                }
                            }
                            break;
                        }
                    }
                    list.add(employee);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}

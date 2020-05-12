package com.Kotori.web;

import com.Kotori.domain.AjaxResult;
import com.Kotori.domain.Employee;
import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;
import com.Kotori.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /***
     * @brief  Head to employee.jsp
     * @params null
     * @return View location of employee.jsp
     */
    @RequestMapping("/employee.action")
    @RequiresPermissions("employee:index")
    public String employee() {
        return "/WEB-INF/view/employee.jsp";
    }

    /***
     * @brief  Obtain all employees in database
     * @params null
     * @return Divide all results into pages and return one page
     */
    @RequestMapping("/getEmployeePage.action")
    @ResponseBody
    public PageListResult getEmployeePage(QueryViewObject queryViewObject) {
        PageListResult page = employeeService.getEmployeePage(queryViewObject);
        return page;
    }

    /***
     * @brief  Add a new employee to database
     * @params employee
     * @return Callback info for inserting the new employee
     */
    @RequestMapping("/saveEmployee.action")
    @RequiresPermissions("employee:add")
    @ResponseBody
    public AjaxResult saveEmployee(Employee employee) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            // Always on duty when adding a new employee
            employee.setState(true);

            // Encrypt password by md5. Salt is username.
            Md5Hash md5Hash = new Md5Hash(employee.getPassword(), employee.getUsername(), 2);
            employee.setPassword(md5Hash.toString());

            employeeService.saveEmployee(employee);
            ajaxResult.setMsg("保存成功");
            ajaxResult.setResult(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMsg("保存失败");
            ajaxResult.setResult(false);
        }
        return ajaxResult;
    }

    /***
     * @brief  Update employee info
     * @params employee
     * @return Callback info for Updating the employee
     */
    @RequestMapping("/updateEmployee.action")
    @RequiresPermissions("employee:edit")
    @ResponseBody
    public AjaxResult updateEmployee(Employee employee) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            employeeService.updateEmployee(employee);
            ajaxResult.setMsg("更新成功");
            ajaxResult.setResult(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMsg("更新失败");
            ajaxResult.setResult(false);
        }
        return ajaxResult;
    }

    /***
     * @brief  Switch employee state
     * @params id
     * @return Callback info for Updating the state
     */
    @RequestMapping("/updateState.action")
    @RequiresPermissions("employee:delete")
    @ResponseBody
    public AjaxResult updateState(Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            employeeService.updateState(id);
            ajaxResult.setMsg("更新成功");
            ajaxResult.setResult(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMsg("更新失败");
            ajaxResult.setResult(false);
        }
        return ajaxResult;
    }

    /***
     * @brief  vague query to retrieve employees whose names are similar to keyword
     * @params queryViewObject
     * @return List of employees meet the requirement
     */
    @RequestMapping("/vagueQueryEmployee.action")
    @ResponseBody
    public PageListResult vagueQueryEmployee(QueryViewObject queryViewObject) {
        System.out.println(queryViewObject);
        PageListResult page = employeeService.vagueQueryEmployee(queryViewObject);
        return page;
    }

    /***
     *
     * @brief  Handle shiro exception that the current subject has no permission to visit resource.
     *         A json or an http response will be returned
     * @params handlerMethod response
     * @throws IOException
     */
    @ExceptionHandler (AuthorizationException.class)
    public void handleShiroException(HandlerMethod handlerMethod, HttpServletResponse response) throws IOException {
        ResponseBody methodAnnotation = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (null != methodAnnotation) {
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.setMsg("没有权限访问");
            ajaxResult.setResult(false);
            String jsonString = new ObjectMapper().writeValueAsString(ajaxResult);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonString);
        } else {
            response.sendRedirect("illegalAccess.jsp");
        }
    }

    /***
     * @brief  Create Excel workbook and render the workbook to store employee Info
     * @params response
     * @throws IOException
     */
    @RequestMapping("/importExcel.action")
    @ResponseBody
    public void importExcel(HttpServletResponse response) throws IOException {
        List<Employee> list = employeeService.getEmployeeList();

        // Step 1: Create excel by Apache POI
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("员工数据");

        // Step 2: Set rows in the sheet
        HSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("编号");
        headerRow.createCell(1).setCellValue("用户名");
        headerRow.createCell(2).setCellValue("入职日期");
        headerRow.createCell(3).setCellValue("电话");
        headerRow.createCell(4).setCellValue("邮件");

        for (int i = 0; i < list.size(); i++) {
            Employee employee = list.get(i);
            HSSFRow currentRow = sheet.createRow(i + 1);
            currentRow.createCell(0).setCellValue(employee.getId());
            currentRow.createCell(1).setCellValue(employee.getUsername());

            if (null == employee.getInputtime()) {
                currentRow.createCell(2).setCellValue("");
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String format = simpleDateFormat.format(employee.getInputtime());
                currentRow.createCell(2).setCellValue(format);
            }

            if (null == employee.getTel()) {
                currentRow.createCell(3).setCellValue("");
            } else {
                currentRow.createCell(3).setCellValue(employee.getTel());
            }

            if (null == employee.getEmail()) {
                currentRow.createCell(4).setCellValue("");
            } else {
                currentRow.createCell(4).setCellValue(employee.getEmail());
            }
        }

        // Step 3: Response to the request
        // Convert filename consists of UTF-8 characters into ios8859-1
        String filename = new String("员工数据.xls".getBytes("UTF-8"), "iso8859-1");
        response.setHeader("content-Disposition","attachment;filename=" + filename);
        wb.write(response.getOutputStream());
    }

    /***
     * @brief  Download excel template from server
     * @params request, response
     * @return null
     */
    @RequestMapping("/downloadTemplate.action")
    @ResponseBody
    public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) {
        FileInputStream in = null;
        try {
            String filename = new String("EmployeeTemplate.xls");
            response.setHeader("content-Disposition","attachment;filename=" + filename);

            String realPath = request.getSession().getServletContext().getRealPath("/static/EmployeeTemplate.xls");

            in = new FileInputStream(realPath);
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * @brief  Upload excel and conserve all items in database
     * @params excel
     * @return AjaxResult indicating success or failure
     */
    @RequestMapping("uploadExcel.action")
    @ResponseBody
    public AjaxResult uploadExcel(MultipartFile excel){
        AjaxResult ajaxResult =new AjaxResult();
        try {
            HSSFWorkbook wb = new HSSFWorkbook(excel.getInputStream());
            HSSFSheet sheet = wb.getSheetAt(0);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = 1; i <= lastRowNum; i++) {
                HSSFRow row = sheet.getRow(i);

                // No need to set id because id is naturally increasing in database
                String username = getValue(row.getCell(1));
                String inputtime = getValue(row.getCell(2));
                String tel = getValue(row.getCell(3));
                String email = getValue(row.getCell(4));

                Date date = null;
                if (null != inputtime) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    date = simpleDateFormat.parse(inputtime);
                }

                Employee employee = new Employee();
                employee.setUsername(username);
                employee.setInputtime(date);
                employee.setTel(tel);
                employee.setEmail(email);

                employeeService.saveEmployee(employee);
            }

            ajaxResult.setMsg("上传成功");
            ajaxResult.setResult(true);

        } catch (Exception e) {
            ajaxResult.setMsg("上传失败");
            ajaxResult.setResult(false);
            e.printStackTrace();
        }
        return ajaxResult;
    }

    /***
     * @brief  Force to read cell and obtain cell value in String format
     * @params cell
     * @return cell value in String
     */
    private String getValue(Cell cell){
        if (null == cell) {
            return null;
        }
        cell.setCellType(CellType.STRING);
        return cell.getRichStringCellValue().getString();
    }
}

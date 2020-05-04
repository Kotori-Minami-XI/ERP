package com.Kotori.web;

import com.Kotori.domain.AjaxResult;
import com.Kotori.domain.Employee;
import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;
import com.Kotori.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
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
    @RequestMapping("/getEmployeeList.action")
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
}

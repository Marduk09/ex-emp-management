package jp.co.sample.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

/**
 * 従業員情報に対する処理を制御するコントローラー.
 * 
 * @author takara.miyazaki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	/**
	 * UpdateEmployeeFormをインスタンス化
	 * @return UpdateEmployeeForm
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpUpdateEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	
	/**
	 * 従業員一覧を出力する.
	 * 
	 * @param model リクエストスコープ
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		
		return "employee/list";
	}
	
	/**
	 * 従業員の詳細情報表示.
	 * 
	 * @param id 従業員ID
	 * @param model リクエストスコープ
	 * @return 従業員の詳細情報画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		
		return "employee/detail";
	}
	
	/**
	 * 従業員データの編集画面表示.
	 * 
	 * @param form リクエストパラメータ
	 * @param id 従業員ID
	 * @param model リクエストスコープ
	 * @return 編集画面
	 */
	@RequestMapping("/showEdit")
	public String showEdit(UpdateEmployeeForm form, String id, Model model) {
		

		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		BeanUtils.copyProperties(employee, form);
		form.setHireDate(new SimpleDateFormat("yyyy-MM-dd").format(employee.getHireDate()));
		form.setSalary(employee.getSalary().toString());
		form.setDependentsCount(employee.getDependentsCount().toString());
		
		return "employee/edit";
	}
	
	/**
	 * 従業員の扶養人数を更新する
	 * @param form リクエストパラメータ
	 * @param model リクエストスコープ
	 * @return 従業員一覧画面へのリダイレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		
		if(result.hasErrors()) {
			return "employee/edit";
			//return showEdit(form, result, form.getId(), model);
		}
		
		Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
		BeanUtils.copyProperties(form, employee);
		employee.setHireDate(Date.valueOf(form.getHireDate()));
		employee.setSalary(Integer.parseInt(form.getSalary()));
		employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));

		employeeService.update(employee);
		model.addAttribute("employee", employee);
		
		return "employee/detail";
	}
}

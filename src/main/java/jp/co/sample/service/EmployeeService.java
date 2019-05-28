package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 *  従業員DBを操作するサービス.
 *  
 * @author takara.miyazaki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * 従業員情報を全件取得する.
	 * 
	 * @return 全従業員情報を格納したリスト
	 */
	public List<Employee> showList(){
		return employeeRepository.findAll();
	}
	
	/**
	 * 従業員情報を取得する.
	 * 
	 * @param id 従業員ID
	 * @return 取得した従業員情報
	 */
	public Employee showDetail(Integer id) {
		return employeeRepository.load(id);
	}
}

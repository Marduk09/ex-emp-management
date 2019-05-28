package jp.co.sample.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

/**
 * 管理者情報に対する処理を制御するコントローラー.
 * 
 * @author takara.miyazaki
 *
 */
@Controller
@RequestMapping("/")
public class AdministratorController {
	
	@Autowired
	private AdministratorService administratorService;
	
	/**
	 * InsertAdministratorFormオブジェクトをリクエストスコープに格納.
	 * 
	 * @return InsertAdministratorFormのインスタンス
	 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertAdministratorForm() {
		return new InsertAdministratorForm();
	}
	
	/**
	 * LoginFormオブジェクトをリクエストスコープに格納.
	 * 
	 * @return LoginFormのインスタンス
	 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	/**
	 * administrator/insert.htmlにフォワード.
	 * 
	 * @return フォワード先"administrator/insert"
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * 管理者情報を登録する.
	 * 
	 * @param form　登録する管理者情報を格納したフォーム
	 * @return　"/"へのリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(InsertAdministratorForm form) {
		
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		
		administratorService.insert(administrator);
		
		return "redirect:/";
	}
	
	/**
	 * administrator/loginへフォワード
	 * 
	 * @return　フォワード先"administrator/login"
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}

}

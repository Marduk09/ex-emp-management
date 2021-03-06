package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@Autowired
	private HttpSession session;
	
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
	 * 管理者情報登録画面にフォワード.
	 * 
	 * @return 管理者情報登録画面
	 */
	@RequestMapping("/toInsert")
	public String toInsert() {
		return "administrator/insert";
	}
	
	/**
	 * 管理者情報を登録する.
	 * 
	 * @param form 登録する管理者情報を格納したフォーム
	 * @return 管理者ログイン画面へのリダイレクト
	 */
	@RequestMapping("/insert")
	public String insert(
			@Validated InsertAdministratorForm form
			,BindingResult result
			,RedirectAttributes redirectAttributes
			) {
		
		if(result.hasErrors()) {
			return toInsert();
		}
		
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		
		administratorService.insert(administrator);
		
		return "redirect:/";
	}
	
	/**
	 * 管理者ログイン画面へフォワード
	 * 
	 * @return 管理者ログイン画面
	 */
	@RequestMapping("/")
	public String toLogin() {
		return "administrator/login";
	}
	
	/**
	 * ログイン処理をする.
	 * @param form ログインする管理者情報
	 * @param model リクエストスコープ
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/login")
	public String login(LoginForm form, BindingResult result) {
		Administrator administrator
				= administratorService.login(form.getMailAddress(), form.getPassword());
		
		if(administrator == null) {
			result.rejectValue("mailAddress", null , "メールアドレスまたはパスワードが不正です。");
			return "administrator/login";
		}else {
			session.setAttribute("administratorName", administrator.getName());
		}
		
		return "forward:/employee/showList";
		
	}
	
	/**
	 * session情報を削除してログアウトする.
	 * 
	 * @return ログイン画面にリダイレクト
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
	}

}

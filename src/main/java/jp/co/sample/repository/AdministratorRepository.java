package jp.co.sample.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Administrator;

/**
 * administratorsテーブルを操作するレポジトリ.
 * 
 * @author takara.miyazaki
 *
 */
@Repository
public class AdministratorRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<Administrator> ADMINISTRATOR_ROW_MAPPER = (rs, i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};

	/**
	 * 渡された管理者情報をDBに登録する.
	 * 
	 * @param administrator 管理者情報
	 */
	public void insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);

		String sql = "INSERT INTO administrators (name, mail_address, password) "
						+ "VALUES (:name, :mailAddress, :password);";
		template.update(sql, param);
	}
	
	/**
	 * メールアドレスとパスワードから管理者情報を取得する.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password　パスワード
	 * @return　取得した管理者情報。存在しない場合はnullを返す
	 */
	public Administrator findByMailAddressAndPassword(
			String mailAddress,
			String password) {
		String sql = "SELECT id, name, mail_address, password FROM administrators "
						+ "WHERE mail_adress=:mailAddress AND password=:password;";
		
		SqlParameterSource param 
				= new MapSqlParameterSource().addValue(mailAddress, mailAddress)
											 .addValue(password, password);
		
		try {
			Administrator administrator = template.queryForObject(sql, param, ADMINISTRATOR_ROW_MAPPER);
			return administrator;
		} catch (DataAccessException e) {
			System.out.println("データが存在しません");
			return null;
		}
		
	}

}

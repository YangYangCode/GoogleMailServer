package javaweb.service;

import javaweb.exception.CertException;
import javaweb.exception.PasswordInvalidException;
import javaweb.exception.UserNotFoundException;
import javaweb.model.dto.UserCert;
import javaweb.model.entity.User;
import javaweb.repository.UserDaoImpl;
import javaweb.utils.Hash;
import javaweb.repository.UserDao;

// 憑證服務
public class CertService {
	private UserDao userDao = new UserDaoImpl();
	
	// 登入成功後可以取得憑證
	public UserCert getCert(String username, String password) throws CertException{
		// 1.是否有此人
		User user = userDao.getUser(username);
		if (user == null) {
			throw new UserNotFoundException();
		}
		
		// 2.對比密碼
		String passwordHash = Hash.getHash(password, user.getSalt());
		if(!passwordHash.equals(user.getPasswordHash())) {
			throw new PasswordInvalidException();
		}
		
		// 3.簽發憑證
		UserCert userCert = new UserCert(user.getUserId(), user.getUsername(), user.getRole());
		return userCert;
	}
}

package javaweb.service;

import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.exceptions.PasswordExpiredException;

import javaweb.exception.UserNotFoundException;
import javaweb.model.dto.UserDto;
import javaweb.model.entity.User;
import javaweb.repository.UserDao;
import javaweb.repository.UserDaoImpl;
import javaweb.utils.Hash;

//UserService 是給 UserServlet(Controller) 使用
public class UserService {

	private UserDao userDao = new UserDaoImpl();

	// 所有使用者
		public List<UserDto> findAll() {
			List<UserDto> userDtos = new ArrayList<>();
			// 將 User 轉 UserDto
			// 向 userDao 索取 List<User> 集合
			List<User> users = userDao.findAllUsers();
			for(User user : users) {
				// 一個一個將 User 轉成 UserDto 並放在 userDtos 集合中
				UserDto userDto = new UserDto();
				userDto.setUserId(user.getUserId());
				userDto.setUsername(user.getUsername());
				userDto.setEmail(user.getEmail());
				userDto.setActive(user.getActive());
				userDto.setRole(user.getRole());
				
				userDtos.add(userDto);
			}
			
			return userDtos;
		}

	// 新增使用者
	public void appendUser(String username, String password, String email, String role) {
		String salt = Hash.getSalt();
		String passwordHash = Hash.getHash(password, salt); // 得到 Hash
		Boolean action = false; // email 尚未驗證成功
		// 根據上列參數封裝到 User 物件中給
		User user = new User();
		user.setUsername(username);
		user.setPasswordHash(passwordHash);
		user.setSalt(salt);
		user.setEmail(email);
		user.setActive(action);
		user.setRole(role);

		// 存入(新增使用者): userDao.addUser(user)
		userDao.addUser(user);
	}
	
	// 刪除使用者
	public void deleteUser(String userId) {
		userDao.deleteUser(Integer.parseInt(userId));
	}
	
	// 取得特定使用者
	public UserDto getUser(String username) {
		User user = userDao.getUser(username);
		
		if(user == null) {
			return null;
		}

		// 一個一個將 User 轉成UserDto
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setActive(user.getActive());
		userDto.setRole(user.getRole());
		return userDto;
	}
	
	
	public void updateUser(String userId, String active, String role) {
		if(!active.isEmpty()) {
			userDao.updateUserActive(Integer.parseInt(userId), Boolean.parseBoolean(active));
		}
		if(!role.isEmpty()) {
			userDao.updateUserRole(Integer.parseInt(userId), role);
		}
	}

	// 變更密碼
	public void updatePassword(Integer userId, String username, String oldPassword, String newPassword) 
							throws UserNotFoundException, PasswordExpiredException{
		System.out.println(userId+", "+username+", "+oldPassword+", "+newPassword);
		User user = userDao.getUser(username);
		if(user == null) {
			throw new UserNotFoundException();
		}
		
		// 比對修改前的 password 是否正確
		String oldPasswordHash = Hash.getHash(oldPassword, user.getSalt());
		System.out.println(oldPasswordHash+","+user.getSalt());
		if (!oldPasswordHash.equals(user.getPasswordHash())) {
			throw new PasswordExpiredException("原密碼入錯誤");
		}
		
		// 產生新密碼Hash(使用原本salt)
		String newPasswordHash = Hash.getHash(newPassword, user.getSalt());
		
		// 密碼修改
		userDao.updatePasswordHash(userId, newPasswordHash);
		
	}

}

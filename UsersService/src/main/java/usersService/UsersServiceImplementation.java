package usersService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import serviceLibrary.dto.usersService.UserDto;
import serviceLibrary.services.usersService.UsersService;

@RestController
public class UsersServiceImplementation implements UsersService{
	
	@Autowired
	private UserRepository repo;
	
	public UserDto modelToDto(UserModel model) {
		return new UserDto(model.getEmail(), model.getPassword(), model.getRole());
	}

	@Override
	public ResponseEntity<?> getAllUsers() {
		List<UserModel> models = repo.findAll();
		List<UserDto> dtos = new ArrayList<UserDto>();
		if(!models.isEmpty()) {
			for(UserModel m : models) {
				dtos.add(modelToDto(m));
			}
			return ResponseEntity.ok(dtos);
		}
		return ResponseEntity.status(404).body("Currently no users in database");
			
		
	}

	@Override
	public ResponseEntity<?> getUserByEmail(String email) {
		UserModel model = repo.findByEmailIgnoreCase(email);
		if(model != null) {
			return ResponseEntity.ok(modelToDto(model));
		}
		return ResponseEntity.status(404)
				.body("User with an email: " + email + " not found!");
	}

	@Override
	public ResponseEntity<?> createUser(UserDto body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> updateUser(UserDto body) {
		// TODO Auto-generated method stub
		return null;
	}

}

package pe.com.bibliAI.BibliAI.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bibliAI.BibliAI.entity.User;
import pe.com.bibliAI.BibliAI.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		user.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		return userRepository.save(user);
	}

	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public void deleteUserById(Long id) {
		if (userRepository.existsById(id)) {
			userRepository.deleteById(id);
		} else {
			throw new RuntimeException("No se puede eliminar. Usuario no encontrado con ID: " + id);
		}
	}

	public User updateUser(Long id, User updatedUser) {
		userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

		return userRepository.save(updatedUser);
	}

	 public User loginUser(String username, String password) {
	        User user = userRepository.findByUsername(username)
	                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

	        if (user.getPassword().equals(password)) {
	            return user;
	        } else {
	            throw new RuntimeException("Contraseña incorrecta");
	        }
	    }
}
package project.smartcity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.smartcity.model.Driver;
import project.smartcity.repository.DriverRepository;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	private DriverRepository driverRepository;
	private PasswordEncoder bcryptEncoder;

	@Autowired
	public void setUserDao(DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	@Autowired
	public void setBcryptEncoder(PasswordEncoder bcryptEncoder) {
		this.bcryptEncoder = bcryptEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Driver driver = driverRepository.findByUsername(username);
		if (driver == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(driver.getUsername(), driver.getPass(),
				new ArrayList<>());
	}

	public Driver save(Driver driver) throws Exception {
		driver.setPass(bcryptEncoder.encode(driver.getPass()));
		return driverRepository.save(new Driver(driver.getUsername(),driver.getPass(), driver.getEmail()));
	}
}
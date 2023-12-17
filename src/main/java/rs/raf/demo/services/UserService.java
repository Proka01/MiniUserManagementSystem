package rs.raf.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.Permission;
import rs.raf.demo.model.UpdateRequestDTO;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User myUser = this.findByEmail(email);
        if(myUser == null) {
            throw new UsernameNotFoundException("Email "+email+" not found");
        }

        Collection<Permission> permissions = new ArrayList<>();
        if (myUser.getPermissions() != null){
            for(String s : myUser.getPermissions().split(";")) {
                permissions.add(new Permission(s));
            }
        }

        return new org.springframework.security.core.userdetails.User(myUser.getEmail(), myUser.getPassword(),
                permissions);
    }

    public User create(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    public User findByEmail(String email) {
        return this.userRepository.findUserByEmail(email);
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User update(UpdateRequestDTO userDTO) {
        User userToUpdate = this.userRepository.findUserByEmail(userDTO.getEmail());
        userToUpdate.setFirstName(userDTO.getFirstName());
        userToUpdate.setLastName(userDTO.getLastName());
        userToUpdate.setEmail(userDTO.getEmail());
        userToUpdate.setPermissions(userDTO.getPermissions());
        return this.userRepository.save(userToUpdate);
    }

    public void delete(Long id) {
        this.userRepository.deleteUserByUserId(id);
    }


}

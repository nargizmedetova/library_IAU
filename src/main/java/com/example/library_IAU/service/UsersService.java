package com.example.library_IAU.service;


import com.example.library_IAU.model.UsersModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsersService {
    List<UsersModel> getAllUsers();

    UsersModel saveUser(UsersModel usersModel);

    UsersModel getUserById(Long id);

    UsersModel updateUser(UsersModel usersModel);

    void deleteUserById(Long id);
    UsersModel registerUser(String email, String password);
    UsersModel authenticate(String email, String password);
    Optional<UsersModel> getUsersModelByEmail(String email);

   /* private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UsersModel registerUser(String email, String password){
        if (email == null || password == null) {
            return null;
        } else {
            UsersModel usersModel = new UsersModel();
            usersModel.setEmail(email);
            usersModel.setPassword(password);
            return usersRepository.save(usersModel);

        }
    }
    public UsersModel authenticate(String email, String password){
        return usersRepository.findByEmailAndPassword(email, password).orElse(null);
    }*/


}

package com.example.library_IAU.service;

import com.example.library_IAU.model.UsersModel;
import com.example.library_IAU.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UsersServicelmpl implements UsersService{

    private final UsersRepository usersRepository;

    public UsersServicelmpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public Optional<UsersModel> getUsersModelByEmail(String email){
        if (email == null){
            return null;
        }
        else {
            return usersRepository.getUsersModelByEmail(email);
        }
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
    }

    @Override
    public List<UsersModel> getAllUsers() {
        return usersRepository.findAll();
    }


    @Override
    public UsersModel saveUser(UsersModel usersModel) {
        return usersRepository.save(usersModel);
    }

    @Override
    public UsersModel getUserById(Long id) {
        return usersRepository.getReferenceById(id);
    }

    @Override
    public UsersModel updateUser(UsersModel usersModel) {
        return null;
    }


    @Override
    public void deleteUserById(Long id) {
        usersRepository.deleteById(id);
    }
}

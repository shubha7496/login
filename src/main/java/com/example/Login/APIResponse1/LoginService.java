package com.example.Login.APIResponse1;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Login.Dto.LoginRequestDTO;
import com.example.Login.Dto.SignUpRequestDTO;



@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    public APIResponse signUp(SignUpRequestDTO signUpRequestDTO) {
        APIResponse apiResponse = new APIResponse();

        // validation

        // dto to entity
        User userEntity = new User();
        userEntity.setName(signUpRequestDTO.getName());
        userEntity.setEmailId(signUpRequestDTO.getEmailId());
        userEntity.setActive(Boolean.TRUE);
        userEntity.setGender(signUpRequestDTO.getGender());
        userEntity.setPhoneNumber(signUpRequestDTO.getPhoneNumber());
        userEntity.setPassword(signUpRequestDTO.getPassword());

        // store entity
        userEntity = userRepository.save(userEntity);

       //  generate jwt
        String token = jwtUtils.generateJwt(userEntity);

        Map<String , Object> data = new HashMap<>();
        data.put("accessToken", token);
        System.out.println(token);
        apiResponse.setData(data);
//
//        // return
        return apiResponse;
    }

    public APIResponse login(LoginRequestDTO loginRequestDTO) {

        APIResponse apiResponse = new APIResponse();

        // validation

        // verify user exist with given email and password
        User user = userRepository.findOneByEmailIdIgnoreCaseAndPassword(loginRequestDTO.getEmailId(), loginRequestDTO.getPassword());

        // response
        if(user == null){
            apiResponse.setData("User login fail");
        }
        else if(user.getEmailId().equals(loginRequestDTO.getEmailId())) {
            apiResponse.setData("User login in");
            System.out.println(loginRequestDTO.getEmailId()+""+loginRequestDTO.getPassword()+""+user.getEmailId());

        }
     

      //   generate jwt
//        String token = jwtUtils.generateJwt(user);
//
//        Map<String , Object> data = new HashMap<>();
//        data.put("accessToken", token);
//
//        apiResponse.setData(data);

        return apiResponse;
    }
}
package in.arjun.service;

import in.arjun.repository.CounsellorRepository;
import in.arjun.model.request.RegisterCounsellorRequest;
import in.arjun.model.entity.Counsellor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CounsellorServiceImpl implements CounsellorService {

    @Autowired
    private CounsellorRepository counsellorRepository;


    @Override
    public String createCounsellor(RegisterCounsellorRequest registerCounsellorRequest) {
        Counsellor counsellor = Counsellor.builder()
                .name(registerCounsellorRequest.getName())
                .email(registerCounsellorRequest.getEmail())
                .password(registerCounsellorRequest.getPassword())
                .phoneNo(registerCounsellorRequest.getPhoneNo())
                .build();
        counsellorRepository.save(counsellor);
        return "success";
    }

    @Override
    public String loginCounsellor(String email,String password) {
        Optional<Counsellor> counsellor = counsellorRepository.findByEmail(email);
        if(counsellor.isEmpty()) {
            return "this mail id is doesn't exists please register";
        }
        String savedPassword = counsellor.get().getPassword();
        if (savedPassword.equals(password)) {
            return "login successfully";
        } else {
            return "Incorrect password";
        }
    }

}

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

    @Autowired
    private EmailService emailService;


    @Override
    public Counsellor createCounsellor(RegisterCounsellorRequest registerCounsellorRequest) {
        Counsellor counsellor = Counsellor.builder()
                .name(registerCounsellorRequest.getName())
                .email(registerCounsellorRequest.getEmail())
                .password(registerCounsellorRequest.getPassword())
                .phoneNo(registerCounsellorRequest.getPhoneNo())
                .build();
        counsellorRepository.save(counsellor);

        //Sending email to the registered counsellor
        String subject="Counsellor Registration";
        String body="your registration successfully completed, Welcome!!! to my World";
        String to=counsellor.getEmail();
        emailService.sendEmail(subject,body,to);

        return counsellor;
    }

    @Override
    public  Counsellor loginCounsellor(String email, String password) {
        Optional<Counsellor> optionalCounsellor = counsellorRepository.findByEmailAndPassword(email, password);
        return optionalCounsellor.orElse(null);
    }

    @Override
    public Counsellor getByEmail(String email) {
        return counsellorRepository.findByEmail(email).orElse(null);
    }

}

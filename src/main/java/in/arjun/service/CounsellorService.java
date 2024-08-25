package in.arjun.service;

import in.arjun.model.entity.Counsellor;
import in.arjun.model.request.RegisterCounsellorRequest;

import java.util.Optional;

public interface CounsellorService {

    Optional<Counsellor> createCounsellor(RegisterCounsellorRequest registerCounsellorRequest);

    Optional<Counsellor> loginCounsellor(String email,String password);

   Optional<Counsellor> getByEmail(String email);


}

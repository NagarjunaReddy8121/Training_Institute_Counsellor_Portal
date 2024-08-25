package in.arjun.service;

import in.arjun.model.entity.Counsellor;
import in.arjun.model.request.RegisterCounsellorRequest;

import java.util.Optional;

public interface CounsellorService {

    Counsellor createCounsellor(RegisterCounsellorRequest registerCounsellorRequest);

    Counsellor loginCounsellor(String email,String password);

    Counsellor getByEmail(String email);


}

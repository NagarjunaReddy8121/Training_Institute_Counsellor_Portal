package in.arjun.service;

import in.arjun.model.request.RegisterCounsellorRequest;

public interface CounsellorService {

    String createCounsellor(RegisterCounsellorRequest registerCounsellorRequest);

    String loginCounsellor(String email,String password);


}

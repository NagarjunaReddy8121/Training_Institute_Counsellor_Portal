package in.arjun.controller;

import in.arjun.model.request.RegisterCounsellorRequest;
import in.arjun.service.CounsellorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/counsellor")
public class CounsellorController {

    @Autowired
    private CounsellorService counsellorService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCounsellor(@RequestBody RegisterCounsellorRequest counsellors){
        counsellorService.createCounsellor(counsellors);
        return new ResponseEntity<>("registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginCounsellor(@RequestParam String email,
                                                  @RequestParam String password){
        String result = counsellorService.loginCounsellor(email, password);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}

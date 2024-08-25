package in.arjun.controller;

import in.arjun.model.entity.Counsellor;
import in.arjun.model.request.RegisterCounsellorRequest;
import in.arjun.service.CounsellorService;
import in.arjun.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CounsellorController {

    @Autowired
    private CounsellorService counsellorService;

    @Autowired
    private EnquiryService enquiryService;

    @GetMapping("/")
     public String index(Model model){
        RegisterCounsellorRequest request=new RegisterCounsellorRequest();
        model.addAttribute("counsellor",request);
         return "index";
     }

     @PostMapping("/login")
     public String login(Counsellor counsellor, HttpServletRequest req, Model model) {
         Optional<Counsellor> optionalCounsellor = counsellorService.loginCounsellor(counsellor.getEmail(), counsellor.getPassword());
         if (optionalCounsellor.isEmpty()){
             model.addAttribute("emsg","Invalid credentials");
             return "index";
         } else {

             HttpSession session = req.getSession(true);
             session.setAttribute("email",optionalCounsellor.get().getEmail());

             return "redirect:/dashboard";
         }

     }

     @GetMapping("/register")
     public String registerPage(Model model){
        Counsellor counsellor=new Counsellor();
        model.addAttribute("counsellor",counsellor);
        return "register";
     }

     @PostMapping("/register")
     public String handleRegistrationPage(@ModelAttribute("counsellor") RegisterCounsellorRequest request, Model model){

         Optional<Counsellor> byEmail = counsellorService.getByEmail(request.getEmail());
         if(byEmail.isPresent()){
             model.addAttribute("emsg","Duplicate email...");
             return "register";
         }
         Optional<Counsellor> optionalCounsellor = counsellorService.createCounsellor(request);
         if(optionalCounsellor.isPresent()){
             model.addAttribute("smsg","Registration successfull....");
         } else {
             model.addAttribute("emsg","Registration failed...");
         }
         return "register";
     }

     @GetMapping("/logout")
     public String logout(HttpServletRequest req){
         HttpSession session = req.getSession(false);
         session.invalidate();
         return "redirect:/";
     }
}

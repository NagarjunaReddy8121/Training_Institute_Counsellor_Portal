package in.arjun.controller;

import in.arjun.model.entity.Counsellor;
import in.arjun.model.request.LoginCounsellorRequest;
import in.arjun.model.request.RegisterCounsellorRequest;
import in.arjun.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CounsellorController {

    @Autowired
    private CounsellorService counsellorService;

    @GetMapping("/")
     public String index(Model model, HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if (session != null) {
            String emailBySession=(String) session.getAttribute("email");
            if (emailBySession != null) {
                return "redirect:/dashboard";
            }
        }
        RegisterCounsellorRequest request=new RegisterCounsellorRequest();
        model.addAttribute("counsellor",request);
         return "index";
     }

    @PostMapping("/login")
    public String login(@ModelAttribute("counsellor") LoginCounsellorRequest request, HttpServletRequest req, Model model) {
        Counsellor counsellor = counsellorService.loginCounsellor(request.getEmail(), request.getPassword());
        if (counsellor == null) {
            model.addAttribute("emsg", "Invalid credentials");
            return "index";
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("email", counsellor.getEmail());

        return "redirect:/dashboard";

    }

     @GetMapping("/register")
     public String registerPage(Model model){
        Counsellor counsellor=new Counsellor();
        model.addAttribute("counsellor",counsellor);
        return "register";
     }

     @PostMapping("/register")
     public String handleRegistrationPage(@ModelAttribute("counsellor") RegisterCounsellorRequest request, Model model){
         Counsellor counsellor = counsellorService.getByEmail(request.getEmail());
         if(counsellor != null){
             model.addAttribute("emsg","Duplicate email...");
             return "register";
         }
             counsellorService.createCounsellor(request);
             model.addAttribute("smsg","Registration successfull....");
         return "register";
     }

     @GetMapping("/logout")
     public String logout(HttpServletRequest req){
         HttpSession session = req.getSession(false);
         session.invalidate();
         return "redirect:/";
     }
}

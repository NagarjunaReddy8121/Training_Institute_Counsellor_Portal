package in.arjun.controller;

import in.arjun.model.entity.Enquiry;
import in.arjun.model.request.AddEnquiryRequest;
import in.arjun.model.request.ViewEnquiryFilterRequest;
import in.arjun.model.response.DashboardResponse;
import in.arjun.model.response.EnquiryResponse;
import in.arjun.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    @GetMapping("/enquiry")
     public String addEnquiryPage(Model model){
         Enquiry enquiry=new Enquiry();
         model.addAttribute("enquiry",enquiry);
         return "enquiry";
     }

     @PostMapping("/addEnquiry")
     public String handleAddEnquiry(@ModelAttribute("enquiry") AddEnquiryRequest addEnquiryRequest, HttpServletRequest request, Model model){
         HttpSession session = request.getSession(false);
        String emailBySession=(String) session.getAttribute("email");
         boolean isSaved = enquiryService.createEnquiry(addEnquiryRequest,emailBySession);
         if (isSaved){
             model.addAttribute("smsg","Enquiry added..");
             return "redirect:/view-enquiries";
         } else {
             model.addAttribute("emsg","failed to add Enquiry..");
             return "enquiry";
         }
     }

     @GetMapping("/dashboard")
     public String displayDashboard(HttpServletRequest req,Model model){
         HttpSession session = req.getSession(false);
        String emailBySession=(String) session.getAttribute("email");

         DashboardResponse response = enquiryService.getCounsellorEnquiryData(emailBySession);
         model.addAttribute("dashboardInfo",response);
         return "dashboard";
     }

     @GetMapping("/view-enquiries")
     public String getAllEnquiriesByCounsellor( HttpServletRequest req,Model model){
         HttpSession session = req.getSession(false);
        String emailBySession=(String) session.getAttribute("email");

         List<EnquiryResponse> enqList = enquiryService.findAllEnquiries(emailBySession);
         model.addAttribute("enquiries",enqList);

         ViewEnquiryFilterRequest filterRequest=new ViewEnquiryFilterRequest();
         model.addAttribute("enquiryFilterRequest",filterRequest);

         return "viewEnqsPage";
     }

     @PostMapping("/filter-enquiries")
     public String getFilterEnquiries(@ModelAttribute("enquiryFilterRequest") ViewEnquiryFilterRequest enquiryFilterRequest,HttpServletRequest req,Model model){
         HttpSession session = req.getSession(false);
         String emailBySession=(String) session.getAttribute("email");
         List<EnquiryResponse> enqList = enquiryService.findFilterRequest(enquiryFilterRequest, emailBySession);
         model.addAttribute("enquiries",enqList);
         return "viewEnqsPage";
     }

     @GetMapping("/getEnquiry")
     public String getEnquiryById(@RequestParam("id") Integer id,Model model){
         Enquiry enquiryById = enquiryService.getEnquiryById(id);
         model.addAttribute("enquiry",enquiryById);
         model.addAttribute("isEdit", true);
         return "enquiry";
     }

}

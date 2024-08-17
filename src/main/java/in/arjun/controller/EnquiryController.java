package in.arjun.controller;

import in.arjun.model.request.AddEnquiryRequest;
import in.arjun.model.request.ViewEnquiryFilterRequest;
import in.arjun.model.response.DashboardResponse;
import in.arjun.model.response.EnquiryResponse;
import in.arjun.service.EnquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enquiry")
public class EnquiryController {

    @Autowired
    private EnquiryService enquiryService;

    @PostMapping("/add")
    public ResponseEntity<String> addEnquiry(@RequestBody AddEnquiryRequest addEnquiryRequest){
        String status = enquiryService.createEnquiry(addEnquiryRequest);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<EnquiryResponse>> getAllEnquiries(@RequestParam String counsellorEmail){
        List<EnquiryResponse> enquiryList = enquiryService.findAllEnquiries(counsellorEmail);
        return new ResponseEntity<>(enquiryList,HttpStatus.OK);
    }

    @GetMapping("/dashboard")
    public DashboardResponse getCounsellorEnquiriesData(@RequestParam String counsellorEmail){
      return enquiryService.getCounsellorEnquiryData(counsellorEmail);
    }

    @PostMapping("/filter")
    public ResponseEntity<List<EnquiryResponse>> findFilterData(@RequestBody ViewEnquiryFilterRequest filterRequest){
        List<EnquiryResponse> filterList = enquiryService.findFilterRequest(filterRequest);
        return new ResponseEntity<>(filterList,HttpStatus.OK);
    }

}

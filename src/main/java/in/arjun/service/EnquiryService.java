package in.arjun.service;

import in.arjun.model.request.AddEnquiryRequest;
import in.arjun.model.entity.Enquiry;
import in.arjun.model.request.ViewEnquiryFilterRequest;
import in.arjun.model.response.DashboardResponse;
import in.arjun.model.response.EnquiryResponse;

import java.util.List;
import java.util.Optional;

public interface EnquiryService {

     boolean createEnquiry(AddEnquiryRequest addEnquiryRequest,String email);

    List<EnquiryResponse> findAllEnquiries(String email);

    DashboardResponse getCounsellorEnquiryData(String counsellorEmail);

    List<EnquiryResponse> findFilterRequest(ViewEnquiryFilterRequest filterRequest,String email);

    Enquiry getEnquiryById(Integer id);

}

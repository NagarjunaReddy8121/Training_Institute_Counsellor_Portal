package in.arjun.service;

import in.arjun.model.request.AddEnquiryRequest;
import in.arjun.model.entity.Enquiry;
import in.arjun.model.request.ViewEnquiryFilterRequest;
import in.arjun.model.response.DashboardResponse;
import in.arjun.model.response.EnquiryResponse;

import java.util.List;

public interface EnquiryService {

     String createEnquiry(AddEnquiryRequest addEnquiryRequest);

    List<EnquiryResponse> findAllEnquiries(String counsellorEmail);

    DashboardResponse getCounsellorEnquiryData(String counsellorEmail);

    List<EnquiryResponse> findFilterRequest(ViewEnquiryFilterRequest filterRequest);

}

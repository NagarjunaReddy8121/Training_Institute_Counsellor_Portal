package in.arjun.service;

import in.arjun.enums.ClassMode;
import in.arjun.enums.EnquiryStatus;
import in.arjun.model.request.ViewEnquiryFilterRequest;
import in.arjun.model.response.DashboardResponse;
import in.arjun.model.response.EnquiryResponse;
import in.arjun.repository.CounsellorRepository;
import in.arjun.repository.EnquiryRepository;
import in.arjun.model.request.AddEnquiryRequest;
import in.arjun.model.entity.Counsellor;
import in.arjun.model.entity.Enquiry;
import in.arjun.exception.CounsellorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnquiryServiceImpl implements EnquiryService{

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private CounsellorRepository counsellorRepository;

    @Override
    public String createEnquiry(AddEnquiryRequest addEnquiryRequest) {
        Optional<Counsellor> optionalCounsellor = counsellorRepository.findByEmail(addEnquiryRequest.getCounsellorEmail());
        if (optionalCounsellor.isEmpty()) {
            throw new CounsellorNotFoundException("counsellor not found with this email" + addEnquiryRequest.getCounsellorEmail());
        }
        Enquiry enquiry = Enquiry.builder().
                name(addEnquiryRequest.getName())
                .phoneNo(addEnquiryRequest.getPhoneNo())
                .courseName(addEnquiryRequest.getCourseName())
                .classMode(addEnquiryRequest.getClassMode())
                .status(addEnquiryRequest.getStatus())
                .counsellor(optionalCounsellor.get())
                .build();

        enquiryRepository.save(enquiry);
        return "success";
    }

    @Override
    public List<EnquiryResponse> findAllEnquiries(String counsellorEmail) {
        Optional<Counsellor> optionalCounsellor = counsellorRepository.findByEmail(counsellorEmail);
        if (optionalCounsellor.isEmpty()) {
            throw new CounsellorNotFoundException("counsellor not found with this email" + counsellorEmail);
        }
        Integer counsellorId = optionalCounsellor.get().getId();
        List<Enquiry> enquiryList = enquiryRepository.findAllEnquiriesByCounsellorId(counsellorId);

        // Another Way
        /*Counsellor counsellor = optionalCounsellor.get();
        List<Enquiry> enquiryList = enquiryRepository.findByCounsellor(counsellor);*/

        return enquiryList.stream().map(EnquiryResponse::fromEnquiry).collect(Collectors.toList());
    }

    @Override
    public DashboardResponse getCounsellorEnquiryData(String counsellorEmail) {
        Optional<Counsellor> optionalCounsellor = counsellorRepository.findByEmail(counsellorEmail);
        if (optionalCounsellor.isEmpty()){
            throw new CounsellorNotFoundException("counsellor not found with this email" + counsellorEmail);
        }

        Counsellor counsellor = optionalCounsellor.get();
        Integer counsellorId = counsellor.getId();
        List<Enquiry> totalEnquiries = enquiryRepository.findAllEnquiriesByCounsellorId(counsellorId);
        List<Enquiry> openEnquiries = totalEnquiries.stream()
                .filter(enquiry -> enquiry.getStatus().equals(EnquiryStatus.OPEN)).toList();
        List<Enquiry> enrolledEnquiries = totalEnquiries.stream()
                .filter(enquiry -> enquiry.getStatus().equals(EnquiryStatus.ENROLLED)).toList();
        List<Enquiry> lostEnquiries = totalEnquiries.stream()
                .filter(enquiry -> enquiry.getStatus().equals(EnquiryStatus.LOST)).toList();


        return DashboardResponse.builder()
                .totalEnquiries(totalEnquiries.size())
                .openEnquiries(openEnquiries.size())
                .enrolledEnquiries(enrolledEnquiries.size())
                .lostEnquiries(lostEnquiries.size())
                .build();
    }

    @Override
    public List<EnquiryResponse> findFilterRequest(ViewEnquiryFilterRequest filterRequest) {
        String courseName = filterRequest.getCourse();
        EnquiryStatus status = filterRequest.getStatus();
        ClassMode classMode = filterRequest.getClassMode();
        List<Enquiry> filteredEnquiries = Collections.EMPTY_LIST;

        if (courseName != null && status != null && classMode != null) {
            filteredEnquiries = enquiryRepository.findByClassModeAndCourseNameAndStatus(classMode,courseName,status);
        } else if (courseName !=null && status !=null) {
            filteredEnquiries = enquiryRepository.findByCourseNameAndStatus(courseName, status);
        } else if (courseName !=null && classMode !=null) {
           filteredEnquiries = enquiryRepository.findByClassModeAndCourseName(classMode,courseName);
        } else if (classMode !=null && status !=null) {
            filteredEnquiries = enquiryRepository.findByClassModeAndStatus(classMode,status);
        } else if (courseName !=null) {
            filteredEnquiries = enquiryRepository.findByCourseName(courseName);
        } else if (classMode !=null) {
            filteredEnquiries = enquiryRepository.findByClassMode(classMode);
        }else {
            filteredEnquiries = enquiryRepository.findByStatus(status);
        }
        return filteredEnquiries.stream().map(EnquiryResponse::fromEnquiry).toList();
    }


}

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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnquiryServiceImpl implements EnquiryService {

    @Autowired
    private EnquiryRepository enquiryRepository;

    @Autowired
    private CounsellorRepository counsellorRepository;

    @Override
    public boolean createEnquiry(AddEnquiryRequest addEnquiryRequest, String email) {
        Optional<Counsellor> optionalCounsellor = counsellorRepository.findByEmail(email);
        if (optionalCounsellor.isEmpty()) {
            throw new CounsellorNotFoundException("counsellor not found with this email" + email);
        }

        Enquiry enquiry = Enquiry.builder().
                name(addEnquiryRequest.getName())
                .phoneNo(addEnquiryRequest.getPhoneNo())
                .courseName(addEnquiryRequest.getCourseName())
                .classMode(addEnquiryRequest.getClassMode())
                .status(addEnquiryRequest.getStatus())
                .counsellor(optionalCounsellor.get())
                .build();

        if (addEnquiryRequest.getId() !=null){
            enquiry.setId(addEnquiryRequest.getId());
        }

        Enquiry saved = enquiryRepository.save(enquiry);
        if (saved !=null) {
            return true;
        }
        return false;
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
        if (optionalCounsellor.isEmpty()) {
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
    public List<EnquiryResponse> findFilterRequest(ViewEnquiryFilterRequest filterRequest, String email) {
        Optional<Counsellor> optionalCounsellor = counsellorRepository.findByEmail(email);
        if (optionalCounsellor.isEmpty()) {
            throw new CounsellorNotFoundException("counsellor not found with this email" + email);
        }

        Counsellor counsellor = optionalCounsellor.get();
        String courseName = filterRequest.getCourseName();
        EnquiryStatus status = filterRequest.getStatus();
        ClassMode classMode = filterRequest.getClassMode();
        List<Enquiry> filteredEnquiries = Collections.EMPTY_LIST;

        if (StringUtils.isNotEmpty(courseName) && status != null && classMode != null) {
            filteredEnquiries = enquiryRepository.findByClassModeAndCourseNameAndStatusAndCounsellor(classMode, courseName, status, counsellor);
        } else if (StringUtils.isNotEmpty(courseName) && status != null) {
            filteredEnquiries = enquiryRepository.findByCourseNameAndStatusAndCounsellor(courseName, status, counsellor);
        } else if (StringUtils.isNotEmpty(courseName) && classMode != null) {
            filteredEnquiries = enquiryRepository.findByClassModeAndCourseNameAndCounsellor(classMode, courseName, counsellor);
        } else if (classMode != null && status != null) {
            filteredEnquiries = enquiryRepository.findByClassModeAndStatusAndCounsellor(classMode, status, counsellor);
        } else if (StringUtils.isNotEmpty(courseName)) {
            filteredEnquiries = enquiryRepository.findByCourseNameAndCounsellor(courseName, counsellor);
        } else if (classMode != null) {
            filteredEnquiries = enquiryRepository.findByClassModeAndCounsellor(classMode, counsellor);
        } else {
            filteredEnquiries = enquiryRepository.findByStatusAndCounsellor(status, counsellor);
        }
        return filteredEnquiries.stream().map(EnquiryResponse::fromEnquiry).toList();
    }

    @Override
    public Enquiry getEnquiryById(Integer id) {
        Optional<Enquiry> enquiryById = enquiryRepository.findById(id);
        return enquiryById.orElse(null);
    }


}

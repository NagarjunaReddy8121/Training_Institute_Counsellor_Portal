package in.arjun.repository;

import in.arjun.enums.ClassMode;
import in.arjun.enums.EnquiryStatus;
import in.arjun.model.entity.Counsellor;
import in.arjun.model.entity.Enquiry;
import in.arjun.model.request.ViewEnquiryFilterRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnquiryRepository extends JpaRepository<Enquiry,Integer> {

    @Query(value = "SELECT * FROM enquiry WHERE cid=:cid",nativeQuery = true)
    List<Enquiry> findAllEnquiriesByCounsellorId(Integer cid);

    List<Enquiry> findByCounsellor(Counsellor counsellor);

    List<Enquiry> findByClassModeAndCourseNameAndStatusAndCounsellor(ClassMode classMode, String courseName, EnquiryStatus status,Counsellor counsellor);

    List<Enquiry> findByClassModeAndCourseNameAndCounsellor(ClassMode classMode, String courseName,Counsellor counsellor);

    List<Enquiry> findByClassModeAndStatusAndCounsellor(ClassMode classMode, EnquiryStatus status,Counsellor counsellor);

    List<Enquiry> findByCourseNameAndStatusAndCounsellor(String courseName,EnquiryStatus status,Counsellor counsellor);

    List<Enquiry> findByCourseNameAndCounsellor(String courseName,Counsellor counsellor);

    List<Enquiry> findByClassModeAndCounsellor(ClassMode classMode,Counsellor counsellor);

    List<Enquiry> findByStatusAndCounsellor(EnquiryStatus status,Counsellor counsellor);
}

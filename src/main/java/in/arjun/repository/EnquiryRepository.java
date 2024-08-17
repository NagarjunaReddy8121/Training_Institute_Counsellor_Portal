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

    @Query(value = "SELECT * FROM ENQUIRY WHERE CID=:CID",nativeQuery = true)
    List<Enquiry> findAllEnquiriesByCounsellorId(@Param("CID") Integer cid);

    List<Enquiry> findByCounsellor(Counsellor counsellor);

    List<Enquiry> findByClassModeAndCourseNameAndStatus(ClassMode classMode, String courseName, EnquiryStatus status);

    List<Enquiry> findByClassModeAndCourseName(ClassMode classMode, String courseName);

    List<Enquiry> findByClassModeAndStatus(ClassMode classMode, EnquiryStatus status);

    List<Enquiry> findByCourseNameAndStatus(String courseName,EnquiryStatus status);

    List<Enquiry> findByCourseName(String courseName);

    List<Enquiry> findByClassMode(ClassMode classMode);

    List<Enquiry> findByStatus(EnquiryStatus status);
}

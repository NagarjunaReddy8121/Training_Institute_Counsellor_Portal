package in.arjun.model.response;

import in.arjun.enums.ClassMode;
import in.arjun.enums.EnquiryStatus;
import in.arjun.model.entity.Enquiry;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
public class EnquiryResponse {
    private Integer id;
    private String name;
    private Long phoneNo;
    private String courseName;
    private ClassMode classMode;
    private EnquiryStatus status;

    public static EnquiryResponse fromEnquiry(Enquiry enquiry){
       return EnquiryResponse.builder()
                .id(enquiry.getId())
                .name(enquiry.getName())
                .phoneNo(enquiry.getPhoneNo())
                .courseName(enquiry.getCourseName())
                .classMode(enquiry.getClassMode())
                .status(enquiry.getStatus())
                .build();
    }
}

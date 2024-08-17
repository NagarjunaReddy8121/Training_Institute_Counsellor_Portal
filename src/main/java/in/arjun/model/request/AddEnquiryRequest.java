package in.arjun.model.request;

import in.arjun.enums.ClassMode;
import in.arjun.enums.EnquiryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddEnquiryRequest {

    private String name;
    private String phoneNo;
    private String courseName;
    private ClassMode classMode;
    private EnquiryStatus status;
    private String counsellorEmail;
}

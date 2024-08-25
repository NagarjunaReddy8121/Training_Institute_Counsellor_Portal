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
public class ViewEnquiryFilterRequest {

    private ClassMode classMode;
    private String courseName;
    private EnquiryStatus status;
}

package in.arjun.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponse {

    private Integer totalEnquiries;
    private Integer openEnquiries;
    private Integer enrolledEnquiries;
    private Integer lostEnquiries;
}

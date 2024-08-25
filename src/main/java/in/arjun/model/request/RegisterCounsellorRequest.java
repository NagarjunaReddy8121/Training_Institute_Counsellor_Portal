package in.arjun.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterCounsellorRequest {

    private String name;
    private String email;
    private String password;
    private Long phoneNo;

}

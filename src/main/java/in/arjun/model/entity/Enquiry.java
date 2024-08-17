package in.arjun.model.entity;

import in.arjun.enums.ClassMode;
import in.arjun.enums.EnquiryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String phoneNo;
    private String courseName;
    @Enumerated(EnumType.STRING)
    private ClassMode classMode;
    @Enumerated(EnumType.STRING)
    private EnquiryStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Counsellor counsellor;

}

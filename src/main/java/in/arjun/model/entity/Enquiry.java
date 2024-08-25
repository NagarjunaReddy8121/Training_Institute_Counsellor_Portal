package in.arjun.model.entity;

import in.arjun.enums.ClassMode;
import in.arjun.enums.EnquiryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Long phoneNo;
    private String courseName;
    @Enumerated(EnumType.STRING)
    private ClassMode classMode;
    @Enumerated(EnumType.STRING)
    private EnquiryStatus status;

    @CreatedDate
    @Column(updatable = false)
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Counsellor counsellor;

}

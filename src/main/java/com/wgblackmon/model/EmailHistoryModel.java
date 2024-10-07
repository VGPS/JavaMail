package com.wgblackmon.model;

// import com.esrx.services.common.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;


// database model
// @Entity
// @Table(name = "USER_LOGIN_EMAIL_HISTORY", schema = "eccportal")
// @NaturalIdCache
// @Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailHistoryModel implements Serializable {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(name = "USER_ID")
    private String userid;

    // @Column(name = "EMAIL")
    private String email;

    // @Column(name = "LAST_LOGIN")
    private OffsetDateTime lastlogin;

    // @Column(name = "SUCCESS")
    private Boolean success;
}

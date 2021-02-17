package com.linkare.rec.labmanager.persistence.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@SQLDelete(sql = "UPDATE LabManager SET deleted = true WHERE id = ?")
@Where(clause = "deleted = 0")
@DynamicUpdate
public class LabManager implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String coordinatesRegex = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String picture;

    @Pattern(regexp = coordinatesRegex, message = "Coordinates not valid!")
    private String coordinates;

    private String editUsers;
    private String url;
    private boolean deleted;
}
package com.linkare.rec.labmanager.command;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class LabManagerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String description;
    private String picture;
    private String coordinates;
    private String editUsers;
    private String url;
    private boolean deleted;
}

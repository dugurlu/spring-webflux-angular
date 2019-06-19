package de.denizugurlu.stacirest.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Project {

    @Id
    @ToString.Exclude
    private String id;

    @NotBlank
    @Size(min = 3)
    private String name;
}

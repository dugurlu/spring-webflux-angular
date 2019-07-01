package de.denizugurlu.stacirest.domain;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Rating {

    @ToString.Exclude
    private String id;

    @NotBlank
    private RatingDomain domain;

    @NotBlank
    private String rule;

    @Min(0)
    @Max(100)
    private int fulfillment;
}

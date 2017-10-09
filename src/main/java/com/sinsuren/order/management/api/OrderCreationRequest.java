package com.sinsuren.order.management.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by surender.s on 05/10/17.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderCreationRequest {
    @NotNull
    @NotEmpty
    @Valid
    String orderIdentifier;
    @NotEmpty
    String name;
    @NotEmpty
    String description;
    @NotNull
    String customerId;
    @NotNull
    Double amount;
    @NotEmpty
    String address;
}

package com.jsp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jsp.util.NoSpecialChars;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

/**
 * Entity representing a device.
 * Contains device ID, name, and model.
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dId;

    @NoSpecialChars(message = "The string must not contain escape characters!")
    @JsonProperty("dName")
    private String dName;

    @NoSpecialChars(message = "The string must not contain escape characters!")
    @JsonProperty("dModel")
    private String dModel;
}

package com.jsp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jsp.util.NoSpecialChars;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Entity
@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dId;
    //    @NoSpecialChars(message = "The string must not contain escape characters!")
    @JsonProperty("dName")
    private String dName;
    //    @NoSpecialChars(message = "The string must not contain escape characters!")
    @JsonProperty("dModel")
    private String dModel;
}

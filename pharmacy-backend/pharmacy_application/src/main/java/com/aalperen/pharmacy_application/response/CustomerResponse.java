package com.aalperen.pharmacy_application.response;

import com.aalperen.pharmacy_application.enums.Gender;
import com.aalperen.pharmacy_application.enums.SkinType;
import lombok.Data;

import java.util.List;
@Data
public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private SkinType skinType;
    private Gender gender;
    private String identityNumber;
    private List<OrderResponse> orders;

    public CustomerResponse(Long id, String firstName, String lastName, SkinType skinType, Gender gender, String identityNumber, List<OrderResponse> orderResponses) {
    }

}

package com.api.estudo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoginDTO {

        @NotEmpty
        @Email
        @Size(max = 20)
        private String email;

        @NotEmpty
        @Size(min = 6, max = 40)
        private String senha;
}

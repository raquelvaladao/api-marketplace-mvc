package com.api.estudo.exceptions;

import com.api.estudo.dto.ApiErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({LojaException.class})
    public ResponseEntity<ApiErrorDTO> handleLojaException(LojaException ex, WebRequest request){
        String error = "Pane no sistema";
        ApiErrorDTO apiError = new ApiErrorDTO(ex.getMessage(), error, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<ApiErrorDTO> handleEntityNotFound(EntityNotFoundException ex, WebRequest request){
        String error = "Recurso não encontrado";
        ApiErrorDTO apiError = new ApiErrorDTO(ex.getMessage(), error, HttpStatus.NOT_FOUND);

        return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({UsuarioNaoPermitido.class})
    public ResponseEntity<ApiErrorDTO> handleUsuarioNaoAutorizado(UsuarioNaoPermitido ex, WebRequest request){
        String error = "Usuário não autenticado/autorizado";
        ApiErrorDTO apiError = new ApiErrorDTO(ex.getMessage(), error, HttpStatus.FORBIDDEN);

        return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({InputInvalidoException.class})
    public ResponseEntity<ApiErrorDTO> handleInputViolation(InputInvalidoException ex, WebRequest request){
        String error = "Esse input é inválido.";
        ApiErrorDTO apiError = new ApiErrorDTO(ex.getMessage(), error, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ApiErrorDTO>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}

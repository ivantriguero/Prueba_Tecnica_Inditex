package es.inditex.coreplatform.infrastructure.rest.controller;


import es.inditex.coreplatform.adapter.infrastructure.rest.openapi.dto.ErrorResponseDTO;
import es.inditex.coreplatform.domain.exception.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Lanza esta excepción cuando no se encuentra un precio aplicable.
     * Es una excepción personalizada del dominio.
     */
    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handlePriceNotFound(PriceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    /**
     * Lanza esta excepción cuando un parámetro de la query (como Long o OffsetDateTime)
     * no se puede convertir desde el String proporcionado (por ejemplo, "abc" en vez de "123").
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        assert ex.getRequiredType() != null;
        String message = String.format("El parámetro '%s' debe ser de tipo '%s'",
                ex.getName(), ex.getRequiredType().getSimpleName());
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(message));
    }

    /**
     * Lanza esta excepción cuando falta un parámetro obligatorio en la query string.
     * Ejemplo: no se envía `productId` en la URL.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingParams(MissingServletRequestParameterException ex) {
        String message = String.format("Falta el parámetro obligatorio: '%s'", ex.getParameterName());
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(message));
    }

    /**
     * Para cualquier excepción no controlada previamente.
     * Devuelve 500 y logea el error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex) {
        log.error("Error inesperado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponseDTO("Error interno del servidor."));
    }
}

package es.inditex.coreplatform.infrastructure.rest.controller;

import es.inditex.coreplatform.adapter.infrastructure.rest.openapi.dto.ErrorResponseDTO;
import es.inditex.coreplatform.domain.exception.PriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handlePriceNotFound_returns404WithMessage() {
        // Given
        PriceNotFoundException ex = new PriceNotFoundException(35455L, 1L, LocalDateTime.of(2020, 6, 14, 10, 0));

        // When
        ResponseEntity<ErrorResponseDTO> response = handler.handlePriceNotFound(ex);

        // Then
        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().getMessage().contains("Precio no encontrado"));
    }

    @Test
    void handleTypeMismatch_withRequiredType_returnsDetailedMessage() {
        // Given
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        doReturn("productId").when(ex).getName();
        doReturn((Class<?>) Long.class).when(ex).getRequiredType();

        // When
        ResponseEntity<ErrorResponseDTO> response = handler.handleTypeMismatch(ex);

        // Then
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("El parámetro 'productId' debe ser de tipo 'Long'", response.getBody().getMessage());
    }


    @Test
    void handleTypeMismatch_withoutRequiredType_returnsFallbackMessage() {
        // Given
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);
        when(ex.getName()).thenReturn("someParam");
        when(ex.getRequiredType()).thenReturn(null);

        // When
        ResponseEntity<ErrorResponseDTO> response = handler.handleTypeMismatch(ex);

        // Then
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("El parámetro 'someParam' tiene un tipo desconocido", response.getBody().getMessage());
    }

    @Test
    void handleMissingParams_returnsBadRequestWithParamName() {
        // Given
        MissingServletRequestParameterException ex =
                new MissingServletRequestParameterException("brandId", "Long");

        // When
        ResponseEntity<ErrorResponseDTO> response = handler.handleMissingParams(ex);

        // Then
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Falta el parámetro obligatorio: 'brandId'", response.getBody().getMessage());
    }

    @Test
    void handleGeneric_returnsInternalServerError() {
        // Given
        Exception ex = new RuntimeException("unexpected");

        // When
        ResponseEntity<ErrorResponseDTO> response = handler.handleGeneric(ex);

        // Then
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error interno del servidor.", response.getBody().getMessage());
    }
}

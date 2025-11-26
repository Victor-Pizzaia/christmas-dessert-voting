package com.christmas.dessert.voting.christmas_dessert_voting.dessert.web;

import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertId;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.DessertRequestDTO;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.domain.OwnerId;
import com.christmas.dessert.voting.christmas_dessert_voting.dessert.usecase.DessertUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DessertControllerTest {

    @Mock
    private DessertUseCase dessertUseCase;

    @InjectMocks
    private DessertController dessertController;

    private final Authentication authentication = mock(Authentication.class);
    private DessertRequestDTO dessertRequestDTO;
    private DessertDTO dessertDTO;
    private final OwnerId ownerId = new OwnerId();

    @BeforeEach
    public void setUp() {
        when(authentication.getName()).thenReturn(ownerId.id().toString());
        this.dessertRequestDTO = new DessertRequestDTO("Tiramisu", "Classic Italian dessert with coffee and mascarpone");
        this.dessertDTO = new DessertDTO(new DessertId().id().toString(), "Tiramisu", "Classic Italian dessert with coffee and mascarpone");
    }

    @Test
    void shouldRegisterDessertSuccessfully() {
        ResponseEntity<Void> response = dessertController.registerDessert(authentication, dessertRequestDTO);

        verify(dessertUseCase).registerDessert(eq(dessertRequestDTO), eq(ownerId));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldGetAllDessertsSuccessfully() {
        List<DessertDTO> desserts = List.of(dessertDTO);
        when(dessertUseCase.findAllDesserts(ownerId)).thenReturn(desserts);

        ResponseEntity<List<DessertDTO>> response = dessertController.getDesserts(authentication);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(desserts, response.getBody());
    }

    @Test
    void shouldGetDessertByIdSuccessfully() {
        DessertId dessertId = new DessertId();
        when(dessertUseCase.findDessertById(dessertId)).thenReturn(dessertDTO);

        ResponseEntity<DessertDTO> response = dessertController.getDessertById(dessertId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dessertDTO, response.getBody());
    }

    @Test
    void shouldGetDessertByNameSuccessfully() {
        String dessertName = "Tiramisu";
        when(dessertUseCase.findDessertByName(dessertName)).thenReturn(dessertDTO);

        ResponseEntity<DessertDTO> response = dessertController.getDessertByName(dessertName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dessertDTO, response.getBody());
    }

    @Test
    void shouldUpdateDessertSuccessfully() {
        DessertId dessertId = new DessertId();

        ResponseEntity<Void> response = dessertController.updateDessert(authentication, dessertId, dessertRequestDTO);

        verify(dessertUseCase).updateDessert(eq(dessertId), eq(dessertRequestDTO), eq(ownerId));
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

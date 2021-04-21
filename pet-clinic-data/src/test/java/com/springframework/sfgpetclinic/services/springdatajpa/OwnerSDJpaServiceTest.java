package com.springframework.sfgpetclinic.services.springdatajpa;

import com.springframework.sfgpetclinic.model.Owner;
import com.springframework.sfgpetclinic.repositories.OwnerRepository;
import com.springframework.sfgpetclinic.repositories.PetRepository;
import com.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// sets up junit5 env for mockito
@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;
    // inject Mocks into the constructor of the OwnerService
    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;

    Owner owner;

    String LAST_NAME = "Khan";


    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).lastName(LAST_NAME).build();

    }

    @Test
    void findAll() {
        Set<Owner> owners = new HashSet<>();
        owners.add(Owner.builder().id(1L).build());
        owners.add(Owner.builder().id(2L).build());
        when(ownerRepository.findAll()).thenReturn(owners);
        Set<Owner> foundOwners = ownerSDJpaService.findAll();
        assertNotNull(foundOwners);
        assertEquals(2,foundOwners.size());
        verify(ownerRepository,times(1)).findAll();
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(owner);
        Owner foundOwner = ownerSDJpaService.findByLastName(LAST_NAME);
        assertEquals(1L,foundOwner.getId());
        assertEquals(LAST_NAME,foundOwner.getLastName());
        verify(ownerRepository,times(1)).findByLastName(any());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(any())).thenReturn(Optional.of(owner));
        Owner foundOwner = ownerSDJpaService.findById(1L);
        assertNotNull(foundOwner);
        assertEquals(1L,foundOwner.getId());
        assertEquals(LAST_NAME,foundOwner.getLastName());
        verify(ownerRepository,times(1)).findById(any());
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());
        Owner foundOwner = ownerSDJpaService.findById(1L);
        assertNull(foundOwner);
        verify(ownerRepository).findById(any());
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(owner);
        Owner savedOwner = ownerSDJpaService.save(ownerToSave);
        assertNotNull(savedOwner);
        assertEquals(1L,savedOwner.getId());
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(owner);
        verify(ownerRepository).delete(any());

    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(1L);
        verify(ownerRepository).deleteById(anyLong());
    }
}
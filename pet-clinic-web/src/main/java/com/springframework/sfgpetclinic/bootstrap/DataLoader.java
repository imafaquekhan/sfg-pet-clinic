package com.springframework.sfgpetclinic.bootstrap;

import com.springframework.sfgpetclinic.model.Owner;
import com.springframework.sfgpetclinic.model.Pet;
import com.springframework.sfgpetclinic.model.PetType;
import com.springframework.sfgpetclinic.model.Vet;
import com.springframework.sfgpetclinic.services.OwnerService;
import com.springframework.sfgpetclinic.services.PetTypeService;
import com.springframework.sfgpetclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

// becomes a spring bean
@Component
public class DataLoader implements CommandLineRunner {

    // here it is polymorphism!!!
    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService){
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("cat");
        PetType savedCatPetType = petTypeService.save(cat);

        Owner owner = new Owner();
        owner.setFirstName("Micheal");
        owner.setLastName("Weston");

        Pet pet = new Pet();
        pet.setPetType(savedCatPetType);
        pet.setOwner(owner);
        pet.setBirthDate(LocalDate.now().minusMonths(5));
        pet.setName("Rosco");
        owner.getPets().add(pet);
        ownerService.save(owner);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");

        Pet pet2 = new Pet();
        pet2.setPetType(savedDogPetType);
        pet2.setOwner(owner2);
        pet2.setBirthDate(LocalDate.now().minusMonths(15));
        pet2.setName("Sheru");
        owner2.getPets().add(pet2);
        ownerService.save(owner2);

        System.out.println("Loaded Owners");


        Vet vet = new Vet();
        vet.setFirstName("Sam");
        vet.setLastName("Axe");
        vetService.save(vet);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vetService.save(vet2);

        System.out.println("Loaded Vets");


    }

}

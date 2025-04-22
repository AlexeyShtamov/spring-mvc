package school.sorokin.springboot.springmvcexam.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sorokin.springboot.springmvcexam.models.PetDto;
import school.sorokin.springboot.springmvcexam.services.PetService;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public ResponseEntity<PetDto> create(@RequestBody @Valid PetDto petDto){
        PetDto createdPet = petService.createPet(petDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> get(@PathVariable("id") Long id){
        PetDto foundedPet = petService.getPet(id);
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .body(foundedPet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> update(@RequestBody @Valid PetDto petDto, @PathVariable("id") Long id){
        PetDto updatedPet = petService.updatePet(petDto, id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id){
        petService.removePet(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}

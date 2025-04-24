package school.sorokin.springboot.springmvcexam.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import school.sorokin.springboot.springmvcexam.controllers.PetController;
import school.sorokin.springboot.springmvcexam.models.PetDto;
import school.sorokin.springboot.springmvcexam.models.UserDto;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PetService {

    private Long idCounter;
    private final Map<Long, PetDto> petDB;

    private final UserService userService;

    public PetService(@Lazy UserService userService){
        this.userService = userService;
        idCounter = 0L;
        this.petDB = new HashMap<>();
    }

    public PetDto createPet(PetDto pet){
        if (pet == null) throw new NullPointerException("Pet couldn't be null");

        UserDto user = getPetUser(pet.getUserId());

        PetDto createdPet = new PetDto(
                ++idCounter,
                pet.getName(),
                pet.getUserId()
        );
        user.getPets().add(createdPet);
        petDB.put(createdPet.getId(), createdPet);

        return createdPet;
    }

    public PetDto getPet(Long id){
        return Optional.ofNullable(petDB.get(id))
                .orElseThrow(() -> new NoSuchElementException("No pet with id: " + id));
    }

    public PetDto updatePet(PetDto updatedPet, Long id){
        if (updatedPet == null) throw  new NullPointerException("Pet couldn't be null");

        UserDto user = getPetUser(updatedPet.getUserId());

        PetDto pet = petDB.get(id);
        if (pet == null) throw new NoSuchElementException("No pet with id: " + id);


        pet.setName(updatedPet.getName());

        if (!updatedPet.getUserId().equals(pet.getId())){
            UserDto oldUser = userService.getUserDB().get(pet.getUserId());
            oldUser.getPets().remove(pet);
            user.getPets().add(pet);
        }
        pet.setUserId(updatedPet.getUserId());

        return pet;
    }

    public void removePet(Long id){
        PetDto pet = Optional.ofNullable(petDB.get(id))
                .orElseThrow(() -> new NoSuchElementException("No pet with id: " + id));

        UserDto user = getPetUser(pet.getUserId());
        user.getPets().remove(pet);

        petDB.remove(id);
    }

    private UserDto getPetUser(Long userId){
        return Optional.ofNullable(userService.getUserDB().get(userId))
                .orElseThrow(() -> new NoSuchElementException("No user with id: " + userId));
    }

    public Map<Long, PetDto> getPetDB(){
        return petDB;
    }

}

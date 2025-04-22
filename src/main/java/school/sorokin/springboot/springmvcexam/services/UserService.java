package school.sorokin.springboot.springmvcexam.services;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import school.sorokin.springboot.springmvcexam.models.PetDto;
import school.sorokin.springboot.springmvcexam.models.UserDto;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private Long idCounter;

    private final PetService petService;

    private final Map<Long, UserDto> userDB;

    public UserService(@Lazy PetService petService){
        this.petService = petService;
        idCounter = 0L;
        this.userDB = new HashMap<>();
    }

    public UserDto creteUser(UserDto user){
        if (user == null) throw  new NullPointerException("User couldn't be null");


        UserDto createdUser = new UserDto(
                ++idCounter,
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
        userDB.put(createdUser.getId(), createdUser);
        return createdUser;
    }

    public UserDto getUser(Long id){
        UserDto user = userDB.get(id);
        if (user == null) throw new NoSuchElementException("No user with id: " + id);

        return user;
    }

    public UserDto updateUser(UserDto updatedUser, Long id){
        if (updatedUser == null) throw  new NullPointerException("User couldn't be null");

        UserDto user = userDB.get(id);
        if (user == null) throw new NoSuchElementException("No user with id: " + id);


        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setAge(updatedUser.getAge());

        return user;
    }

    public void removeUser(Long id){
        var user = userDB.remove(id);
        if (user == null) {
            throw new NoSuchElementException("No found user by id=%s".formatted(id));
        }
        for (PetDto petDto : user.getPets()){
            petService.getPetDB().remove(petDto.getId());
        }
    }

    public Map<Long, UserDto> getUserDB(){
        return userDB;
    }


}

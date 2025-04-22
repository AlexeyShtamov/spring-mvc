package school.sorokin.springboot.springmvcexam.models;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDto{

        @Null
        private Long id;

        @NotBlank
        @Size(min = 1, max = 100)
        private String name;

        @Email
        private String email;

        @NotNull
        @Max(value = 250)
        private Integer age;

        private List<PetDto> pets;

        public UserDto() {}

        public UserDto(Long id, String name, String email, Integer age) {
                this.id = id;
                this.name = name;
                this.email = email;
                this.age = age;
                this.pets = new ArrayList<>();
        }

        public Long getId() {
                return id;
        }


        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public Integer getAge() {
                return age;
        }

        public void setAge(Integer age) {
                this.age = age;
        }

        public List<PetDto> getPets() {
                return pets;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                UserDto userDto = (UserDto) o;
                return Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email) && Objects.equals(age, userDto.age);
        }

        @Override
        public int hashCode() {
                return Objects.hash(name, email, age);
        }
}

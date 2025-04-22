package school.sorokin.springboot.springmvcexam.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class PetDto{

        @Null
        private Long id;

        @NotBlank
        @Size(min = 1, max = 100)
        private String name;

        @NotNull
        private Long userId;

        public PetDto() {}

        public PetDto(Long id, String name, Long userId) {
                this.id = id;
                this.name = name;
                this.userId = userId;
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

        public Long getUserId() {
                return userId;
        }

        public void setUserId(Long userId) {
                this.userId = userId;
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                PetDto petDto = (PetDto) o;
                return Objects.equals(name, petDto.name) && Objects.equals(userId, petDto.userId);
        }

        @Override
        public int hashCode() {
                return Objects.hash(name, userId);
        }
}

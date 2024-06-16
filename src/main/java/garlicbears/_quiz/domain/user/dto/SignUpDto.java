package garlicbears._quiz.domain.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SignUpDto {

    private Long id;
    @NotNull
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 200)
    private String password;

    @NotNull
    @Size(max = 100)
    private String nickname;

    @NotNull
    private int birthYear;

    @NotNull
    private int age;

    @NotNull
    private String gender;

    @NotNull
    private String location;

    public Long getId() {
        return id;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){ return password; }

    public String getNickname(){
        return nickname;
    }

    public int getBirthYear(){ return birthYear; }

    public int getAge(){
        return age;
    }

    public String getGender(){
        return gender;
    }

    public String getLocation(){ return location; }
}

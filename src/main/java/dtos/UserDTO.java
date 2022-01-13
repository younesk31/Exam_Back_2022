package dtos;

import entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private String username;
    private String password;
    private String address;
    private String phone;
    private String email;
    private int birthYear;
    private int accountBalance;

    public static List<UserDTO> getDtos(List<User> u){
        List<UserDTO> userDTOSdtos = new ArrayList();
        u.forEach(dtotoentity -> userDTOSdtos.add(new UserDTO(dtotoentity)));
        return userDTOSdtos;
    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.birthYear = user.getBirthYear();
        this.accountBalance = user.getAccountBalance();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}

package entities;

import dtos.UserDTO;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Basic(optional = false)
  @NotNull
  @Column(name = "user_name", length = 25)
  private String username;
  @Basic(optional = false)
  @NotNull
  @Size(min = 1, max = 255)
  @Column(name = "user_pass")
  private String password;
  @JoinTable(name = "user_roles", joinColumns = {
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")}, inverseJoinColumns = {
    @JoinColumn(name = "role_name", referencedColumnName = "role_name")})
  @ManyToMany(cascade = CascadeType.PERSIST)
  private List<Role> roleList = new ArrayList<>();

  @Column(name = "address")
  private String address;
  @Column(name = "phone")
  private String phone;
  @Column(name = "email")
  private String email;
  @Column(name = "birth_year")
  private int birthYear;
  @Column(name = "balance")
  private int accountBalance;

  @ManyToMany(mappedBy = "users",cascade = CascadeType.PERSIST)
  private List<Assignment> assignments;

  @OneToMany(mappedBy = "user",cascade = CascadeType.PERSIST)
  List<Transaction> transaction;

  public User() {}

  public User(UserDTO userDTO) {
    this.username = userDTO.getUsername();
    this.password = BCrypt.hashpw(userDTO.getPassword(),BCrypt.gensalt());
  }

  public boolean verifyPassword(String pw){
        return BCrypt.checkpw(pw,this.password);
    }

  public User(String username, String password, String address, String phone, String email, int birthYear, int accountBalance) {
    this.username = username;
    this.address = address;
    this.phone = phone;
    this.email = email;
    this.birthYear = birthYear;
    this.accountBalance = accountBalance;
    this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    this.transaction = new ArrayList<>();
    this.assignments = new ArrayList<>();
  }

  public List<String> getRolesAsStrings() {
    if (roleList.isEmpty()) {
      return null;
    }
    List<String> rolesAsStrings = new ArrayList<>();
    roleList.forEach((role) -> {
      rolesAsStrings.add(role.getRoleName());
    });
    return rolesAsStrings;
  }

  public List<Transaction> getTransactionDetails() {
    return transaction;
  }

  public void addTransaction(Transaction transaction) {
    this.transaction.add(transaction);
    if (transaction != null){
      transaction.setUser(this);
    }
  }

  public List<Assignment> getAssignments() {
    return assignments;
  }

  public void addAssignments(Assignment assignments) {
    if (assignments != null){
      this.assignments.add(assignments);
      assignments.getUsers().add(this);
    }

  }

  public List<Transaction> getTransaction() {
    return transaction;
  }

  public void setTransaction(List<Transaction> transaction) {
    this.transaction = transaction;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String userName) {
    this.username = userName;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String userPass) {
    this.password = BCrypt.hashpw(userPass,BCrypt.gensalt());
  }

  public List<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public void addRole(Role userRole) {
    roleList.add(userRole);
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
}

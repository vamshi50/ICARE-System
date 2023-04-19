package com.icare.db.entities;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UserPassword {
    private @Id
    @GeneratedValue Long id;
    @OneToOne
    private User user;
    private String userName;
    private String encryptedPassword;
    private int passwordExpiryTime;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date userAccountExpiryDate;

    public UserPassword() {
    }

    public UserPassword(String userName, User user, String encryptedPassword, int passwordExpiryTime, Date userAccountExpiryDate) {
        this.userName = userName;
        this.user = user;
        this.encryptedPassword = encryptedPassword;
        this.passwordExpiryTime = passwordExpiryTime;
        this.userAccountExpiryDate = userAccountExpiryDate;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public int getPasswordExpiryTime() {
        return this.passwordExpiryTime;
    }

    public void setPasswordExpiryTime(int passwordExpiryTime) {
        this.passwordExpiryTime = passwordExpiryTime;
    }

    public Date getUserAccountExpiryDate() {
        return this.userAccountExpiryDate;
    }

    public void setUserAccountExpiryDate(Date userAccountExpiryDate) {
        this.userAccountExpiryDate = userAccountExpiryDate;
    }

    public void encryptPassword() {
        this.encryptedPassword = new BCryptPasswordEncoder().encode(this.encryptedPassword);
    }

    @Override
    public String toString() {
        return "UserPassword(" +
            " id='" + getId() + "'" +
            ", userName='" + getUserName() + "'" +
            ")";
    }
}

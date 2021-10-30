package com.example.costall.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    String Name;
    String Surname;
    String DateOfBirth;
    String CellPhoneNumber;
    String Country;
    String Region;
    String City;
    String Address;
    String Company;
    String Department;
    String Position;
    double Salary;
    String Username;
    String Email;
    String Password;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", DateOfBirth='" + DateOfBirth + '\'' +
                ", CellPhoneNumber='" + CellPhoneNumber + '\'' +
                ", Country='" + Country + '\'' +
                ", Region='" + Region + '\'' +
                ", City='" + City + '\'' +
                ", Address='" + Address + '\'' +
                ", Company='" + Company + '\'' +
                ", Department='" + Department + '\'' +
                ", Position='" + Position + '\'' +
                ", Salary=" + Salary +
                ", Username='" + Username + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(user.Salary, Salary) == 0 &&
                Objects.equals(Name, user.Name) &&
                Objects.equals(Surname, user.Surname) &&
                Objects.equals(DateOfBirth, user.DateOfBirth) &&
                Objects.equals(CellPhoneNumber, user.CellPhoneNumber) &&
                Objects.equals(Country, user.Country) &&
                Objects.equals(Region, user.Region) &&
                Objects.equals(City, user.City) &&
                Objects.equals(Address, user.Address) &&
                Objects.equals(Company, user.Company) &&
                Objects.equals(Department, user.Department) &&
                Objects.equals(Position, user.Position) &&
                Objects.equals(Username, user.Username) &&
                Objects.equals(Email, user.Email) &&
                Objects.equals(Password, user.Password);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(Name, Surname, DateOfBirth, CellPhoneNumber, Country, Region, City, Address, Company, Department, Position, Salary, Username, Email, Password);
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getCellPhoneNumber() {
        return CellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        CellPhoneNumber = cellPhoneNumber;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String company) {
        Company = company;
    }

    public String getDepartment() {
        return Department;
    }

    public void setDepartment(String department) {
        Department = department;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}

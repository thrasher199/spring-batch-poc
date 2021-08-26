package com.example.springbatchpoc.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String empId;
    private String namePrefix;
    private String firstName;
    private String middleInitial;
    private String lastName;
    private String gender;
    private String email;
    private String fatherName;
    private String motherName;
    private String motherMaidenName;
    private String dob;
    private String tob;
    private String age;
    private String weight;
    private String dateOfJoining;
    private String quarterOfJoining;
    private String halfOfJoining;
    private String yearOfJoining;
    private String monthOfJoining;
    private String monthNameOfJoining;
    private String shortMonth;
    private String dayOfJoining;
    private String dowOfJoining;
    private String shortDOW;
    private String ageInCompany;
    private String salary;
    private String lastPercentageHike;
    private String ssn;
    private String phoneNo;
    private String placeName;
    private String county;
    private String city;
    private String state;
    private String zip;
    private String region;
    private String userName;
    private String password;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherMaidenName() {
        return motherMaidenName;
    }

    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getTob() {
        return tob;
    }

    public void setTob(String tob) {
        this.tob = tob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(String dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getQuarterOfJoining() {
        return quarterOfJoining;
    }

    public void setQuarterOfJoining(String quarterOfJoining) {
        this.quarterOfJoining = quarterOfJoining;
    }

    public String getHalfOfJoining() {
        return halfOfJoining;
    }

    public void setHalfOfJoining(String halfOfJoining) {
        this.halfOfJoining = halfOfJoining;
    }

    public String getYearOfJoining() {
        return yearOfJoining;
    }

    public void setYearOfJoining(String yearOfJoining) {
        this.yearOfJoining = yearOfJoining;
    }

    public String getMonthOfJoining() {
        return monthOfJoining;
    }

    public void setMonthOfJoining(String monthOfJoining) {
        this.monthOfJoining = monthOfJoining;
    }

    public String getMonthNameOfJoining() {
        return monthNameOfJoining;
    }

    public void setMonthNameOfJoining(String monthNameOfJoining) {
        this.monthNameOfJoining = monthNameOfJoining;
    }

    public String getShortMonth() {
        return shortMonth;
    }

    public void setShortMonth(String shortMonth) {
        this.shortMonth = shortMonth;
    }

    public String getDayOfJoining() {
        return dayOfJoining;
    }

    public void setDayOfJoining(String dayOfJoining) {
        this.dayOfJoining = dayOfJoining;
    }

    public String getDowOfJoining() {
        return dowOfJoining;
    }

    public void setDowOfJoining(String dowOfJoining) {
        this.dowOfJoining = dowOfJoining;
    }

    public String getShortDOW() {
        return shortDOW;
    }

    public void setShortDOW(String shortDOW) {
        this.shortDOW = shortDOW;
    }

    public String getAgeInCompany() {
        return ageInCompany;
    }

    public void setAgeInCompany(String ageInCompany) {
        this.ageInCompany = ageInCompany;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getLastPercentageHike() {
        return lastPercentageHike;
    }

    public void setLastPercentageHike(String lastPercentageHike) {
        this.lastPercentageHike = lastPercentageHike;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

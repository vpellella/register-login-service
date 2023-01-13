package com.example.demo.dummy;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record DummyUser(String id, String name) {
}

class Dummy {
    public static void main(String[] args) throws UnknownHostException {
        LocalTime date = LocalTime.parse("15:25:08.690791");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h BBBBB");
        System.out.println(formatter.format(date));

        InetAddress address = InetAddress.getByName("www.google.com");
        System.out.println(address.getHostAddress());
        StringBuilder addressStr = new StringBuilder();
        for (byte c : address.getAddress()){
            addressStr.append((char) c);
        }
        System.out.println(addressStr);
        System.out.println(address.getCanonicalHostName());
        System.out.println(address.getHostName());

    }
}

abstract sealed class Person permits Teacher, Student{
    private String name;
    Person(String name){
    }
    public String getName(){
        return  this.name;
    }
}

sealed class Teacher extends Person permits HighSchoolTeacher, PrimarySchoolTeacher{

    Teacher(String name) {
        super(name);
    }
}
final class HighSchoolTeacher extends Teacher implements Serializable {

    HighSchoolTeacher(String name) {
        super(name);
    }
}

non-sealed class PrimarySchoolTeacher extends Teacher{

    PrimarySchoolTeacher(String name) {
        super(name);
    }
}

non-sealed class Student extends Person{

    Student(String name) {
        super(name);
    }

}

class CleverStudent extends Student{

    CleverStudent(String name) {
        super(name);
    }
}
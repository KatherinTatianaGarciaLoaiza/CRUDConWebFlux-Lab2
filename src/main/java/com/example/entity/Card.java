package com.example.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "cards")
public class Card {

    @Id
    private String number;
    private String type;
    private String code;
    private String date;
    private String title;

    public Card(String number, String title){
        this.number = number;
        this.code = generateCode(number);
        this.date = generateDate();
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String generateCode(String number){
        return number.substring(0, 2);
    }

    private String generateDate(){
        return new Date().toString();
    }

    @Override
    public String toString() {
        return "Card{" + '\n' +
                "Number = " + number + '\n' +
                "Code = " + code + '\n' +
                "Type = " + type + '\n' +
                "Date = " + date + '\n' +
                "Title = " + code + '\n' +
                '}';
    }
}

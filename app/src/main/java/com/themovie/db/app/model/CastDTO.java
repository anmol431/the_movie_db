package com.themovie.db.app.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CastDTO implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("gender")
    private int gender;

    @SerializedName("known_for_department")
    private String  known_for_department;

    @SerializedName("name")
    private String  name;

    @SerializedName("character")
    private String  character;

    @SerializedName("profile_path")
    private String  profile_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    @NonNull
    @Override
    public String toString() {
        return "CastDTO{" +
                "id=" + id +
                ", gender=" + gender +
                ", known_for_department='" + known_for_department + '\'' +
                ", name='" + name + '\'' +
                ", character='" + character + '\'' +
                ", profile_path='" + profile_path + '\'' +
                '}';
    }
}

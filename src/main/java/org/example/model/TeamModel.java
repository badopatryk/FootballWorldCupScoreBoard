package org.example.model;

public record TeamModel(String name) {

    @Override
    public String toString() {
        return name;
    }
}

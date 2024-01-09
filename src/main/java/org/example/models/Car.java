package org.example.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Car {
    private int id;
    @NotEmpty(message = "Название марки не должно быть пустым")
    private String brandTitle;
    @NotEmpty(message = "Название модели не должно быть пустым")
    private String modelTitle;
    @Min(value = 1885, message = "Год производства должен соответствовать реальности")
    @Max(value = 2024, message = "Год производства должен соответствовать реальности")
    private int yearOfRelease;

    public Car() {
    }

    public Car(int id, String brandTitle, String modelTitle, int yearOfRelease) {
        this.id = id;
        this.brandTitle = brandTitle;
        this.modelTitle = modelTitle;
        this.yearOfRelease = yearOfRelease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelTitle() {
        return modelTitle;
    }

    public void setModelTitle(String modelTitle) {
        this.modelTitle = modelTitle;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getBrandTitle() {
        return brandTitle;
    }

    public void setBrandTitle(String brandTitle) {
        this.brandTitle = brandTitle;
    }
}

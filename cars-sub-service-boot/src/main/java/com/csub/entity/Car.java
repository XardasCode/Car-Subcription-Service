package com.csub.entity;

import com.csub.controller.request.CarRequestDTO;
import com.csub.entity.audit.CarEntityListener;
import jakarta.persistence.*;
import lombok.*;

@EntityListeners(CarEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "model")
    private String model;

    @Column(name = "brand")
    private String brand;

    @Column(name = "year")
    private int year;

    @Column(name = "color")
    private String color;

    @Column(name = "price")
    private int price;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "chassis_number")
    private String chassisNumber;

    @Column(name = "reg_number")
    private String regNumber;

    @Column(name = "reg_date")
    private String regDate;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "last_service_date")
    private String lastServiceDate;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "last_update_date")
    private String lastUpdateDate;

    @Column(name = "image_path")
    private String imagePath;

    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Subscription subscription;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "status_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private CarStatus carStatus;

    public static Car createCarFromRequest(CarRequestDTO car) {
        return Car.builder()
                .name(car.getName() == null ? null : car.getName())
                .model(car.getModel() == null ? null : car.getModel())
                .brand(car.getBrand() == null ? null : car.getBrand())
                .year(car.getYear() == null ? 0 : Integer.parseInt(car.getYear()))
                .color(car.getColor() == null ? null : car.getColor())
                .price(car.getPrice() == null ? 0 : Integer.parseInt(car.getPrice()))
                .fuelType(car.getFuelType() == null ? null : car.getFuelType())
                .chassisNumber(car.getChassisNumber() == null ? null : car.getChassisNumber())
                .regNumber(car.getRegNumber() == null ? null : car.getRegNumber())
                .regDate(car.getRegDate() == null ? null : car.getRegDate())
                .mileage(car.getMileage() == null ? 0 : Integer.parseInt(car.getMileage()))
                .lastServiceDate(car.getLastServiceDate() == null ? null : car.getLastServiceDate())
                .build();
    }

    public static void mergeCars(Car dbCar, Car carEntity) {
        carEntity.setId(dbCar.getId());
        carEntity.setCreateDate(dbCar.getCreateDate());
        carEntity.setName((carEntity.getName() != null && !carEntity.getName().isBlank())
                ? carEntity.getName() : dbCar.getName());
        carEntity.setModel((carEntity.getModel() != null && !carEntity.getModel().isBlank())
                ? carEntity.getModel() : dbCar.getModel());
        carEntity.setBrand((carEntity.getBrand() != null && !carEntity.getBrand().isBlank())
                ? carEntity.getBrand() : dbCar.getBrand());
        carEntity.setYear((carEntity.getYear() != 0) ? carEntity.getYear() : dbCar.getYear());
        carEntity.setColor((carEntity.getColor() != null && !carEntity.getColor().isBlank())
                ? carEntity.getColor() : dbCar.getColor());
        carEntity.setPrice((carEntity.getPrice() != 0) ? carEntity.getPrice() : dbCar.getPrice());
        carEntity.setFuelType((carEntity.getFuelType() != null && !carEntity.getFuelType().isBlank())
                ? carEntity.getFuelType() : dbCar.getFuelType());
        carEntity.setChassisNumber((carEntity.getChassisNumber() != null && !carEntity.getChassisNumber().isBlank())
                ? carEntity.getChassisNumber() : dbCar.getChassisNumber());
        carEntity.setRegNumber((carEntity.getRegNumber() != null && !carEntity.getRegNumber().isBlank())
                ? carEntity.getRegNumber() : dbCar.getRegNumber());
        carEntity.setRegDate((carEntity.getRegDate() != null && !carEntity.getRegDate().isBlank())
                ? carEntity.getRegDate() : dbCar.getRegDate());
        carEntity.setMileage((carEntity.getMileage() != 0) ? carEntity.getMileage() : dbCar.getMileage());
        carEntity.setLastServiceDate((carEntity.getLastServiceDate() != null && !carEntity.getLastServiceDate().isBlank())
                ? carEntity.getLastServiceDate() : dbCar.getLastServiceDate());
        carEntity.setImagePath((carEntity.getImagePath() != null && !carEntity.getImagePath().isBlank())
                ? carEntity.getImagePath() : dbCar.getImagePath());
        carEntity.setSubscription(carEntity.getSubscription() != null ? carEntity.getSubscription() : dbCar.getSubscription());
    }
}


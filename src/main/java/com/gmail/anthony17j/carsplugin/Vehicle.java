package com.gmail.anthony17j.carsplugin;

import org.bukkit.entity.ArmorStand;

import java.util.HashMap;

public class Vehicle {

    public static HashMap<String, ArmorStand> autoStand = new HashMap<>();
    public static HashMap<String, Integer> seatSize = new HashMap<>();
    public static HashMap<String, Double> seatX = new HashMap<>();
    public static HashMap<String, Double> seatY = new HashMap<>();
    public static HashMap<String, Double> seatZ = new HashMap<>();
    public static HashMap<String, Double> speed = new HashMap<>();
    public static HashMap<String, Double> breakingSpeed = new HashMap<>();
    public static HashMap<String, Double> maxSpeed = new HashMap<>();
    public static HashMap<String, Double> maxSpeedBackwards = new HashMap<>();
    public static HashMap<String, Double> accelerationSpeed = new HashMap<>();
    public static HashMap<String, Double> rotateSpeed = new HashMap<>();
    public static HashMap<String, Double> frictionSpeed = new HashMap<>();

}

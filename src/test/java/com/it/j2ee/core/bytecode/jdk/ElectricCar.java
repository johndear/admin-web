package com.it.j2ee.core.bytecode.jdk;

public class ElectricCar implements Rechargable, Vehicle {  
	  
    @Override  
    public void drive() {  
        System.out.println("Electric Car is Moving silently...");  
    }  
  
    @Override  
    public void recharge() {  
        System.out.println("Electric Car is Recharging...");  
    }  
  
}  

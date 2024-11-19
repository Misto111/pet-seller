package com.project.petSeller.service.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* com.project.petSeller.service.PetOfferService.getAllOffers(..))")
    public void trackOfferSearch() {
    }
}

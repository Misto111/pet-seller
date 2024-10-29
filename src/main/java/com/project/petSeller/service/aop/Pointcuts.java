package com.project.petSeller.service.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* com.project.petSeller.service.OfferService.getAllOffers(..))")
    public void trackOfferSearch() {}


    @Pointcut("@annotation(WarnIfExecutionExceeds)")
    public void warnIfExecutionExceeds(){}
}

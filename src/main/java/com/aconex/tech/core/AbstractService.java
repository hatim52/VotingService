package com.aconex.tech.core;

public abstract class AbstractService {
    protected final Services services;
    protected final String fileLocation;

    protected AbstractService(Services services,String fileLocation) {
        this.services = services;
        this.fileLocation=fileLocation;
    }
}


package com.aconex.tech.api;

public class CandidateList  {

    private final String fileLocation;

    public CandidateList(String fileLocation) {
        this.fileLocation=fileLocation;

    }

    public String getName() {
        return fileLocation;
    }

    public String toString() {
        return fileLocation;
    }



}

package org.github.snambi.bbs.service;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 */
@Component
public class VersionService {

    Random random = new Random();

    public String version(){
        String result = "" + random.nextInt(10)  + "." + random.nextInt(10) + "." + random.nextInt(10);
        return result;
    }
}

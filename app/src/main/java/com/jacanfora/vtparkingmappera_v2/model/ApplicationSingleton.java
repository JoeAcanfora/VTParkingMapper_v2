package com.jacanfora.vtparkingmappera_v2.model;

/**
 * Created by jacanfora on 8/22/16.
 */
import android.app.Application;

public class ApplicationSingleton extends Application {

    private Credential pass;
    static public String sharedPrefName = "credentials";

    /**
     * @return the pass
     */
    public Credential getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(Credential pass) {
        this.pass = pass;
    }

}

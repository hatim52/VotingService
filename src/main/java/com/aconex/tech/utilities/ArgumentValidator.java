package com.aconex.tech.utilities;

/**
 * This is a common utility class to be able to check if arguments being passed by user are valid for use.
 */

import com.aconex.tech.exceptions.MissingResourceException;
import com.aconex.tech.exceptions.UnAuthorizedException;

import java.io.File;
import java.text.MessageFormat;

public class ArgumentValidator {
    /**
     *
     * @param obj
     */
    public static void checkArgumentNullOrEmpty(Object obj){
        try {
            if (null == obj || obj == "") {
                throw new IllegalArgumentException ("Argument passed is either null or empty.");
            }
        }catch(IllegalArgumentException il){
            System.out.println (il.getMessage ());
            System.exit (1);
        }
    }

    /**
     *Validate if the location passed exists on the file system.
     * @param location
     */
    public static void checkLocationExists(String location) {
        try {
            //Check if the parameter passed is not null
            if (null == location) {
                throw new MissingResourceException (MessageFormat.format ("Location {0}  is passed as null. ", location));
            }
            File testLoc = new File (location);
            //Check if the location exists
            if (!testLoc.exists ( )) {
                throw new MissingResourceException (MessageFormat.format("Location {0} does not exist.",location));
            }
        }catch(MissingResourceException e){
            System.out.println(e.getMessage ());
            System.exit (1);
//            e.printStackTrace ();
        }
    }

    /**
     *Checks if the file/folder passed has read permissions.
     * @param location
     */
    public static void checkFileReadPermission(String location){
        try {
            //Check if the parameter passed is not null
            if (null == location) {
                throw new MissingResourceException (MessageFormat.format ("Location {0} is passed as null.", location));
            }
            File testLoc = new File (location);
            //If you are here, mean location exists, check if you have permissions to read the wallet
            if (!testLoc.canRead ( )) {
                throw new UnAuthorizedException (MessageFormat.format ("Unable to read from location - {0}",location));
            }
        } catch(MissingResourceException mr){
            System.out.println (mr.getMessage ());
            System.exit (1);
            //mr.printStackTrace ();
        } catch(UnAuthorizedException ua){
            System.out.println (ua.getMessage ());
            System.exit (1);
            //mr.printStackTrace ();
        }
    }

}
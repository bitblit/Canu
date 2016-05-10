package com.erigir.canu;

import org.apache.commons.cli.*;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;
/**
 * Created by cweiss on 5/10/16.
 */
public class TestCanuCLI {

    @Test
    public void parseShortArgs()
            throws Exception
    {

        String[] args =
                new String[]{
                        "-t","tofile.txt",
                        "-f","fromfile.txt",
                        "-c","ccfile.txt",
                        "-b","bccfile.txt",
                        "-s","test subject",
                        "-h","htfile.html",
                        "-x","txtfile.txt",
                        "-a","attachment1.jpg",
                        "-a","attachment2.png",
                };

        Canu canu = CanuCLI.canuFromCommandLineArgs(args);

        assertEquals("test subject", canu.getSubject());
        assertEquals(2, canu.getAttachments().size());

    }

}

package com.erigir.canu;

import org.junit.Test;

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
                        "-t","direct-to@test.com",
                        "-o","src/test/resources/test-to-file.txt",
                        "-f","test-from@test.com",
                        "-c","src/test/resources/test-cc-file.txt",
                        "-b","src/test/resources/test-bcc-file.txt",
                        "-s","test subject",
                        "-h","src/test/resources/test-html-body.html",
                        "-x","src/test/resources/test-text-body.txt",
                        "-a","src/test/resources/test-attachment-1.jpeg",
                        "-a","src/test/resources/test-attachment-2.pdf"
                };

        Canu canu = CanuCLI.canuFromCommandLineArgs(args);

        assertEquals("test subject", canu.getSubject());
        assertEquals(2, canu.getAttachments().size());

        canu.validate();
    }

}

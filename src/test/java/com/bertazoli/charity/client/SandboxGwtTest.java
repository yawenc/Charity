package com.bertazoli.charity.client;

import com.google.gwt.junit.client.GWTTestCase;

public class SandboxGwtTest extends GWTTestCase {
    @Override
    public String getModuleName() {
        return "com.bertazoli.charity.Charity";
    }

    public void testSandbox() {
        assertTrue(true);
    }
}

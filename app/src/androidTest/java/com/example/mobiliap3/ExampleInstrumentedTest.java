package com.example.mobiliap3;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.mobiliap3", appContext.getPackageName());
    }
    
    @Test
    public void testBasicFunctionality() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertNotNull("Context não deve ser nulo", appContext);
        assertTrue("Package name deve estar correto", 
                   appContext.getPackageName().contains("mobiliap3"));
    }
}
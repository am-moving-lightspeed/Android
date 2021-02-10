<<<<<<< HEAD:Tabata_timer/app/src/androidTest/java/com/tabatatimer/ExampleInstrumentedTest.java
package com.tabatatimer;
=======
package com.oaoaoa.battleships;

>>>>>>> Battleships:Battleships/app/src/androidTest/java/com/oaoaoa/battleships/ExampleInstrumentedTest.java

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;



/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
<<<<<<< HEAD:Tabata_timer/app/src/androidTest/java/com/tabatatimer/ExampleInstrumentedTest.java
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.tabatatimer", appContext.getPackageName());
=======
        Context appContext = InstrumentationRegistry.getInstrumentation()
                                                    .getTargetContext();
        assertEquals("com.oaoaoa.battleships", appContext.getPackageName());
>>>>>>> Battleships:Battleships/app/src/androidTest/java/com/oaoaoa/battleships/ExampleInstrumentedTest.java
    }

}
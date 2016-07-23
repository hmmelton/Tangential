package com.hmmelton.tangential;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.logging.Logger;


/**
 * Created by harrisonmelton on 7/20/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest()
public class MonteCarloTest {

    @Test
    public void monteCarloValidator_AccurateStats() {
        Logger logger = PowerMockito.mock(Logger.class);
        Mockito.verify(logger).warning("test");
    }

}

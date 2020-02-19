package de.fred4jupiter.fredbet.service.config;

import de.fred4jupiter.fredbet.AbstractTransactionalIntegrationTest;
import de.fred4jupiter.fredbet.domain.RuntimeConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class RuntimeConfigurationServiceIT extends AbstractTransactionalIntegrationTest {

    @Autowired
    private RuntimeConfigurationService runtimeConfigurationService;

    @Test
    public void loadAndSaveConfiguration() {
        RuntimeConfig runtimeConfig = runtimeConfigurationService.loadRuntimeConfig();
        assertNotNull(runtimeConfig);

        runtimeConfig.setEnabledParentChildRanking(true);

        runtimeConfigurationService.saveRuntimeConfig(runtimeConfig);

        RuntimeConfig loaded = runtimeConfigurationService.loadRuntimeConfig();
        assertNotNull(loaded);
        assertTrue(loaded.isEnabledParentChildRanking());
    }
}

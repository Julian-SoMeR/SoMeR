package modules;

import com.google.inject.AbstractModule;
import models.ApplicationStart;

/**
 * This module is necessary to configure Elastic Search and index everything.
 */
public class StartModule extends AbstractModule {
    protected void configure() {
        bind(ApplicationStart.class).asEagerSingleton();
    }
}
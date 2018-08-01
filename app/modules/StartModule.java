package modules;

import com.google.inject.AbstractModule;
import models.ApplicationStart;

public class StartModule extends AbstractModule {
    protected void configure() {
        bind(ApplicationStart.class).asEagerSingleton();
    }
}
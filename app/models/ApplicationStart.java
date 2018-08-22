package models;

import javax.inject.*;
import play.inject.ApplicationLifecycle;
import play.Environment;
import java.util.concurrent.CompletableFuture;
import io.ebean.DocumentStore;
import io.ebean.EbeanServer;
import io.ebean.Ebean;


/**
 * The StartModule binds this to enable a single instance on startup. Everything that needs to happen on application
 * startup goes here.
 */
@Singleton
public class ApplicationStart {

    /* Inject the application's Environment upon start-up and register hook(s) for shut-down. */
    @Inject
    public ApplicationStart(ApplicationLifecycle lifecycle, Environment environment) {
        elasticIndexAll();

        // Shut-down hook
        lifecycle.addStopHook( () -> {
            return CompletableFuture.completedFuture(null);
        } );

    }

    public static void elasticIndexAll() {
        DocumentStore documentStore = Ebean.getDefaultServer().docStore();
        documentStore.indexAll(Platform.class);
        documentStore.indexAll(Information.class);
        documentStore.indexAll(Function.class);
        documentStore.indexAll(Impact.class);
        documentStore.indexAll(Aggregator.class);
        documentStore.indexAll(InformationContent.class);
        documentStore.indexAll(FunctionContent.class);
        documentStore.indexAll(ImpactContent.class);
        documentStore.indexAll(AggregatorContent.class);
        System.out.println("IT WORKED");
    }

}
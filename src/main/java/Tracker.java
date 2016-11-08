import Config.AppConfig;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by fiyyanp on 11/7/2016.
 */
public class Tracker implements io.dropwizard.ConfiguredBundle<AppConfig> {

    public void initialize(Bootstrap<?> bootstrap) {

    }

    public void run(AppConfig configuration, Environment environment) throws Exception {
        TrackerTask tracker = new TrackerTask(configuration);
        tracker.execute();

    }
}

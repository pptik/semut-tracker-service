import Config.AppConfig;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by fiyyanp on 11/7/2016.
 */
public class TrackerService extends Application<AppConfig> {
    public void run(AppConfig configuration, Environment environment) throws Exception {

    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        bootstrap.addBundle(new Tracker());
    }

    public static void main(String[] args) throws Exception {
        new TrackerService().run(args);
    }
}

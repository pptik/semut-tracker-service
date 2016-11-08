package Config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSourceFactory;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by fiyyanp on 11/7/2016.
 */
public class AppConfig extends Configuration {
    @JsonProperty
    @NotNull
    private String rabbitMQHost;
    @JsonProperty
    @NotNull
    private String rabbitMQUsername;
    @JsonProperty
    @NotNull
    private String rabbitMQPassword;
    @JsonProperty
    @NotNull
    private String rabbitMQVHost;

    @JsonProperty
    @NotNull
    private String mySqlhost;
    @JsonProperty
    @NotNull
    private String mySqlDb;
    @JsonProperty
    @NotNull
    private String mySqlpass;
    @JsonProperty
    @NotNull
    private String mySqluser;

    public String getRabbitMQHost() {
        return rabbitMQHost;
    }

    public void setRabbitMQHost(String rabbitMQHost) {
        this.rabbitMQHost = rabbitMQHost;
    }

    public String getRabbitMQUsername() {
        return rabbitMQUsername;
    }

    public void setRabbitMQUsername(String rabbitMQUsername) {
        this.rabbitMQUsername = rabbitMQUsername;
    }

    public String getRabbitMQPassword() {
        return rabbitMQPassword;
    }

    public void setRabbitMQPassword(String rabbitMQPassword) {
        this.rabbitMQPassword = rabbitMQPassword;
    }

    public String getRabbitMQVHost() {
        return rabbitMQVHost;
    }

    public void setRabbitMQVHost(String rabbitMQVHost) {
        this.rabbitMQVHost = rabbitMQVHost;
    }

    public String getMySqlhost() {
        return mySqlhost;
    }

    public void setMySqlhost(String mySqlhost) {
        this.mySqlhost = mySqlhost;
    }

    public String getMySqluser() {
        return mySqluser;
    }

    public void setMySqluser(String mySqluser) {
        this.mySqluser = mySqluser;
    }

    public String getMySqlpass() {
        return mySqlpass;
    }

    public void setMySqlpass(String mySqlpass) {
        this.mySqlpass = mySqlpass;
    }

    public String getMySqlDb() {
        return mySqlDb;
    }

    public void setMySqlDb(String mySqlDb) {
        this.mySqlDb = mySqlDb;
    }
}

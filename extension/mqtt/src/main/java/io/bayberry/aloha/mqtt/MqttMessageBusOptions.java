package io.bayberry.aloha.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class MqttMessageBusOptions {

    private String serverUri;
    private String clientId = "";
    private int qos;
    private boolean cleanSession;
    private String username;
    private String password;

    public String getServerUri() {
        return serverUri;
    }

    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public boolean isCleanSession() {
        return cleanSession;
    }

    public void setCleanSession(boolean cleanSession) {
        this.cleanSession = cleanSession;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MqttConnectOptions getConnectOptions() {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setCleanSession(this.cleanSession);
        connectOptions.setUserName(this.username);
        if (this.password != null) {
            connectOptions.setPassword(this.password.toCharArray());
        }
        return connectOptions;
    }
}

package org.home.notifier.indicator;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.home.notifier.common.IndicatorStatuses;

public class IndicatorController {

    public IndicatorStatuses getStatus() {
        return IndicatorStatuses.UNKNOWN;
    }

    public void setStatus(IndicatorStatuses status) {

    }

    private void sendRequest() {


        Client client = ClientBuilder.newClient();

        WebTarget resource = client.target("https://google.com");

        var request = resource.request();
        request.accept(MediaType.APPLICATION_JSON);

        Response response = request.get();

        if (response.getStatusInfo().getFamily() == Response.Status.Family.SUCCESSFUL) {
            System.out.println("Success! " + response.getStatus());
        } else {
            System.out.println("ERROR! " + response.getStatus());
        }
        System.out.println(response.getEntity());
    }

    public static void main(String[] args) {
        new IndicatorController().sendRequest();
    }
}

package org.home.notifier.indicator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.home.notifier.common.MeetingState;
import org.home.notifier.common.PropertiesLoader;
import org.home.notifier.indicator.entity.Indicator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import static java.time.temporal.ChronoUnit.SECONDS;

public class IndicatorClient {

    private final String indicatorUrl;
    private final int timeOut;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public IndicatorClient() {
        var applicationProperties = PropertiesLoader.getProperties();
        this.indicatorUrl = applicationProperties.getProperty("application.protocol")
                    + "://" + applicationProperties.getProperty("indicator.host")
                    + ":" + applicationProperties.getProperty("indicator.port");
        this.timeOut = Integer.parseInt(applicationProperties.getProperty("request.timeout"));
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public CompletableFuture<MeetingState> getIndicatorState() {

        var request = HttpRequest.newBuilder()
                .uri(URI.create(indicatorUrl + "/getMeetingState"))
                .timeout(Duration.of(timeOut, SECONDS))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(value -> {
                    try {
                        var indicator = objectMapper.readValue(value, Indicator.class);
                        return MeetingState.valueOf(indicator.getMeetingState());
                    } catch (JsonProcessingException e) {
                        // ToDo Handle it
                        throw new RuntimeException("Bad response: " + value);
                    }
                });
    }

    public CompletableFuture<Integer> setIndicatorState(MeetingState meetingState) throws JsonProcessingException {

        var indicator = new Indicator(meetingState.getValue());
        var requestBody = objectMapper.writeValueAsString(indicator);

        var request = HttpRequest.newBuilder()
                .uri(URI.create(indicatorUrl + "/setMeetingState"))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode);
    }
}

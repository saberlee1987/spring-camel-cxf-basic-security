package com.saber.spring_rest_client.config;

import com.saber.spring_rest_client.routes.Headers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Slf4j
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        LocalDateTime startTime = LocalDateTime.now();
        String correlation = getCorrelation(request.getHeaders());
        traceRequest(request, body, startTime,correlation);

        ClientHttpResponse response = execution.execute(request, body);

        LocalDateTime endTime = LocalDateTime.now();
        long duration = ChronoUnit.MILLIS.between(startTime, endTime);
        traceResponse(response, startTime, endTime, duration,correlation);
        return response;
    }

    private void traceRequest(HttpRequest request, byte[] body, LocalDateTime startDateTime,String correlation) throws IOException {
        log.info("===========================request begin================================================");
        log.info("Request for correlation {} , startTime ====>  {}",correlation, startDateTime);
        log.info("Request for correlation {} to URI ==> {}",correlation, request.getURI().toString());
        log.info("Request for correlation =====>  {}", correlation);
        log.info("Request  Method  ====>  {}", request.getMethod());
        log.info("Request Headers ====>  {}", request.getHeaders());
        log.debug("Request for correlation {} , startTime {} , body ===>  {}",correlation,startDateTime, new String(body, StandardCharsets.UTF_8));
        log.info("==========================request end================================================");
    }

    private void traceResponse(ClientHttpResponse response, LocalDateTime startTime, LocalDateTime endTime, long duration,String correlation) throws IOException {
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8));
        String line = bufferedReader.readLine();
        while (line != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
            line = bufferedReader.readLine();
        }
        log.info("============================response begin==========================================");
        log.info("Response for correlation {} , StatusCode  ===> {}",correlation, response.getStatusCode());
        log.info("Response for correlation {} , StatusText  ===> {}",correlation, response.getStatusText());
        log.info("Response Headers  ===>  {}", response.getHeaders());
        log.info("correlation =====>  {}", correlation);
        log.info("Response for correlation {} ===>  body: {}",correlation, inputStringBuilder.toString());
        log.info("Response for correlation {} startTime {} , endTime {} , duration {} ms",correlation, startTime, endTime, duration);
        log.info("=======================response end=================================================");
    }

    private String getCorrelation(HttpHeaders headers) {
        String correlation = "";
        List<String> correlations = headers.get(Headers.correlation);
        if (correlations == null || correlations.isEmpty()) {
            correlation = UUID.randomUUID().toString();
        }else{
            correlation = correlations.get(0);
        }
        return correlation;
    }
}

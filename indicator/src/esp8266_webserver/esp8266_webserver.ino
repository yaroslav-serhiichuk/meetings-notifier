#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <ESP8266WebServer.h>
#include <ESP8266mDNS.h>
#include <ArduinoJson.h>

const char* SSID = "XXXXX";
const char* PASSWORD = "XXXXX";
const int BODY_SIZE = JSON_OBJECT_SIZE(2);

MDNSResponder mdns;
ESP8266WebServer server(80);
StaticJsonDocument<BODY_SIZE> body;

int gpio0_pin = 0;
int gpio2_pin = 2;

void sendErrorMessage(int httpCode, const char *errorMessage) {
  body.clear();
  body["error"] = errorMessage;
  String response = body.as<String>();
  Serial.println("Sending response: " + response);
  server.send(httpCode, "application/json", response);
  body.clear();
}

void setup(void){

  pinMode(gpio0_pin, OUTPUT);
  digitalWrite(gpio0_pin, LOW);
  pinMode(gpio2_pin, OUTPUT);
  digitalWrite(gpio2_pin, LOW);

  delay(1000);
  Serial.begin(115200);
  WiFi.begin(SSID, PASSWORD);
  Serial.println("");

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.print("Connected to ");
  Serial.println(SSID);
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());

  if (mdns.begin("esp8266", WiFi.localIP())) {
    Serial.println("MDNS responder started");
  }

  server.on("/healthCheck", [](){
    if (server.method() == HTTP_GET) {
      body["indicatorHealth"] = "OK";
      String response = body.as<String>();
      Serial.println("Sending response: " + response);
      server.send(200, "application/json", response);
      body.clear();
    } else {
      sendErrorMessage(405, "Method not allowed");
    }
  });

  server.on("/getMeetingState", [](){
    if (server.method() == HTTP_GET) {
      
      int red = digitalRead(gpio0_pin);
      int green = digitalRead(gpio2_pin);
      int state;
      if (red == 1 && green == 0) {
        state = 1;
      } else if (red == 0 && green == 1) {
        state = 2;
      } else if (red == 0 && green == 0) {
        state = 3;
      } else {
        state = -1;
      }
      body["meetingState"] = state;
      String response = body.as<String>();
      Serial.println("Sending response: " + response);
      server.send(200, "application/json", response);
      body.clear();
    } else {
      sendErrorMessage(405, "Method not allowed");
    }
  });

  server.on("/setMeetingState", [](){
    if (server.method() == HTTP_PUT) {
      DeserializationError err = deserializeJson(body, server.arg("plain"));
      if (err) {
        sendErrorMessage(400, "Fail to process request body");
      } else {
        int meetingState = body["meetingState"].as<int>();
        switch(meetingState) {
          case 1:
            digitalWrite(gpio0_pin, HIGH);
            digitalWrite(gpio2_pin, LOW);
            Serial.println("Meeting state set to 1 \"DO NOT DISTURB\" ");
            server.send(202);
            body.clear();
            break;
          case 2:
            digitalWrite(gpio0_pin, LOW);
            digitalWrite(gpio2_pin, HIGH);
            Serial.println("Meeting state set to 2 \"AVAILABLE\" ");
            server.send(202);
            body.clear();
            break;
          case 3:
            digitalWrite(gpio0_pin, LOW);
            digitalWrite(gpio2_pin, LOW);
            Serial.println("Meeting state set to 3 \"OFF\" ");
            server.send(202);
            body.clear();
            break;
          default:
            sendErrorMessage(400, "Incorrect meeting state value. Possible values: 1, 2, 3");
            break;
        } 
      }
    } else {
      sendErrorMessage(405, "Method not allowed");
    }
  });

  server.begin();
  Serial.println("HTTP server started");
}

void loop(void){
  server.handleClient();
}

#include <ESP8266WiFi.h>

#define WIFI_SSID "La Kien Vinh"
#define WIFI_PASSWORD "stdio_vn"

void setup() {
  Serial.begin(9600);

  // connect to wifi.
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Connecting");
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(100);
  }
  Serial.println();
  Serial.print("Connected: ");
  Serial.println(WiFi.localIP());
}

void loop() {

}

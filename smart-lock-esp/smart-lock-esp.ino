#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include <WiFiClient.h>
#include <Keypad.h>
#include <FirebaseArduino.h>

#define WIFI_SSID "SSID"
#define WIFI_PASSWORD "stdio_vn"

#define FIREBASE_HOST "smart-lock-70150.firebaseio.com"
#define FIREBASE_AUTH ""

#define DEVICE_UNIQUE_ID "eoBEboSRFfU:APA91bEueuJj58k1SmRMJ3JPC0SW059wfNBPMo55e8nFViIVVWCmgzoov1zgJZi4E9Qt6oe_rvCU1QebLCZv3bYBU1XnTSEcxIVIP43eWYsNEtLxpaTF4wolJtvp11Qn5rjsz7H-0l6r"
#define FCM_KEY "AAAAFcYf74U:APA91bGOgaiDjXR5iAc-YdSDu3-4jMfY5WClIhG5V7BoDoL2CQZ4dqTxU399FNRQBJRB9zi0kFzmDGGeGp4nQxjroKTdr_Pju7bzDPXi0cRrmvwt2hbAovrf48x_0cY_5lgrACDEKa6jFRF7bBc9Kx6Ot-we1pA_ew"
#define FCM_SERVER "fcm.googleapis.com"

const int NTP_PACKET_SIZE = 48;
byte packetBuffer[NTP_PACKET_SIZE];
unsigned long unixTimestamp = 0;
WiFiUDP udp;
WiFiClient client;

//Set for matrix key 4x4
#define ROWS 4
#define COLUMNS 4

char keys[ROWS][COLUMNS] = {
                              {'1', '2', '3', 'A'},
                              {'4', '5', '6', 'B'},
                              {'7', '8', '9', 'C'},
                              {'*', '0', '#', 'D'},
                           };
byte rowPins[ROWS] = {5, 4, 0, 2}; 
byte columnPins[COLUMNS] = {14, 12, 13, 15};
Keypad keypad = Keypad(makeKeymap(keys), rowPins, columnPins, ROWS, COLUMNS);

bool pinInputing = false;
String inputPin = "";

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

  syncGlobalTime();

  // Init Firebase
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);
}

void loop() {
  char temp = keypad.getKey();
  if ((int)keypad.getState() ==  PRESSED) 
  {
    if (temp == '*')
    {
      Serial.println("Begin input");
      pinInputing = true;
      inputPin = "";
    }
    else if (pinInputing && temp >= '0' && temp <= '9')
    { 
      inputPin += temp;
      Serial.println(inputPin);
    }
    else if (pinInputing && temp == '#')
    {
      Serial.println("End input");
      Serial.println(inputPin);
      
      String doorPin = Firebase.getString("system/door_pin");
      Serial.println("-----");
      Serial.println(inputPin);
      Serial.println(doorPin);
      Serial.println("-----");

      StaticJsonBuffer<256> jsonBuffer;
      JsonObject& data = jsonBuffer.createObject();

      String message = "Door open: ";
      if (doorPin == inputPin)
      {
        Serial.println("CORRECT");
        data["status"] = "success";      
      }
      else
      {
        Serial.println("INCORRECT");
        data["status"] = "failure";
      }
      data["timestamp"] = (String) (unixTimestamp + millis() / 1000);
      Firebase.push("system/tracking", data);
      sendPushNotification(data["timestamp"]);
      pinInputing = false;
    }
  }
}

void syncGlobalTime()
{
  unsigned int localPort = 2390;
  IPAddress timeServerIP;
  const char* ntpServerName = "time.nist.gov";
  
  WiFiClient client;

  Serial.println("Starting UDP");
  udp.begin(localPort);
  Serial.print("Local port: ");
  Serial.println(udp.localPort());
  
  WiFi.hostByName(ntpServerName, timeServerIP); 

  sendNTPpacket(timeServerIP);
  delay(1000);  
  int cb = udp.parsePacket();
  while(!cb)
  {
    Serial.println("No packet yet. Retry!");
    sendNTPpacket(timeServerIP);
    delay(1000);
    cb = udp.parsePacket();
  }
  
  Serial.print("Packet received, length=");
  Serial.println(cb);  
  udp.read(packetBuffer, NTP_PACKET_SIZE);

  //the timestamp starts at byte 40 of the received packet and is four bytes,
  // or two words, long. First, esxtract the two words:
  unsigned long highWord = word(packetBuffer[40], packetBuffer[41]);
  unsigned long lowWord = word(packetBuffer[42], packetBuffer[43]);
  // combine the four bytes (two words) into a long integer
  // this is NTP time (seconds since Jan 1 1900):
  unsigned long secsSince1900 = highWord << 16 | lowWord;
  Serial.print("Seconds since Jan 1 1900 = " );
  Serial.println(secsSince1900);

  // now convert NTP time into everyday time:
  Serial.print("Unix time = ");
  // Unix time starts on Jan 1 1970. In seconds, that's 2208988800:
  const unsigned long seventyYears = 2208988800UL;
  // subtract seventy years:
  unsigned long epoch = secsSince1900 - seventyYears;
  // print Unix time:
  Serial.println(epoch);
  unixTimestamp = epoch;

  // print the hour, minute and second:
  Serial.print("The UTC time is ");       // UTC is the time at Greenwich Meridian (GMT)
  Serial.print((epoch  % 86400L) / 3600); // print the hour (86400 equals secs per day)
  Serial.print(':');
  if ( ((epoch % 3600) / 60) < 10 ) {
    // In the first 10 minutes of each hour, we'll want a leading '0'
    Serial.print('0');
  }
  Serial.print((epoch  % 3600) / 60); // print the minute (3600 equals secs per minute)
  Serial.print(':');
  if ( (epoch % 60) < 10 ) {
    // In the first 10 seconds of each minute, we'll want a leading '0'
    Serial.print('0');
  }
  Serial.println(epoch % 60); // print the second
}

// send an NTP request to the time server at the given address
unsigned long sendNTPpacket(IPAddress& address)
{
  Serial.println("Sending NTP packet...");
  // set all bytes in the buffer to 0
  memset(packetBuffer, 0, NTP_PACKET_SIZE);
  // Initialize values needed to form NTP request
  // (see URL above for details on the packets)
  packetBuffer[0] = 0b11100011;   // LI, Version, Mode
  packetBuffer[1] = 0;     // Stratum, or type of clock
  packetBuffer[2] = 6;     // Polling Interval
  packetBuffer[3] = 0xEC;  // Peer Clock Precision
  // 8 bytes of zero for Root Delay & Root Dispersion
  packetBuffer[12]  = 49;
  packetBuffer[13]  = 0x4E;
  packetBuffer[14]  = 49;
  packetBuffer[15]  = 52;

  // all NTP fields have been given values, now
  // you can send a packet requesting a timestamp:
  udp.beginPacket(address, 123); //NTP requests are to port 123
  udp.write(packetBuffer, NTP_PACKET_SIZE);
  udp.endPacket();
}

void sendPushNotification(const String& message) {
  String data = "{" ;
  data = data + "\"to\": \"" + DEVICE_UNIQUE_ID + "\"," ;
  data = data + "\"notification\": {" ;
  data = data + "\"body\": \"" + message + "\"," ;
  data = data + "\"title\" : \"Alarm\" " ;
  data = data + "} }" ;

  Serial.println("Send data...");
  if (client.connect(FCM_SERVER, 80)) {
    Serial.println("Connected to the server..");
    client.println("POST /fcm/send HTTP/1.1");
    client.println(((String)"Authorization: key=") + FCM_KEY);
    client.println("Content-Type: application/json");
    client.println("Host: fcm.googleapis.com");
    client.print("Content-Length: ");
    client.println(data.length());
    client.print("\n");
    client.print(data);
  }
  Serial.println("Data sent...Reading response..");
  while (client.available()) {
   char c = client.read();
   Serial.print(c);
  }
  Serial.println("Finished!");
  client.flush();
  client.stop();
}

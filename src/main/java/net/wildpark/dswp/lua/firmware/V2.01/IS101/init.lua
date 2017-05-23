--Device init.lua
connections={};
connections.wifiConnection=false;
connections.mqttClient=false;

device={};
device.alarm=false;
device.energySave=false;
device.name="";
device.battery=0;
device.reportTime=5000;
device.chipId=node.chipid();
device.sensors={};
device.modelType="IS101";
dofile('WiFi.lua') -- Create object of class WiFi with methods: init(),checkConnection();
dofile('Led.lua')-- Create object of class Led with methods: blink(countInInt),setConnectionsStatus(stateAsBoolean);
dofile('MQTT.lua') -- Create object of class MQTT with methods: sendMessage(),sendGreetings(),sendLog(logAsString),init();
dofile('' .. device.modelType .. '.lua') --Create object of class WPEcoDevice with methods: report();
dofile('File.lua') -- Create object of class File with methods : parseCommand(jsonDataAsString),downloadFile(URLAsString,fileNameAsString,runAfterDownloadAsBoolean),loadSettings(),saveSettings();
dofile('Battery.lua') -- Create object of class Battery with methods: init(),measure();
File:loadSettings();
deviceReportTimer=tmr.create();
deviceReportTimer:register(device.reportTime,tmr.ALARM_AUTO,function() WPEcoDevice:report()  end);
deviceReportTimer:start();
WiFi:init();
WiFi:checkConnection();
MQTT:sendGreeting();
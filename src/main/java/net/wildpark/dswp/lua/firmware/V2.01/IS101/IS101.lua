WPEcoDevice={};
device.name="IS101";

device.sensors.temp=0;
device.sensors.humi=0;
dht11={}
dht11.pin=1;

function dht11Measure() 
	status, temp, humi, temp_dec, humi_dec = dht.read(dht11.pin);
	if status == dht.OK then
		device.sensors.temp=math.floor(temp);
		device.sensors.humi=math.floor(humi);		
	end	
end

function WPEcoDevice:report()
	deviceReportTimer:interval(device.reportTime);
	dht11Measure();
	Battery:measure();
	WiFi:checkConnection();
	MQTT:sendMessage();
	collectgarbage();
end
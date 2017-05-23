WPEcoDevice={};


device.sensors.nowCapacity=0;
hcsr04={};
hcsr04.trigger = 1;
hcsr04.echo = 2;

device.sensors.temp=0;
device.sensors.humi=0;
dht11={}
dht11.pin=7;

function hcsr04Measure()
	gpio.mode(hcsr04.trigger,gpio.OUTPUT);
	gpio.mode(hcsr04.echo,gpio.INPUT);
	gpio.write(hcsr04.trigger, gpio.LOW);
	dist = 0;
	for variable = 0, 200, 1 do
		dist = dist + gpio.read(hcsr04.echo);
	end  
	gpio.write(hcsr04.trigger, gpio.HIGH);
	device.sensors.nowCapacity=dist*294/100;
end

function dht11Measure() 
	status, temp, humi, temp_dec, humi_dec = dht.read(dht11.pin);
	if status == dht.OK then
		device.sensors.temp=math.floor(temp);
		device.sensors.humi=math.floor(humi);		
	end	
end

function WPEcoDevice:report()
	hcsr04Measure();
	dht11Measure();
	Battery:measure();
	WiFi:checkConnection();
	MQTT:sendMessage();
	collectgarbage();
end
WPEcoDevice={};
device.name="ARC101";

WPEcoDevice.switch1=1;
WPEcoDevice.switch2=2;
WPEcoDevice.relay1=7;
WPEcoDevice.relay2=6;

device.sensors.timer1=100;
device.sensors.timer2=3000;
device.sensors.swich1State=false;
device.sensors.swich2State=false;
device.sensors.relay1State=false;
device.sensors.relay2State=false;
device.sensors.passes=0;

alarm=tmr.create();
alarm:register(600000,tmr.ALARM_AUTO,function()checkAlarm()end);
alarm:start();

pinReader=tmr.create();
pinReader:register(100,tmr.ALARM_AUTO,function()readSwitches()end);
pinReader:start();

gpio.mode(WPEcoDevice.switch1,gpio.INPUT);
gpio.mode(WPEcoDevice.switch2,gpio.INPUT);
gpio.mode(WPEcoDevice.relay1,gpio.OUTPUT);
gpio.write(WPEcoDevice.relay1,gpio.LOW);
gpio.mode(WPEcoDevice.relay2,gpio.OUTPUT);
gpio.write(WPEcoDevice.relay2,gpio.LOW);

relay1Delay=tmr.create();
relay1Delay:register(device.sensors.timer1,tmr.ALARM_SINGLE,function()relay1Enable()end);

relay2Delay=tmr.create();
relay2Delay:register(device.sensors.timer2,tmr.ALARM_SINGLE,function()relay2Enable()end);

function checkAlarm() 
	if device.alarm then 
		MQTT:sendMessage();
		relay1Enable();
		relay2Enable();
		MQTT:sendLog("Execute the emergency protocol");
		else 
		device.alarm=true;		
	end
	
end;

function readSwitches()
	
	
	if gpio.read(WPEcoDevice.switch1)~=device.sensors.swich1State then 
		device.sensors.swich1State=gpio.read(WPEcoDevice.switch1);
		local mess=nil
		if device.sensors.swich1State==gpio.LOW then 
			relay1Delay:start();
            else 
			device.sensors.swich1State=gpio.HIGH;
		end            
	end
	
	if gpio.read(WPEcoDevice.switch2)~=device.sensors.swich2State then 
		device.sensors.swich2State=gpio.read(WPEcoDevice.switch2);
		local mess=nil
		if device.sensors.swich2State==gpio.LOW then 
			relay2Delay:start();
            else 
			device.sensors.swich2State=gpio.HIGH;
		end            
	end
	
end

function relay1Enable()
	gpio.write(WPEcoDevice.relay1,gpio.HIGH);
	tmr.alarm(4,1000,tmr.ALARM_SINGLE,
		function() 
			gpio.write(WPEcoDevice.relay1,gpio.LOW);
			device.sensors.passes=sensors.passes + 1;
			device.alarm=false;
			MQTT:sendMessage();
		end)        
end

function relay2Enable()
	gpio.write(WPEcoDevice.relay2,gpio.HIGH);
    tmr.alarm(4,1000,tmr.ALARM_SINGLE,
        function() 
			gpio.write(WPEcoDevice.relay2,gpio.LOW);
			device.sensors.passes=sensors.passes + 1;
			device.alarm=false;
			MQTT:sendMessage();
		end)        
end

function WPEcoDevice:report()
	WiFi:checkConnection();
	MQTT:sendMessage();
	collectgarbage();
end


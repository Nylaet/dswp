WPEcoDevice={};

WPEcoDevice.relay1Pin=0;
WPEcoDevice.relay2Pin=6;
WPEcoDevice.relay3Pin=12;
WPEcoDevice.relay4Pin=7;
WPEcoDevice.relay5Pin=2;

WPEcoDevice.pwmPin=1;
WPEcoDevice.lastPWM=0;

device.modelType="BRC101";
device.sensors.relay1=false;
device.sensors.relay2=false;
device.sensors.relay3=false;
device.sensors.relay4=false;
device.sensors.relay5=false;
device.sensors.outPower=0; -- value between 0%-100%
--init relays
gpio.mode(WPEcoDevice.relay1Pin,gpio.OUTPUT);
gpio.mode(WPEcoDevice.relay2Pin,gpio.OUTPUT);
gpio.mode(WPEcoDevice.relay3Pin,gpio.OUTPUT);
gpio.mode(WPEcoDevice.relay4Pin,gpio.OUTPUT);
gpio.mode(WPEcoDevice.relay5Pin,gpio.OUTPUT);

gpio.write(WPEcoDevice.relay1Pin,gpio.LOW);
gpio.write(WPEcoDevice.relay2Pin,gpio.LOW);
gpio.write(WPEcoDevice.relay3Pin,gpio.LOW);
gpio.write(WPEcoDevice.relay4Pin,gpio.LOW);
gpio.write(WPEcoDevice.relay5Pin,gpio.LOW);
--
--init outputPower
pwm.setup(WPEcoDevice.pwmPin,100,0);
pwm.start(WPEcoDevice.pwmPin);
--
function WPEcoDevice:setStateForRelays()
	if (device.sensors.relay1==true) then gpio.write(WPEcoDevice.relay1Pin,gpio.HIGH) else gpio.write(WPEcoDevice.relay1Pin,gpio.LOW) end;
	if (device.sensors.relay2==true) then gpio.write(WPEcoDevice.relay2Pin,gpio.HIGH) else gpio.write(WPEcoDevice.relay2Pin,gpio.LOW) end;
	if (device.sensors.relay3==true) then gpio.write(WPEcoDevice.relay3Pin,gpio.HIGH) else gpio.write(WPEcoDevice.relay3Pin,gpio.LOW) end;
	if (device.sensors.relay4==true) then gpio.write(WPEcoDevice.relay4Pin,gpio.HIGH) else gpio.write(WPEcoDevice.relay4Pin,gpio.LOW) end;
	if (device.sensors.relay5==true) then gpio.write(WPEcoDevice.relay5Pin,gpio.HIGH) else gpio.write(WPEcoDevice.relay5Pin,gpio.LOW) end;
end

function WPEcoDevice:setPWMOutputDuty()
	local power=device.sensors.outPower;
	if power ~= WPEcoDevice.lastPWM then
		pwm.setduty(WPEcoDevice.pwmPin,power);
		WPEcoDevice.lastPWM=power;
	end
end

function WPEcoDevice:report()
	Battery:measure();
	WiFi:checkConnection();
	MQTT:sendMessage();
	collectgarbage();
end

checkState=tmr.create();
checkState:register(1000,tmr.ALARM_AUTO,function () 
	WPEcoDevice:setStateForRelays();
	WPEcoDevice:setPWMOutputDuty();
end)
checkState:start();
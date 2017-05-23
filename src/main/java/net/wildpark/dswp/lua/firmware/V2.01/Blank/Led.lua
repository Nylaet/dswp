Led={};
Led.bussy=false;
Led.state=1;
Led.count=0;
Led.need=0;
Led.StatusPin=5;
Led.blinkPause=10;
timerOn=tmr.create();
timerOff=tmr.create();
timerOn:register(Led.blinkPause,tmr.ALARM_AUTO,function()LedOn()end);
timerOff:register(Led.blinkPause,tmr.ALARM_SEMI,function()LedOff() end);
gpio.mode(Led.StatusPin,gpio.OUTPUT);
gpio.write(Led.StatusPin,1);

--LEDIndication
function Led:blink(needInc)
    Led.need=needInc;
	if Led.bussy then 
		return;
		else 
		Led.bussy=true;
		end
	gpio.mode(4,gpio.OUTPUT);
	gpio.write(4,1);
	timerOn:start();
	Led.bussy=false;
end

function LedOn() 
	if Led.count<Led.need and Led.need>0 then 
		gpio.write(4,0);
		timerOff:start();
		else    
		Led.count=0;
		Led.need=0;
		timerOn:stop();
		timerOff:stop();
		collectgarbage();
	end
	
end

function LedOff() 
	gpio.write(4,1);
	Led.count=Led.count+1;
end

function Led:setConnectionStatus(status) 
	if status then 
		gpio.write(Led.StatusPin,0);
	else 
		gpio.write(Led.StatusPin,1);
	end
end

function Led:identify()
	gpio.write(Led.StatusPin,1);
	tmr.delay(1000);
	gpio.write(Led.StatusPin,0);
end
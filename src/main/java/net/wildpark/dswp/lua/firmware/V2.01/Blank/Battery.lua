Battery={};
Battery.vdd33=false;
Battery.averageSize=100;
Battery.maxValue=0;
Battery.smootedValue=0;-- Default value of full charge battery

function Battery:initAsVDD33()
	if adc.force_init_mode(adc.INIT_VDD33) then 
		Battery.vdd33=true;
		node.reboot();
	end;
end

function Battery:init()
	if adc.force_init_mode(adc.INIT_ADC) then 
		Battery.vdd33=false;
		node.reboot();
	end;
end

function Battery:getClearMeasure()
	if Battery.vdd33 then 
		return adc.readvdd();
	else 
		return adc.read(0);
	end
end

function Battery:measure() 
	local nowValue=adc.read(0);
	if(Battery.maxValue==0) then 
		local batteryFile=file.open("bf.dat","r");
		if batteryFile then 
			Battery.maxValue= tonumber (batteryFile:readline());			
			batteryFile:close();
		end;
	end;
	if(nowValue>Battery.maxValue) then 
		local batteryFile=file.open("bf.dat","w+");
		batteryFile:write(nowValue);
		batteryFile:close();
		Battery.maxValue=nowValue;
	end;
	collectgarbage();
	device.battery=nowValue*100/Battery.maxValue;
	end

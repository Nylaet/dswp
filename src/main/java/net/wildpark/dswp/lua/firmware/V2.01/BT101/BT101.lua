WPEcoDevice={};
device.name="BT101";
--DS18B20
device.sensors.temp1=0;
ds18b20.temp1={};
ds18b20.temp1.Pin=5;
ds18b20.temp1.Addr=nil;

device.sensors.temp2=0;
ds18b20.temp2={};
ds18b20.temp2.Pin=5;
ds18b20.temp2.Addr=nil;

device.sensors.temp3=0;
ds18b20.temp3={};
ds18b20.temp3.Pin=6;
ds18b20.temp3.Addr=nil;

device.sensors.temp4={};
ds18b20.temp4={};
ds18b20.temp4.Pin=6;
ds18b20.temp4.Addr=nil;
-- MAX6675
max6675={}
max6675.miso=12;
max6675.clk=7;

device.sensors.temp5=0;
max6675.temp5={};
max6675.temp5.port=1;

device.sensors.temp6=0;
max6675.temp6={};
max6675.temp6.port=2;

function WPEcoDevice:ds18b20() 
	
	local function readAddr(pin,addr) 
		if addr==nil then return nil end
		ow.reset(pin);
		ow.select(pin, addr);
		ow.write(pin, 0x44, 1);
		tmr.delay(1000);
		present = ow.reset(pin);
		ow.select(pin, addr);
		ow.write(pin,0xBE,1);
		data = nil;
		data = string.char(ow.read(pin));
		for i = 1, 8 do
			data = data .. string.char(ow.read(pin));
		end
		crc = ow.crc8(string.sub(data,1,8));
		if crc == data:byte(9) then
			t = (data:byte(1) + data:byte(2) * 256) * 625;
			t1 = t / 10000;	
			return t1;
		end             
	end
	
	ow.setup(ds18b20.temp1.Pin);
	local addr1=nil;
	local addr2=nil;
	ow.reset_search(ds18b20.temp1.Pin);
	addr1=ow.search(ds18b20.temp1.Pin);
	addr2=ow.search(ds18b20.temp1.Pin);
	if addr1 then 
		ds18b20.temp1.Addr=addr1:byte(8) 
		else 
		ds18b20.temp1.Addr=nil;
	end
	if addr2 then 
		ds18b20.temp2.Addr=addr2:byte(8)
		else
		ds18b20.temp2.Addr=nil;
	end
	device.sensors.temp1=readAddr(ds18b20.temp1.Pin,addr1);
	device.sensors.temp2=readAddr(ds18b20.temp1.Pin,addr2);
	
	ow.setup(ds18b20.temp3.Pin);
	addr1=nil;
	addr2=nil;
	ow.reset_search(ds18b20.temp3.Pin);
	addr1=ow.search(ds18b20.temp3.Pin);
	addr2=ow.search(ds18b20.temp3.Pin);
	if addr1 then 
		ds18b20.temp1.Addr=addr1:byte(8);
		else 
		ds18b20.temp3.Addr=nil;
	end
	
	if addr2 then 
		ds18b20.temp2.Addr=addr2:byte(8);
		else
		ds18b20.temp4.Addr=nil;
	end
	device.sensors.temp3=readAddr(ds18b20.temp3.Pin,addr1);
	device.sensors.temp4=readAddr(ds18b20.temp3.Pin,addr2);	
end

function WPEcoDevice:max6675()
	-- Config
	local CS1 = sensors.max6675.temp5.port;            
	local CS2 = sensors.max6675.temp6.port;            
	local i = 0;
	local result = 0;
	
	-- Pin Initialization
	gpio.mode(max6675.clk, gpio.OUTPUT);
	gpio.mode(max6675.miso, gpio.INPUT);
	
	-- Function to read SPI
	function readSPI(CS)
		gpio.mode(CS, gpio.OUTPUT);
		gpio.write(CS,gpio.HIGH);
		gpio.write(CS, gpio.LOW);     -->Activate the chip 
		tmr.delay(1);                 -->1us Delay
		
		gpio.write(max6675.clk, gpio.HIGH);    -->First bit is dummy, ignore it(refer MAX6675 datasheet)
		tmr.delay(2);
		gpio.write(max6675.clk, gpio.LOW);
		tmr.delay(2);
		
		result = 0;
		
		for i=15,1,-1 do 
			result = bit.lshift(result, 1);
			result = bit.bor(result ,(bit.band(gpio.read(max6675.miso),0x01)));
			gpio.write(max6675.clk, gpio.HIGH);    
			tmr.delay(2);
			gpio.write(max6675.clk, gpio.LOW);
			tmr.delay(2);
		end
		if(bit.isset(result,2)) then                      -- refer MAX6675 Datasheet
			gpio.write(CS, gpio.HIGH);
			do return end
		end        
		gpio.write(CS, gpio.HIGH);
		x=bit.rshift(result,3);
		x=x/4;		
		return x;		
	end
	device.sensors.temp5=readSPI(max6675.temp6.port);
	device.sensors.temp6=readSPI(max6675.temp6.port);
	
end

function WPEcoDevice:report()
	WPEcoDevice:max6675();
	WPEcoDevice:ds18b20();
	Battery:measure();
	WiFi:checkConnection();
	MQTT:sendMessage();
	collectgarbage();
end

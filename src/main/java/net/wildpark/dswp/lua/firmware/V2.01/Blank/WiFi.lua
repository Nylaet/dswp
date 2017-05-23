WiFi={};

function WiFi:init() 
	wifi.setmode(wifi.STATION);
	wifi.sta.sleeptype(wifi.MODEM_SLEEP);
end

function WiFi:checkConnection()	
	
	local station1config={};
		station1config.ssid="WPEco_AP_RSB";
		station1config.pwd="WPStation";
		station1config.auto=false;
	
	local station2config={};
		station2config.ssid="WPEco_AP_RT";
		station2config.pwd="WPStation";
		station1config.auto=false;
	
	local  config=1;
	
	function getNextConfig() 
        if config==1 then 
            config=2;
            return station2config;
			else
            config=1;
            return station1config;
		end
	end
	
	Led:blink(1);
	if wifi.sta.status()~=5 and wifi.sta.status()~=1 then
        Led:setConnectionStatus(false);
		connections.wifiConnection=false;
        connections.mqttClient=false;
        wifi.sta.config(getNextConfig());
        wifi.sta.connect();
	elseif wifi.sta.status()==5 and not connections.wifiConnection then
        Led:setConnectionStatus(true);
		print("Connected");
        connections.wifiConnection=true;
        MQTT:init();
		MQTT:sendGreeting();
	end
    collectgarbage();
end
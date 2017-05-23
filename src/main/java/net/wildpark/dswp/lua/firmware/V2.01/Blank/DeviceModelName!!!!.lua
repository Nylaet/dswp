WPEcoDevice={};


function WPEcoDevice:report()
	Battery:measure();
	WiFi:checkConnection();
	MQTT:sendMessage();
	collectgarbage();
end


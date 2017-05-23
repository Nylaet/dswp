MQTT={}
-- MQTTMessage with data
function MQTT:sendMessage()
	WiFi:checkConnection();
	if(device.energySave) then MQTT:WiFiOn(); end
	if connections.mqttClient then   
		Led:blink(3);
		local message=cjson.encode(device);
		m:publish("/" .. node.chipid() .. "/data/",message,0,0,function(client) print("Successfully send")end)
	end
	if(device.energySave) then MQTT:WiFiOff(); end
end
--

--MQTTMessage log
function MQTT:sendLog(message)
	WiFi:checkConnection();
	if connections.mqttClient then   
		Led:blink(3);
		m:publish("/" .. node.chipid() .. "/log/",message,0,0,
			function(client) 
				print("send:",message);
			end);
	end
end
--
--MQTTGreetingMessage
function MQTT:sendGreeting()
	WiFi:checkConnection();
    if connections.mqttClient then
	Led:blink(3);
            m:publish("/greetings/","TYPE:" .. device.modelType .. ";CHIPID:" .. node.chipid(),0,0,
				function(client) 
					print("Greeding send");
				end)
        end
    end
--
function MQTT:init()
    if connections.wifiConnection and not connections.mqttClient then  
        
		m=mqtt.Client("/" .. node.chipid() ,120,"panker","156456851")
		m:lwt( "/" .. node.chipid().."/log/","Device lost connection",0,0);
        m:on("message",
            function(client,topic,data) -- do if get new data
                File:parseCommands(data);
            end)
    
        m:connect("80.252.241.65",1883,0,
            function(client) -- do if connected
                m:subscribe("/" .. node.chipid() .. "/commands/",0);
                connections.mqttClient=true;
                Led:setConnectionStatus(true);
				MQTT:sendGreeting();
            end,
            function(client,reason) --do if not connected
                print("Not connection whenn",reason);
            end)
            
        
    end
	
	if(device.energySave) then MQTT:WiFiOff(); end
   
end

function MQTT:WiFiOff()
	if wifi.getmode() == wifi.STATION then 
		wifi.setmode(wifi.NULLMODE);
		print("Wifi is power off");
	end		
end

function MQTT:WiFiOn() 
	if wifi.getmode() == wifi.NULLMODE then 
		wifi.setmode(wifi.STATION);
		print("Wifi is power on");
		WiFi:checkConnection();
	end	
end
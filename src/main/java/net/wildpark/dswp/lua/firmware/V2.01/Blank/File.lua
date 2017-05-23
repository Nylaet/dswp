File={};

function File:parseCommands(data) 
	local command=cjson.decode(data);
	print(command);
	if command["command"]=="report" then
		MQTT:sendMessage();
	elseif command["command"]=="reset" then
		File:saveSettings();
		MQTT:sendLog("The device will be rebooted");
		node.restart();
	elseif command["command"]=="downloadFile" then
		local url,fileName,runAfterDownload = nil,nil, false;
		local params=command["params"];
		File:downloadFile(params["url"],params["fileName"],params["runAfterDownload"])
	elseif command["command"]=="setSensors" then
		if command["params"] then 
		device=command["params"];		
		File:saveSettings();
		node.restart();
		end
	elseif command["command"]=="identify" then
		gpio.write(Led.StatusPin,0);
		
	end	
end

function File:downloadFile (url,fileName,runAfterDownload)
	http.get(url,nil,function(code,data)
		local failed=false
		if (code ~= 200) then
			failed=true;
			print("Failed");
			File:sendLog("File download failed")
			collectgarbage();
			return;
		end
		local fd = file.open(fileName,"w+");
			  fd:write(data);
			  fd:close();
		collectgarbage();
		print("Successfully downloaded");
		File:sendLog("Successfully downloaded");
		
		if (runAfterDownload) then	
			dofile(fileName); 
		end
	end)
end

function File:loadSettings()
    local settingsFile=file.open("settings.cfg","r");
    if settingsFile then 
		if pcall(function()
			local dataReaded=settingsFile:read();
			device=cjson.decode(dataReaded);
			t = cjson.decode(dataReaded);
			for k,v in pairs(t) do print(k,v) end;
		end) then 
		MQTT:sendLog("I can not read the file. The file will be deleted");
		end		
        settingsFile:close();
	end
	collectgarbage();
end

function File:saveSettings()
    local settingsFile=file.open("settings.cfg","w+");
    if settingsFile then 
        local ok, json = pcall(cjson.encode, device);
		if ok then
			settingsFile:writeline(json);
			else
			print("failed to encode!");
		end
        settingsFile:close();
	end
    collectgarbage();
end

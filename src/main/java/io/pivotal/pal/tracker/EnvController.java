package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {
    private String port;
    private String memLimit;
    private String cfInstInd;
    private String cfInstAddr;
    public EnvController(
            @Value("${PORT:NOT SET}") String port,
            @Value("${MEMORY_LIMIT:NOT SET}") String memLimit,
            @Value("${CF_INSTANCE_INDEX:NOT SET}") String cfInstInd,
            @Value("${CF_INSTANCE_ADDR:NOT SET}") String cfInstAddr){
        this.port = port;
        this.memLimit = memLimit;
        this.cfInstInd = cfInstInd;
        this.cfInstAddr = cfInstAddr;
    }
    @GetMapping("/env")
    public Map<String, String> getEnv(){
        Map<String, String> returnMap = new HashMap<String,String>();

        returnMap.put("PORT",port);
        returnMap.put("MEMORY_LIMIT",memLimit);
        returnMap.put("CF_INSTANCE_INDEX",cfInstInd);
        returnMap.put("CF_INSTANCE_ADDR",cfInstAddr);

        return returnMap;
    }
}

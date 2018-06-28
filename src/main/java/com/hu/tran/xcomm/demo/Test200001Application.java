package com.hu.tran.xcomm.demo;

import com.hu.tran.xcomm.core.PackMapper;
import com.hu.tran.xcomm.core.TargetMapper;
import com.hu.tran.xcomm.core.XCommService;
import lombok.extern.log4j.Log4j;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

@Log4j
public class Test200001Application {
    public static final String pack = "/pack";
    public static final String targetFile = "/TargetServer.xml";

    public static void main(String[] args) throws Exception{
        String packPath = URLDecoder.decode(Test10010Application.class.getResource(pack).getPath(),"utf-8");
        if(!PackMapper.init(packPath)){
            return;
        }
        String targetPath = URLDecoder.decode(Test10010Application.class.getResource(targetFile).getPath(),"utf-8");
        if(!TargetMapper.init(targetPath)){
            return;
        }
        Map<String,Object> sendMap = new HashMap<String, Object>();
        Map<String,Object> returnMap = new HashMap<String, Object>();
        sendMap.put("firstsysname","xfxd");
        sendMap.put("firstsysmemucode","cdcode");
        sendMap.put("firstsysmemuname","201801");
        sendMap.put("firstsysdate","201801");
        sendMap.put("firstsystime","1212");
        sendMap.put("firstsysseq","dwq123");
        sendMap.put("requesttrancode","CD12");
        sendMap.put("requestseq","123");
        sendMap.put("brno","123");
        sendMap.put("tellerno","123");
        sendMap.put("authtellerno","123");
        sendMap.put("reviewtellrno","123");
        sendMap.put("pageflag","4");
        sendMap.put("currpage","1");
        sendMap.put("pagenum","3");
        sendMap.put("smssendyn","Y");
        //私有域
        sendMap.put("custidnbr","420102199902123022");
        sendMap.put("custidtyp","0");
        sendMap.put("customerid","12");
        sendMap.put("customname","tom");
        sendMap.put("custphone","17219113882");
        sendMap.put("medipwd","123456");
        sendMap.put("acctlev","2");
        sendMap.put("relcardnbr","312421521312");
        sendMap.put("cardagreementtypcd","VCAD");
        sendMap.put("innerflag","OUTER");
        sendMap.put("mediavailyn","Y");
        sendMap.put("medistatcd","NACT");
        sendMap.put("mjaccttypcd","123");
        sendMap.put("miaccttypcd","2312");
        sendMap.put("fzdqmc","sz");
        sendMap.put("expiredate","2018-09-12");
        sendMap.put("ntwrkchkyn","Y");
        sendMap.put("chkanswercd","123");
        sendMap.put("chkanswerdesc","test");
        String result = XCommService.tran("200001",sendMap,returnMap);
        if(result.equals("0000")){              //通讯成功
            for(String str:returnMap.keySet()){
                System.out.println(str+": "+returnMap.get(str));
            }
        }
    }
}

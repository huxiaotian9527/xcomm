package com.hu.tran.xcomm.demo;

import com.hu.tran.xcomm.core.PackMapper;
import com.hu.tran.xcomm.core.TargetMapper;
import com.hu.tran.xcomm.core.XCommService;
import lombok.extern.log4j.Log4j;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hutiantian
 * @create 2018/6/21 11:29
 * @since 1.0.0
 */
@Log4j
public class Test10010Application {

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
        sendMap.put("Mac","0000000000001");
        sendMap.put("MsgId","2bc7f1cc-3757-4381-b9c8-5ba787de39aa");
        sendMap.put("SourceSysId","200510");
        sendMap.put("ConsumerId","200510");
        sendMap.put("ServiceCode","02002000005");
        sendMap.put("ServiceScene","02");
        sendMap.put("TranDate","20180508");
        sendMap.put("TranTime","104115");
        sendMap.put("TranTellerNo","1937");
        sendMap.put("TranSeqNo","3000501506240286ad09080063e7");
        sendMap.put("GlobalSeqNo","3000501506240286ad09080063e7");
        sendMap.put("AuthrTellerNo","1937");
        sendMap.put("IdntTp","101");
        sendMap.put("IdentNo","110108195607175419");
        sendMap.put("CstNm","李龙云");
        sendMap.put("QryRsn","02");
        sendMap.put("QryFmtCd","30");
        sendMap.put("QryTp","0");
        sendMap.put("QryPolcyCd","2");
        sendMap.put("RsltTp","2");
        sendMap.put("GtFlMth","2");
        sendMap.put("IntfId","zhengxin_QuryCustCreditReport");
        sendMap.put("UsrKey","jisfo238249823423jjijf");
        sendMap.put("IntVrNo","1");
        String result = XCommService.tran("10010",sendMap,returnMap);
        if(result.equals("0000")){              //通讯成功
            for(String str:returnMap.keySet()){
                System.out.println(str+": "+returnMap.get(str));
            }
        }
    }
}

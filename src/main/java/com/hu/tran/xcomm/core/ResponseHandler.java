package com.hu.tran.xcomm.core;

import com.hu.tran.xcomm.common.Field;
import com.hu.tran.xcomm.common.Pack;
import com.hu.tran.xcomm.log.LogUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接受报文后处理
 * @author hutiantian
 * @create 2018/6/22 17:44
 * @since 1.0.0
 */
public class ResponseHandler {

    /**
     * 将接受的xml映射至returnMap
     * @param msgId
     * @param pack
     * @param returnMap
     * @param packBaos
     */
    public static void handlerResponse(String msgId, Pack pack, Map<String,Object> returnMap, ByteArrayOutputStream packBaos) throws Exception{
        //将应答字节数组按编码转为document文档
        Document doc =  DocumentHelper.parseText(new String(packBaos.toByteArray(),pack.getEncoding()));
        Element root = doc.getRootElement();
        //遍历请求字段，
        for(int i=0;i<pack.getResponseList().size();i++){
            Element tempRoot = root;
            Field field = pack.getResponseList().get(i);
            String loop = field.getLoop();
            if(loop.equals("")){							//非循环域字段
                String value = getValueFromXml(tempRoot,field.getTag());
                returnMap.put(field.getName(), value);
            }else{											//循环域字段一次性处理完
                ArrayList<Field> fieldList = new ArrayList<Field>();
                List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
                for(Field field1:pack.getResponseList()){
                    if(loop.equals(field1.getLoop())){
                        fieldList.add(field1);
                    }
                }
                getLoopFromXml(fieldList,mapList,tempRoot);
                if(mapList.size()==0){					//xml循环域标签不符合规范，循环内标签默认给空
                    Map<String,String> map = new HashMap<String,String>();
                    for(Field field2:fieldList){
                        map.put(field2.getName(), "");
                    }
                    mapList.add(map);
                }
                returnMap.put(loop, mapList);
                i = i+fieldList.size()-1;
            }
        }
        if(msgId!=null){
            //记录发送的报文日志
            LogUtil.tracePack(msgId,pack.getPackCode(),1,packBaos);
        }
    }

    /**
     * 解析响应xml的普通标签
     * @param elem xml的root
     * @param tag 请求字段对应的tag
     * @return 该tag在xml中的value，未找到至赋空字符串
     */
    private static String getValueFromXml(Element elem,String tag){
        String value = "";
        String[] strAarry = tag.split("/");
        for(int i=1;i<strAarry.length;i++){
            if((elem = elem.element(strAarry[i]))==null){		//xml中没有此标签，默认空
                break;
            }
            if(i==strAarry.length-1){
                value = elem.getTextTrim();
            }
        }
        return value;
    }

    /**
     * 解析响应xml的循环标签
     * @param list 同一loop的标签集合
     * @param mapList 解析后的结果list
     * @param elem xml的root
     */
    private static void getLoopFromXml(ArrayList<Field> list,List<Map<String,String>> mapList,Element elem){
        String[] strAarry = list.get(0).getTag().split("/");
        //这里默认tag标签的倒数第二层为循环标签，且标签长度大于3
        for(int i=1;i<strAarry.length-2;i++){				//遍历到循环域标签的倒数第三位
            if((elem = elem.element(strAarry[i]))==null){
                break;
            }
            if(i==strAarry.length-3){
                List<Element> elemList = elem.elements(strAarry[i+1]); //拿到循环域
                if(elemList==null){
                    break;
                }
                for(Element ele:elemList){								//遍历循环域标签
                    Map<String,String> map = new HashMap<String,String>();
                    for(Field field:list){							//遍历循环域字段并赋值
                        Element e = ele;
                        String[] temp = field.getTag().split("/");
                        if((e = e.element(temp[temp.length-1]))==null){
                            map.put(field.getName(), "");
                        }else{
                            map.put(field.getName(), e.getTextTrim());
                        }
                    }
                    mapList.add(map);
                }
            }
        }
    }
}

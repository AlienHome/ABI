package com.sb.controller;

import org.apache.commons.io.IOUtils;
import org.tensorflow.Graph;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.util.ResourceUtils;
import org.apache.http.client.HttpClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import com.sb.util.ImageBase64Util;
import com.sb.util.HttpClientUtil;
import com.sb.util.https.HttpsClientV45Util;
import com.sb.util.https.HttpsTrustClientV45;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/api/ai")
public class AIController{
    static Logger logger = LoggerFactory.getLogger(AIController.class);
    @RequestMapping(value="pre", method=RequestMethod.POST) 
    public Float predictionTest(@RequestBody Map datas) throws Exception{

        float input = Float.parseFloat(datas.get("input").toString());
        // float[][] x = new float[1][1];
        // x[0] = new float[]{1.0f};
        float[][] x = {{input}};
        logger.info("input:{}", x);

        File file = ResourceUtils.getFile("classpath:model/nn_model.pb");
        try(Graph graph = new Graph()){
            byte[] graphBytes = IOUtils.toByteArray(new FileInputStream(file));
            graph.importGraphDef(graphBytes);
            try(Session session = new Session(graph)){
                // pre = sess.run(pre, feed_dict={x: x_data})
                // pre = session.runner()
                //                     .feed("Input/x:0", Tensor.create(x))
                //                     .fetch("Output/predictions:0").run().get(0).floatValue();
                Tensor prediction = session.runner()
                                .feed("Input/x:0", Tensor.create(x))
                                .fetch("Output/predictions:0").run().get(0);
                float[][] preOutput = (float[][])prediction.copyTo(new float[1][1]);
                return preOutput[0][0];

            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        return input;
    }

    @RequestMapping(value="/image-tranform2base64", method=RequestMethod.POST)
    public String imageTransfor2base64(@RequestBody Map params) throws Exception{
        if(params.containsKey("imageLocalPath")){
            String path = params.get("imageLocalPath").toString();
            return ImageBase64Util.image2Base64(path);
        }else if(params.containsKey("imageURL")){
            Map<String, String> paramHeader = new HashMap<String, String>();
            paramHeader.put("Accept", "application/json");
            String result = null;
            String imageURL = params.get("imageURL").toString();
            HttpClient httpClient = null;
            try{
                httpClient = new HttpsTrustClientV45().init();
                result = HttpsClientV45Util.doGetImage(httpClient, imageURL, paramHeader, null);
            }catch(Exception e){
                e.printStackTrace();
            }
            // logger.info("image data from url: {}",ImageBase64Util.string2Base64(result));
            return result;
            // return ImageBase64Util.string2Base64(result);
        }else{
            return "检查参数";
        }  
    }

    @RequestMapping(value="/image-base64-save", method=RequestMethod.POST) 
    public String imageBase64Save(@RequestBody Map params) throws Exception{
        String imageURL = params.get("imageURL").toString();
        String imageBase64 = null;
        HttpClient httpClient = null;
        Map<String, String> paramHeader = new HashMap<String, String>();
        paramHeader.put("Accept", "application/json");
        try{
            httpClient = new HttpsTrustClientV45().init();
            imageBase64 = HttpsClientV45Util.doGetImage(httpClient, imageURL, paramHeader, null);
        }catch(Exception e){
            e.printStackTrace();
        }
        String imageSavePath = params.get("imageSavePath").toString();
        ImageBase64Util.base64ToImage(imageBase64, imageSavePath);
        return "保存成功";
    }
}




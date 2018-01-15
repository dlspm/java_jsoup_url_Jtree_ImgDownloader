/*
 * Copyright 2018 angus.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 *
 * @author angus
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class Facebook {
    public static void main(String[] args) throws IOException {
        connect();//connect the website,
//        System.out.print(doc.outerHtml());//in the wole html file, i can find the tag <p>
//        Elements newsHeadlines = doc.select("p");//nothing
//        doc.getElementsByTag("p");//nothing either
//        String oldEleStr = newsHeadlines.text();
//        System.out.println(oldEleStr);//nothing
    }

    static void connect() throws IOException {
        Connection.Response res = Jsoup.connect("https://www.facebook.com/login.php")
                .data("email", "0978298512", "pass", "angus0512")
                .method(Method.POST)
                .execute();

        System.out.println(res.statusCode());
        Document doc = res.parse();
        String sessionId = res.cookie("SESSIONID");
    }
    
    

}



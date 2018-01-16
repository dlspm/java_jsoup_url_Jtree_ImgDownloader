import com.github.abola.crawler.CrawlerPack;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;


class PttGossiping {

    public static void main(String[] args) throws IOException {
        int n=0;
        //System.out.println("");

        String url = "https://www.instagram.com/djs920114/";
        
        //圖片：        
        //1."https://pixabay.com/zh/photos/?hp=&image_type=&cat=&min_width=&min_height=&q=%E8%B2%93&order=popular"
            //https://pixabay.com/
            ///zh/photos/?hp=&image_type=&cat=&min_width=&min_height=&q=
            //貓(search name)
            //&order=popular
        
        //3."https://www.stockvault.net/free-photos/dog/"
            //+ href
            //meta, content
        
//        String url = "https://pixabay.com/zh/photos/?hp=&image_type=&" + "貓" + "=&min_width=&min_height=&q=%E8%B2%93&order=popular";
        
        String encoding = "gb2312";
        //1.根据网络和页面的编码集 抓取网页的源代码
        String htmlResouce = GetHtmlResouceByURL(url, encoding); //GetHtmlResouceByURL() 會 return String 整個 html/XML  回來
        //System.out.println(htmlResouce);

        //2.解析网页的源代码 jsoup jar包
        Document document = Jsoup.parse(htmlResouce, "UTF-8");
        System.out.println(document);
        
        
//        Get_facebook_imgurl(document, url);
//        Get_instagram_imgurl(document, url, "imgpath.txt");
        //3.通过调剂语句进行筛选信息
        
//        Get_imgsql_imgurl(document, url);


        new Url_img_dow("Get_urt_img v2").init();

    }
    
    
    
    
    public static void Get_facebook_imgurl(Document document, String url) throws IOException{
        Connection.Response response = Jsoup.connect(url).method(Connection.Method.GET).execute();
        response = Jsoup.connect(url)
            .cookies(response.cookies())
            .data("Action", "Login")
            .data("User", "0978298512")
            .data("Password", "angus0512")
            .method(Connection.Method.POST)
            .followRedirects(true)
            .timeout(50000)
            .execute();
//        Response res = Jsoup
//                .connect("url")
//                .data("loginField", "0978298512", "passField", "angus0512")
//                .method(Method.POST)
//                .execute();
//        Map<String, String> cookies = res.cookies();
//        document = Jsoup.connect("url").cookies(cookies).get();
        System.out.println(response);
    }
    
    
    public static void Get_imgsql_imgurl(Document document, String url, String filename){
        //meta, content
        int n=0;
        Elements elements = document.getElementsByTag("img");  
        // 回傳元素 (element) 指定標籤的後代集合物件 (object) 。
        
        for (Element element : elements) {
            String imgSrc = element.attr("src"); //获取src属性的值(attr代表某個元素的屬性)

            System.out.println("attr:" + imgSrc);
            //下载到本地文件夹中
            
//            downImgs(imgSrc, imgSrc.substring(imgSrc.length() - 12, imgSrc.length()));
            if (n == 3) {
                break;
            } else {
                n++;
            }
        }
            
            
    }
    
    public static void Get_instagram_imgurl(Document document, String url, String filename){
        //meta, content
        int n=0;
        Elements elements = document.getElementsByTag("a");  
        // 回傳元素 (element) 指定標籤的後代集合物件 (object) 。
        
        //輸出圖片路徑到 imgpath.txt
        File fpath = new File(filename);
//        FileOutputStream o = new FileOutputStream(fpath);

        try {

            FileOutputStream out = new FileOutputStream(fpath);
            
        
            for (Element element : elements) {
                String imgSrc = element.attr("href"); //获取src属性的值(attr代表某個元素的屬性)
                imgSrc = url.substring(0, 25) + imgSrc;
                System.out.println("attr:" + imgSrc);
                //下载到本地文件夹中
                String sp[] = new String[10];
                sp = split_line(imgSrc, "/");
                if(imgSrc.length()>29){
                    System.out.println("filename:" + imgSrc.substring(imgSrc.length() - 12, imgSrc.length()));
                    Get_instagram_imgdown(imgSrc, sp[4], out);
//                    Get_instagram_imgdown(imgSrc, imgSrc.substring(29, imgSrc.length()-1), out);
                }

    //            if (n == 1) {
    //                break;
    //            } else {
    //                n++;
    //            }
            }
        
            out.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
    public static void Get_instagram_imgdown(String imgsrc, String filename, FileOutputStream out) throws IOException{

        String htmlResouce = GetHtmlResouceByURL(imgsrc, "gb2312");
        Document document = Jsoup.parse(htmlResouce, "UTF-8");

        Elements elements = document.getElementsByTag("meta");
//             回傳元素 (element) 指定標籤的後代集合物件 (object) 。

        for (Element element : elements) {
            String imgSrc = element.attr("content"); //获取src属性的值(attr代表某個元素的屬性) 
            if (imgSrc.length() > 18) {
                if ("https://instagram.".equals(imgSrc.substring(0, 18))) {
                    System.out.println("attr:" + imgSrc);
                    
                    byte[] bimgpath = (filename + ".jpg," + imgSrc+"\n").getBytes();
                    
                    out.write(bimgpath);
//                    downImgs(imgSrc, filename);
                    break;
                }
            }
        }

    }
    
    public static String GetHtmlResouceByURL(String url, String encoding) {

        // 建立容器存储网页源代码
        StringBuffer buffer = new StringBuffer();
        URL urlobj = null;
        URLConnection uc = null;
        InputStreamReader isr = null;
        BufferedReader input = null;
        try {
            //建立网络连接
            urlobj = new URL(url);
            //打开网络连接
            uc = urlobj.openConnection();
            //建立网络输入流
            isr = new InputStreamReader(uc.getInputStream(), encoding);
            //建立缓冲流读输入的数据
            input = new BufferedReader(isr);

            //循环遍历数据
            String line = null;
            while ((line = input.readLine()) != null) {
                //添加换行
                buffer.append(line + "\n");
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            System.out.println("連接源代碼失敗");
        } finally {
            try {
                if (isr != null) {
                    isr.close();
                }
                input.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("關閉失敗");
            }
        }

        return buffer.toString();
    }
    
    
    
    
    public static void downImgs(String imgsrc, String filePath) {

        //獲取圖片
        try {
            

            URL url = new URL(imgsrc);
            
            //打開圖片鏈接
//            HttpURLConnection uc = (HttpURLConnection) url.openConnection(); 
//            uc.setRequestMethod("GET");//設置請求方式
//            uc.setConnectTimeout(2*1000); //設置超時響應時間 
//            InputStream is = uc.getInputStream(); //通過輸入流獲取圖片數據 

            //創建文檔

            File file = new File(imgsrc );
            //輸出
            String sr = "img/" + filePath + ".jpg";
            FileOutputStream fos = new FileOutputStream(sr, false);
            InputStream is = url.openStream();
            int line = 0;
            while ((line = is.read()) != -1) {
                fos.write(line); //寫入數據
            }
            is.close();
            

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public static String [] split_line(String data, String str) {
        String[] split_line = data.split(str);
//                        System.out.println(split_line[0] + "+" + split_line[1] ;

        String show_split_line = "";
        String sp[] = new String[10];
        int k = 0;
        for (String s : split_line) {
            show_split_line = show_split_line + "[" + s + "]";
//                            System.out.println(show_split_line+","+s);
            sp[k++] = s;
        }
        return sp;

    }
    
}

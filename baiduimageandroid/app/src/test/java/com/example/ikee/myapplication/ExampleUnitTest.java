package com.example.ikee.myapplication;

import android.util.JsonReader;
import android.webkit.WebView;

import com.example.ikee.myapplication.utils.BaiduImages;
import com.example.ikee.myapplication.utils.NetworkConnect;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testFunction() {

        Connection connect = Jsoup.connect(BaiduImages.TEST_URL2);
        try {
            Document document = connect.get();
//            System.out.println(document.html());
            Elements script = document.select("script");
            int count = 0;
            for(Element node : script) {
                System.out.println("-----");
                System.out.println(node.html());
                ++count;
            }
            System.out.println("-----" + count);
        } catch (IOException e) {


        }
    }

    @Test
    public void testDecode() throws IOException, JSONException {
        String decoded = BaiduImages.decode("ippr_z2C$qAzdH3FAzdH3Ft42n_z&e3B17tpwg2_z&e3Bv54AzdH3F7rs5w1fAzdH3Ftpj4AzdH3Fda8mamAzdH3FabAzdH3Fda8mamabdddcc8_PY3u4_z&e3Bpi74k_z&e3B0aa_a_z&e3B3rj2");
        String decoded2 = BaiduImages.decode("ippr_z2C$qAzdH3FAzdH3Fr8_z&e3B2jxtg2_z&e3Bv54AzdH3FG8AzdH3FMaaAzdH3FdCAzdH3F8bAzdH3F6BACE8QxHcPDB6mTAADZd49Ei35aaa_z&e3B3r2");
        String keyWord = URLEncoder.encode("张天爱", "UTF-8");
        String jsonUrl = "http://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&is=&fp=result&queryWord=" + keyWord
                + "&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0&word=" + keyWord +
                "&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&pn=20&rn=10";
        InputStream inputStream = NetworkConnect.downloadInputStream(jsonUrl);
        String s = NetworkConnect.inputStreamToString(inputStream);
        inputStream.close();
        System.out.println(s);
        System.out.println(decoded2);
    }
}
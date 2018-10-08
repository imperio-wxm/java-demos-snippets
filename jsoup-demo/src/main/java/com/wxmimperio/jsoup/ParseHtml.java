package com.wxmimperio.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ParseHtml {

    public static void main(String[] args) throws IOException {
        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n" +
                "<html>\n" +
                "  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=8\">\n" +
                "  <meta http-equiv=\"Content-type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <title>\n" +
                "    Logs for container_e09_1534844699528_766486_01_000001\n" +
                "  </title>\n" +
                "  <link rel=\"stylesheet\" href=\"/static/yarn.css\">\n" +
                "  <style type=\"text/css\">\n" +
                "    #layout { height: 100%; }\n" +
                "    #layout thead td { height: 3em; }\n" +
                "    #layout #navcell { width: 11em; padding: 0 1em; }\n" +
                "    #layout td.content { padding-top: 0 }\n" +
                "    #layout tbody { vertical-align: top; }\n" +
                "    #layout tfoot td { height: 4em; }\n" +
                "  </style>\n" +
                "  <link rel=\"stylesheet\" href=\"/static/jquery/themes-1.9.1/base/jquery-ui.css\">\n" +
                "  <link rel=\"stylesheet\" href=\"/static/dt-1.9.4/css/jui-dt.css\">\n" +
                "  <script type=\"text/javascript\" src=\"/static/jquery/jquery-1.8.2.min.js\">\n" +
                "  </script>\n" +
                "  <script type=\"text/javascript\" src=\"/static/jquery/jquery-ui-1.9.1.custom.min.js\">\n" +
                "  </script>\n" +
                "  <script type=\"text/javascript\" src=\"/static/dt-1.9.4/js/jquery.dataTables.min.js\">\n" +
                "  </script>\n" +
                "  <script type=\"text/javascript\" src=\"/static/yarn.dt.plugins.js\">\n" +
                "  </script>\n" +
                "  <style type=\"text/css\">\n" +
                "    #jsnotice { padding: 0.2em; text-align: center; }\n" +
                "    .ui-progressbar { height: 1em; min-width: 5em }\n" +
                "  </style>\n" +
                "  <script type=\"text/javascript\">\n" +
                "    $(function() {\n" +
                "      $('#nav').accordion({autoHeight:false, active:0});\n" +
                "    });\n" +
                "  </script>\n" +
                "  <div id=\"jsnotice\" class=\"ui-state-error\">\n" +
                "    This page works best with javascript enabled.\n" +
                "  </div>\n" +
                "  <script type=\"text/javascript\">\n" +
                "    $('#jsnotice').hide();\n" +
                "  </script>\n" +
                "  <table id=\"layout\" class=\"ui-widget-content\">\n" +
                "    <thead>\n" +
                "      <tr>\n" +
                "        <td colspan=\"2\">\n" +
                "          <div id=\"header\" class=\"ui-widget\">\n" +
                "            <div id=\"user\">\n" +
                "              Logged in as: dr.who\n" +
                "            </div>\n" +
                "            <div id=\"logo\">\n" +
                "              <img src=\"/static/hadoop-st.png\">\n" +
                "            </div>\n" +
                "            <h1>\n" +
                "              Logs for container_e09_1534844699528_766486_01_000001\n" +
                "            </h1>\n" +
                "          </div>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </thead>\n" +
                "    <tfoot>\n" +
                "      <tr>\n" +
                "        <td colspan=\"2\">\n" +
                "          <div id=\"footer\" class=\"ui-widget\">\n" +
                "          </div>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </tfoot>\n" +
                "    <tbody>\n" +
                "      <tr>\n" +
                "        <td id=\"navcell\">\n" +
                "          <div id=\"nav\">\n" +
                "            <h3>\n" +
                "              ResourceManager\n" +
                "            </h3>\n" +
                "            <ul>\n" +
                "              <li>\n" +
                "                <a href=\"http://wh-7-10:8088\">RM Home</a>\n" +
                "            </ul>\n" +
                "            <h3>\n" +
                "              NodeManager\n" +
                "            </h3>\n" +
                "            <ul>\n" +
                "              <li>\n" +
                "                <a href=\"/node/node\">Node Information</a>\n" +
                "              <li>\n" +
                "                <a href=\"/node/allApplications\">List of Applications</a>\n" +
                "              <li>\n" +
                "                <a href=\"/node/allContainers\">List of Containers</a>\n" +
                "            </ul>\n" +
                "            <h3>\n" +
                "              Tools\n" +
                "            </h3>\n" +
                "            <ul>\n" +
                "              <li>\n" +
                "                <a href=\"/conf\">Configuration</a>\n" +
                "              <li>\n" +
                "                <a href=\"/logs\">Local logs</a>\n" +
                "              <li>\n" +
                "                <a href=\"/stacks\">Server stacks</a>\n" +
                "              <li>\n" +
                "                <a href=\"/jmx?qry=Hadoop:*\">Server metrics</a>\n" +
                "            </ul>\n" +
                "          </div>\n" +
                "        </td>\n" +
                "        <td class=\"content\">\n" +
                "          <pre>\n" +
                "[2018-10-08 10:24:03,283] INFO  che.spark.util.SignalUtils [] [] - Registered signal handler for TERM\n" +
                "Selector选择器组合使用\n" +
                "el#id: 元素+ID，比如： div#logo\n" +
                "el.class: 元素+class，比如： div.masthead\n" +
                "el[attr]: 元素+class，比如： a[href]\n" +
                "任意组合，比如：a[href].highlight\n" +
                "ancestor child: 查找某个元素下子元素，比如：可以用.body p 查找在\"body\"元素下的所有 p元素\n" +
                "parent > child: 查找某个父元素下的直接子元素，比如：可以用div.content > p 查找 p 元素，也可以用body > * 查找body标签下所有直接子元素\n" +
                "siblingA + siblingB: 查找在A元素之前第一个同级元素B，比如：div.head + div\n" +
                "siblingA ~ siblingX: 查找A元素之前的同级X元素，比如：h1 ~ p\n" +
                "el, el, el:多个选择器组合，查找匹配任一选择器的唯一元素，例如：div.masthead, div.logo\n" +
                "</pre>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                "    </tbody>\n" +
                "  </table>\n" +
                "</html>\n";
        Document doc = Jsoup.parse(html);

        Element element = doc.selectFirst("td.content");
        String content = element.getElementsByTag("pre").html();
        InputStream is = new ByteArrayInputStream(content.getBytes());

        StringBuilder out = new StringBuilder();
        byte[] b = new byte[4096];
        for (int n; (n = is.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        System.out.println(out.toString());
    }
}

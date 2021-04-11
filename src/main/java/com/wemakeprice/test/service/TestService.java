package com.wemakeprice.test.service;

import com.wemakeprice.test.param.CrawlingParam;
import com.wemakeprice.test.param.enumParam.TypeEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TestService {

    public String getResultData(CrawlingParam param) throws Exception {
        Document crawlingPage = Jsoup.connect("http://localhost:8080").get();

        return this.getTextInType(crawlingPage, param);
    }

    private String getTextInType(Document crawlingPage, CrawlingParam param) throws Exception {
        final String crawlingStr = crawlingPage.html();

        if (param.getType().equals(TypeEnum.EXCEPT_ALL_HTML_TAG)) {
            // html 태그 모두 삭제
            final String noHtmlTag = removeAllHtmlTag(crawlingStr);
            System.out.println(noHtmlTag);
        } else if (param.getType().equals(TypeEnum.ALL_TEXT)) {
            // <>꺽쇠만 삭제
            final String noBracketTxt = getOnlyText(crawlingStr);
            System.out.println(noBracketTxt);
        }

        return "";
    }

    private String removeAllHtmlTag(String html) throws Exception {
        Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
        Pattern WHITESPACE = Pattern.compile("\\s+");
        Matcher m;
        m = TAGS.matcher(html);
        html = m.replaceAll("");
        m = WHITESPACE.matcher(html);
        html = m.replaceAll("");
        return html;
    }

    private String getOnlyText(String html) throws Exception {
        Pattern WHITESPACE = Pattern.compile("[a-zA-Z0-9]*$");
        Matcher m;
        m = WHITESPACE.matcher(html);
        html = m.replaceAll("");
        return html;
    }

}

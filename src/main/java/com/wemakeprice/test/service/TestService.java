package com.wemakeprice.test.service;

import com.wemakeprice.test.param.CrawlingParam;
import com.wemakeprice.test.param.enumParam.TypeEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TestService {

    public String getResultData(CrawlingParam param) throws Exception {
        Document crawlingPage = Jsoup.connect("http://localhost:8080").get();

        // 타입에 맞게 텍스트를 가져온다.
        final String onlyText = this.getTextInType(crawlingPage.html(), param.getType());

        // 텍스트를 소팅한다.
        final String sortedStr = this.getSortedStr(onlyText);

        // 알파벳만
        final String alphabetStr = sortedStr.replaceAll("[a-zA-Z]","");

        // 숫자만
        final String numStr = sortedStr.replaceAll("[0-9]", "");

        // 알파벳, 숫자를 조건에 맞게 머지한다.
        final String mergedStr = this.getMergeAlphabetAndNumber(alphabetStr, numStr);

        // 단위에 맞게 몫, 나머지를 구한다.

        return "";
    }

    public String getSortedStr(String originStr) {
        String[] originStrArr = new String[originStr.length()];

        for(int i = 0 ; i < originStr.length(); i++) {
            originStrArr[i] = originStr.substring(i, i + 1);
        }

        // 정렬
        Arrays.sort(originStrArr, (o1, o2) -> {
                    if(o1.equalsIgnoreCase(o2)){
                        if(o1.toUpperCase().equals(o1)){
                            return -1;
                        }else{
                            return 1;
                        }
                    }else {
                        return o1.compareToIgnoreCase(o2);
                    }
                }
        );

        StringBuffer sb = new StringBuffer();

        for (String s : originStrArr) {
            sb.append(s);
        }
        return sb.toString();
    }

    public String getTextInType(String html, TypeEnum type) {
        if ( type.equals(TypeEnum.EXCEPT_ALL_HTML_TAG) ) {
            Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
            Matcher m;
            m = TAGS.matcher(html);
            html = m.replaceAll("");
        }

        html = html.replaceAll("\\s+", "").replaceAll("[^xfe0-9a-zA-Z\\s]", "");
        return html;
    }

    public String getMergeAlphabetAndNumber(String alphabetStr, String numberStr) {
        return "A1a3B4b5Cde";
    }
}

package com.wemakeprice.test.service;

import com.wemakeprice.test.dto.ResultDto;
import com.wemakeprice.test.param.CrawlingParam;
import com.wemakeprice.test.param.enumParam.TypeEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.Arrays;


@Service
public class TestService {

    public ResultDto getResultData(CrawlingParam param) throws Exception {
        Document crawlingPage = Jsoup.connect(param.getUrl()).get();;

        // 타입에 맞게 텍스트를 가져온다.
        final String onlyText = this.getTextInType(crawlingPage.html(), param.getType());

        // 텍스트를 소팅한다. ex) 4231BaACc12 -> 112234AaBCc
        final String sortedStr = this.getSortedStr(onlyText);

        // 알파벳만
        final String alphabetStr = this.getOnlyAlphabet(sortedStr);

        // 숫자만
        final String numStr = this.getOnlyNumber(sortedStr);

        // 알파벳, 숫자를 조건에 맞게 머지한다.
        final String mergedStr = this.getMergeAlphabetAndNumber(alphabetStr, numStr);

        // 단위에 맞게 몫, 나머지를 구한다.
        final ResultDto result = this.getResultDtoByMergedStrAndUnitCnt(mergedStr, param.getUnitCnt());

        return result;
    }

    public String getSortedStr(String originStr) {
        String[] originStrArr = new String[originStr.length()];

        for(int i = 0 ; i < originStr.length(); i++) {
            originStrArr[i] = originStr.substring(i, i + 1);
        }
        Arrays.sort(originStrArr);
        // 정렬
        Arrays.sort(originStrArr, (o1, o2) -> {
                    if(o1.equalsIgnoreCase(o2)){
                        if(o1.toUpperCase().equals(o2)){
                            return 1;
                        }else{
                            return -1;
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
            // html태그만 제거의 경우는 태그 안에 있는 문자열 모두 제거
            html = html.replaceAll("<.+?>", "");
        }

        html = html.replaceAll("\\s+", "").replaceAll("[^0-9a-zA-Z\\s]", "");
        return html;
    }

    public String getOnlyAlphabet(String sortedStr) {
        return sortedStr.replaceAll("[^a-zA-Z]","");
    }

    public String getOnlyNumber(String sortedStr) {
        return sortedStr.replaceAll("[^0-9]", "");
    }

    public String getMergeAlphabetAndNumber(String alphabetStr, String numberStr) {
        int alphabetLength = alphabetStr.length();
        int numberLength = numberStr.length();

        boolean isAlpabetEnd = false;
        boolean isNumberEnd = false;

        int alphabetIdx = 0;
        int numberIdx = 0;

        char[] mergedCharArr = new char[alphabetLength + numberLength];
        char[] alphabetCharArr = alphabetStr.toCharArray();
        char[] numberCharArr = numberStr.toCharArray();

        for(int i = 0 ; i < mergedCharArr.length; i++) {

                if ( alphabetIdx == alphabetLength ) {
                    isAlpabetEnd = true;
                } else if ( numberIdx == numberLength ) {
                    isNumberEnd = true;
                } else {
                    if ( i % 2 == 0 ) {
                        mergedCharArr[i] = alphabetCharArr[alphabetIdx++];
                    } else {
                        mergedCharArr[i] = numberCharArr[numberIdx++];
                    }
                }

                if(isAlpabetEnd) {
                    mergedCharArr[i] = numberCharArr[numberIdx++];
                } else if(isNumberEnd) {
                    mergedCharArr[i] = alphabetCharArr[alphabetIdx++];
                }
        }

        StringBuffer sb = new StringBuffer();

        for(char c : mergedCharArr) {
            sb.append(c);
        }

        return sb.toString();
    }

    public ResultDto getResultDtoByMergedStrAndUnitCnt(String mergedStr, int unitCnt) {
        ResultDto resultDto = new ResultDto();
        int mergedStrLength = mergedStr.length();
        if ( unitCnt > mergedStrLength ) {

            resultDto.setQuotient("");
            resultDto.setRemainder(mergedStr);
        } else if ( unitCnt == mergedStrLength ) {

            resultDto.setQuotient(mergedStr);
            resultDto.setRemainder("");
        } else {
            // str의 length를 unitCnt로 나눈 값의 몫 * unitCnt = 몫의 길이
            int quotientLength = (mergedStrLength / unitCnt) * unitCnt;

            resultDto.setQuotient(mergedStr.substring(0, quotientLength));
            resultDto.setRemainder(mergedStr.substring(quotientLength , mergedStrLength));
        }

        return resultDto;
    }
}

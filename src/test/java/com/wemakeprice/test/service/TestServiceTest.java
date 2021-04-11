package com.wemakeprice.test.service;

import com.wemakeprice.test.dto.ResultDto;
import com.wemakeprice.test.param.enumParam.TypeEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestServiceTest {
    private TestService testService;

    @BeforeEach
    void setUp() {
        this.testService = new TestService();
    }

    @Test
    public void getTextExceptAllHtmlTag() {
        String resultExceptAllHtmlTag = testService.getTextInType("<html><h1>T아\n</h1><script>alert(1);</script></html>", TypeEnum.EXCEPT_ALL_HTML_TAG);
        String resultExceptAllHtmlTag2 = testService.getTextInType("<html><h1>T아\n</h1><script>console.log(123);alert(1);function test() { \nconsole.log(123); \n}</script></html>", TypeEnum.EXCEPT_ALL_HTML_TAG);

        assertEquals("Talert1", resultExceptAllHtmlTag);
        assertEquals("Tconsolelog123alert1functiontestconsolelog123", resultExceptAllHtmlTag2);
    }

    @Test
    public void getTextExceptOnlyBracket() {
        String resultOnlyExceptBracket = testService.getTextInType("<html><h1>T아\n</h1><script>alert(1);</script></html>", TypeEnum.ALL_TEXT);
        assertEquals("htmlh1Th1scriptalert1scripthtml", resultOnlyExceptBracket);
    }

    @Test
    public void getSortedStr() {
        String result = testService.getSortedStr("4231BaACc12");

        assertEquals("112234AaBCc", result);
    }

    @Test
    public void getOnlyAlphabet() {
        String result = testService.getOnlyAlphabet("112234AaBCc");
        String onlyNumber = testService.getOnlyAlphabet("112234");

        assertEquals("AaBCc", result);
        assertEquals("", onlyNumber);
    }

    @Test
    public void getOnlyNumber() {
        String result = testService.getOnlyNumber("112234AaBCc");
        String onlyAlphabet = testService.getOnlyNumber("AaBCc");

        assertEquals("112234", result);
        assertEquals("", onlyAlphabet);
    }

    @Test
    public void getMergeAlphabetAndNumber() {
        String result = testService.getMergeAlphabetAndNumber("AaBbCdE", "1345");
        String result2 = testService.getMergeAlphabetAndNumber("AaBbDef", "1");
        String onlyAlphabet = testService.getMergeAlphabetAndNumber("AaBb", "");
        String onlyNumber = testService.getMergeAlphabetAndNumber("", "1236");


        assertEquals("A1a3B4b5CdE", result);
        assertEquals("A1aBbDef", result2);
        assertEquals("AaBb", onlyAlphabet);
        assertEquals("1236", onlyNumber);
    }

    @Test
    public void getResultDtoByMergedStrAndUnitCnt() {
        ResultDto testData = new ResultDto("A1a3B4b5C", "dE");
        ResultDto testData2 = new ResultDto("A1a3B4b5CdE", "");
        ResultDto testData3 = new ResultDto("", "A1a3B4b5CdE");


        ResultDto resultDto1 = testService.getResultDtoByMergedStrAndUnitCnt("A1a3B4b5CdE", 3);
        ResultDto resultDto2 = testService.getResultDtoByMergedStrAndUnitCnt("A1a3B4b5CdE", 11);
        ResultDto resultDto3 = testService.getResultDtoByMergedStrAndUnitCnt("A1a3B4b5CdE", 12);

        assertEquals(testData.getQuotient(), resultDto1.getQuotient());
        assertEquals(testData.getRemainder(), resultDto1.getRemainder());

        assertEquals(testData2.getQuotient(), resultDto2.getQuotient());
        assertEquals(testData2.getRemainder(), resultDto2.getRemainder());

        assertEquals(testData3.getQuotient(), resultDto3.getQuotient());
        assertEquals(testData3.getRemainder(), resultDto3.getRemainder());
    }
}
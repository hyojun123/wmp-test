package com.wemakeprice.test.service;

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
        assertEquals(resultExceptAllHtmlTag, "Talert1");
    }

    @Test
    public void getTextExceptOnlyBracket() {
        String resultOnlyExceptBracket = testService.getTextInType("<html><h1>T아\n</h1><script>alert(1);</script></html>", TypeEnum.ALL_TEXT);
        assertEquals(resultOnlyExceptBracket, "htmlh1Th1scriptalert1scripthtml");
    }

    @Test
    public void getSortedStr() {
        String result = testService.getSortedStr("4231BaACc12");

        assertEquals(result, "112234AaBCc");
    }

    @Test
    public void getMergeAlphabetAndNumber() {
        String result = testService.getMergeAlphabetAndNumber("AaBbCdE", "1345");

        assertEquals(result, "A1a3B4b5Cde");
    }
}
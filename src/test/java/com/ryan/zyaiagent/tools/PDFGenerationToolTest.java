package com.ryan.zyaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "baidu.pdf";
        String content = "baidu https://www.baidu.com";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}
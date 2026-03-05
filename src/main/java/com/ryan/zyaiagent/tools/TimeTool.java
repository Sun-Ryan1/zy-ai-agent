package com.ryan.zyaiagent.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具（获取当前时间日期等信息）
 */
public class TimeTool {

    @Tool(description = "Get the current date and time information. Can return default format or custom format based on input.")
    public String getCurrentTime(
            @ToolParam(description = "Optional format pattern for the date and time (e.g., 'yyyy-MM-dd HH:mm:ss'). If not provided, a default pattern will be used.", required = false)
            String pattern
    ) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter;

        // 如果未提供格式，使用默认格式
        if (pattern == null || pattern.trim().isEmpty()) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        } else {
            try {
                formatter = DateTimeFormatter.ofPattern(pattern);
            } catch (IllegalArgumentException e) {
                return "Error: Invalid date format pattern - " + e.getMessage();
            }
        }

        return now.format(formatter);
    }
}

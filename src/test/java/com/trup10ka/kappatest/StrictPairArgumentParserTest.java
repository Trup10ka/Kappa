package com.trup10ka.kappatest;

import com.trup10ka.kappa.cli.arguments.StrictPairArgumentParser;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StrictPairArgumentParserTest
{
    @Test
    public void parse()
    {
        StrictPairArgumentParser parser = new StrictPairArgumentParser(new String[] { "-fn", "-ln" } );
        Map<String, String> result = parser.parse("key1=value1 key2=value2", "=");
        assertEquals(2, result.size());
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
    }

    @Test
    public void parseWithMissingValue()
    {
        StrictPairArgumentParser parser = new StrictPairArgumentParser(new String[] { "-fn", "-ln" });
        assertThrows(IllegalArgumentException.class, () -> parser.parse("key1=value1 key2", "="));
    }

    @Test
    public void parseWithDefaultDelimiter()
    {
        StrictPairArgumentParser parser = new StrictPairArgumentParser(new String[] { "-fn", "-ln" });
        Map<String, String> result = parser.parse("key1 value1 key2 value2");
        assertEquals(2, result.size());
        assertEquals("value1", result.get("key1"));
        assertEquals("value2", result.get("key2"));
    }
}

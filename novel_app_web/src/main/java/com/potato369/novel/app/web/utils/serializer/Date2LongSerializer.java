package com.potato369.novel.app.web.utils.serializer;

import java.io.IOException;
import java.util.Date;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * <pre>
 * @PackageName com.potato369.novel.app.web.utils.serializer
 * @ClassName Date2LongSerializer
 * @Desc Date2LongSerializer
 * @WebSite https://www.potato369.com
 * @Author Jack
 * @Date 2019/4/29 15:32
 * @CreateBy IntellJ IDEA 2018.3.5
 * @Copyright Copyright (c) 2016 ~ 2020 版权所有 (C) 土豆互联科技(深圳)有限公司 https://www.potato369.com All Rights Reserved。
 * </pre>
 */
public class Date2LongSerializer extends JsonSerializer<Date> {

    /**
     * Method that can be called to ask implementation to serialize
     * values of type this serializer handles.
     *
     * @param date       Value to serialize; can <b>not</b> be null.
     * @param gen         Generator used to output resulting Json content
     * @param serializers Provider that can be used to get serializers for
     */
    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeNumber(date.getTime() / 1000);
    }
}

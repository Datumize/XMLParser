package cat.altimiras.xml;


import cat.altimiras.xml.pojo.Nested2TestObj;
import cat.altimiras.xml.pojo.SimpleTestObj;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class TagListenerTest {

    @Test
    public void listenerStringTest() throws Exception{

        String xml = IOUtils.toString(this.getClass().getResourceAsStream("/simpleTest.xml"), "UTF-8");
        XMLParser<SimpleTestObj> parser = new XMLParserImpl<>(SimpleTestObj.class);

        TagListener stringListener = mock(TagListener.class);
        when(stringListener.notify("element1", "111")).thenReturn(false);

        //register listener
        parser.register("element1", stringListener);

        parser.parse(xml);
        verify(stringListener, times(1)).notify("element1", "111");
    }

    @Test
    public void listenerObjectTest() throws Exception{

        String xml = IOUtils.toString(this.getClass().getResourceAsStream("/nested2Test.xml"), "UTF-8");
        XMLParser<Nested2TestObj> parser = new XMLParserImpl<>(Nested2TestObj.class);

        TagListener objListener = mock(TagListener.class);
        when(objListener.notify(eq("simpleTestObj1"), any())).thenReturn(false);

        //register listener
        parser.register("simpleTestObj1", objListener);

        parser.parse(xml);
        verify(objListener, times(1)).notify(eq("simpleTestObj1"), any());
    }

    @Test
    public void listenerNotPresentTest() throws Exception{

        String xml = IOUtils.toString(this.getClass().getResourceAsStream("/nested2Test.xml"), "UTF-8");
        XMLParser<Nested2TestObj> parser = new XMLParserImpl<>(Nested2TestObj.class);

        TagListener objListener = mock(TagListener.class);
        when(objListener.notify(eq("tagNotFound"), any())).thenReturn(false);

        //register listener
        parser.register("tagNotFound", objListener);

        parser.parse(xml);
        verify(objListener, never()).notify(eq("tagNotFound"), any());
    }

    @Test
    public void listenerStopTest() throws Exception{

        String xml = IOUtils.toString(this.getClass().getResourceAsStream("/nested2Test.xml"), "UTF-8");
        XMLParser<Nested2TestObj> parser = new XMLParserImpl<>(Nested2TestObj.class);

        TagListener objListener = mock(TagListener.class);
        when(objListener.notify(eq("simpleTestObj1"), any())).thenReturn(true);

        //register listener
        parser.register("simpleTestObj1", objListener);

        Nested2TestObj o = parser.parse(xml);

        verify(objListener, times(1)).notify(eq("simpleTestObj1"), any());
        assertNull("title should not be parsed", o.getTitle());
        assertEquals("111", o.getSimpleTestObj1().getElement1());
        assertNull("title should not be parsed", o.getSimpleTestObj2());
    }
}
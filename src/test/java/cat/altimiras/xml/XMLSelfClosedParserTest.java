package cat.altimiras.xml;

import cat.altimiras.xml.pojo.Nested2TestObj;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XMLSelfClosedParserTest {

	@Test
	public void xmlSelfClosedTest() throws Exception {

		ClassIntrospector ci = new ClassIntrospector(Nested2TestObj.class);

		String xml = IOUtils.toString(this.getClass().getResourceAsStream("/selfClosedTest.xml"), "UTF-8");
		XMLParser<Nested2TestObj> parser = new WoodStoxParserImpl<>(Nested2TestObj.class, ci);

		Nested2TestObj o = parser.parse(xml);

		assertEquals("title", o.getTitle().trim());
		assertEquals("111", o.getSimpleTestObj1().getElement1().trim());
		assertEquals("222", o.getSimpleTestObj2().getElement2().trim());
	}
}

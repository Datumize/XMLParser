package cat.altimiras.xml;

import cat.altimiras.xml.exceptions.InvalidXMLFormatException;

public interface XMLParser<T> {

	/**
	 * Parses a XML to T
	 *
	 * @param xml
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	T parse(String xml) throws InvalidXMLFormatException;

	T parse(byte[] xml) throws InvalidXMLFormatException;

	/**
	 * Register a TagListener to xml tag
	 *
	 * @param tag
	 * @param listner
	 */
	void register(String tag, TagListener listner);
}

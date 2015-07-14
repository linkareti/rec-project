package com.linkare.rec.impl.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.linkare.net.protocols.Protocols;
import com.linkare.rec.data.Multiplier;
import com.linkare.rec.data.acquisition.ByteArrayValue;
import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.metadata.ChannelDirection;
import com.linkare.rec.data.metadata.ChannelInfo;
import com.linkare.rec.data.metadata.ChannelParameter;
import com.linkare.rec.data.metadata.FrequencyScale;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.metadata.ParameterType;
import com.linkare.rec.data.metadata.SamplesNumScale;
import com.linkare.rec.data.metadata.Scale;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.data.synch.FrequencyDefType;

public class HardwareInfoXMLReader {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
		URL url = HardwareInfoXMLReader.class.getClassLoader().getResource(
				"pt/utl/ist/elab/driver/aleatorio/HardwareInfo.xml");
		System.out.println(HardwareInfoXMLReader.readHardwareInfo(url));
	}

	public static HardwareInfo readHardwareInfo(final String file_loc) throws FileNotFoundException,
			ParserConfigurationException, SAXException, IOException {
		return HardwareInfoXMLReader.readHardwareInfo(new FileInputStream(file_loc));
	}

	public static HardwareInfo readHardwareInfo(final URL url) throws IOException, ParserConfigurationException,
			SAXException {
		final URLConnection con = url.openConnection();
		return HardwareInfoXMLReader.readHardwareInfo(con.getInputStream());
	}

	public static HardwareInfo readHardwareInfo(final InputStream is) throws ParserConfigurationException,
			SAXException, IOException {

		final Document document = parseInputStreamAsXmlDocument(is);
		final HardwareInfoXMLReader scanner = new HardwareInfoXMLReader(document);
		scanner.visitDocument();
		return scanner.getHardwareInfo();
	}

	public static Document parseInputStreamAsXmlDocument(final InputStream is) throws ParserConfigurationException,
			SAXException, IOException {
		final EntityResolver entityResolver = new EntityResolver() {

			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				if (publicId != null && publicId.equals("-//Linkare/ReC/HardwareInfo//-")) {
					return new InputSource(this.getClass().getResourceAsStream("HardwareInfoSchema.dtd"));
				}

				return new InputSource(new URL(systemId).openConnection().getInputStream());
			}

		};

		final ErrorHandler myErrorHandler = new ErrorHandler() {

			@Override
			public void warning(SAXParseException exception) throws SAXException {
				exception.printStackTrace();
			}

			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				exception.printStackTrace();
			}

			@Override
			public void error(SAXParseException exception) throws SAXException {
				exception.printStackTrace();
			}
		};

		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringElementContentWhitespace(true);
		factory.setIgnoringComments(true);
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		final DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setEntityResolver(entityResolver);
		builder.setErrorHandler(myErrorHandler);
		final Document document = builder.parse(new InputSource(is));
		return document;
	}

	public static final void writeDocument(Document hardwareInfoDocument, File f) throws IOException,
			TransformerException {
		if (!f.exists()) {
			f.createNewFile();
		}

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// <!DOCTYPE HardwareInfo PUBLIC "-//Linkare/ReC/HardwareInfo//-"
		// "http://rec.linkare.com/HardwareInfoSchema.dtd">
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//Linkare/ReC/HardwareInfo//-");
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://rec.linkare.com/HardwareInfoSchema.dtd");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.STANDALONE, "no");

		DOMSource source = new DOMSource(hardwareInfoDocument);
		Result streamOut = new StreamResult(f);
		transformer.transform(source, streamOut);
	}

	/** org.w3c.dom.Document document */
	private Document document;

	/** Holds value of property hardwareInfo. */
	private HardwareInfo hardwareInfo;

	/**
	 * Creates a new instance of HardwareInfoXMLReader
	 * 
	 * @param document
	 */
	public HardwareInfoXMLReader(final Document document) {
		this.document = document;
	}

	/** Scan through org.w3c.dom.Document document. */
	public void visitDocument() {

		final Element element = document.getDocumentElement();

		if ((element != null) && element.getTagName().equals("HardwareInfo")) {
			visitElement_HardwareInfo(element);
		}
	}

	/** Scan through org.w3c.dom.Element named ChannelInfo. */
	ChannelInfo visitElement_ChannelInfo(final Element element) { // <ChannelInfo>
		// element.getValue();
		final ChannelInfo info = new ChannelInfo();
		final NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			final Attr attr = (Attr) attrs.item(i);
			if (attr.getName().equals("direction")) { // <ChannelInfo
				// direction="???">
				// attr.getValue();
				final String strDirection = attr.getValue();
				if (strDirection.equals("CHANNEL_INPUT")) {
					info.setChannelDirection(ChannelDirection.CHANNEL_INPUT);
				} else if (strDirection.equals("CHANNEL_OUTPUT")) {
					info.setChannelDirection(ChannelDirection.CHANNEL_OUTPUT);
				} else {
					throw new RuntimeException("Channel direction must be CHANNEL_INPUT or CHANNEL_OUTPUT");
				}

			}
			if (attr.getName().equals("independent")) { // <ChannelInfo
				// independent="???">
				// attr.getValue();
				final String strIndependent = attr.getValue();
				if (strIndependent.equals("true") || strIndependent.equals("yes") || strIndependent.equals("1")) {
					info.setChannelIndependent(true);
				} else if (strIndependent.equals("false") || strIndependent.equals("no") || strIndependent.equals("0")) {
					info.setChannelIndependent(false);
				} else {
					throw new RuntimeException("Channel independent must be true/yes/1 or false/no/0");
				}
			}
			if (attr.getName().equals("name")) { // <ChannelInfo name="???">
				// attr.getValue();
				info.setChannelName(attr.getValue());
			}
		}
		final NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			final Node node = nodes.item(i);
			switch (node.getNodeType()) {
			case Node.CDATA_SECTION_NODE:
				// ((org.w3c.dom.CDATASection)node).getData();
				break;
			case Node.ELEMENT_NODE:
				final Element nodeElement = (Element) node;
				if (nodeElement.getTagName().equals("Frequency")) {
					info.setSelectedFrequency(visitElement_Frequency(nodeElement));
				}
				if (nodeElement.getTagName().equals("frequency-scale")) {
					info.addFrequencies(visitElement_frequency_scale(nodeElement));
				}
				if (nodeElement.getTagName().equals("parameter")) {
					info.addChannelParameters(visitElement_parameter(nodeElement));
				}
				if (nodeElement.getTagName().equals("sampling-scale")) {
					info.setSamplingScale(visitElement_sampling_scale(nodeElement));
				}
				if (nodeElement.getTagName().equals("scale")) {
					final com.linkare.rec.impl.utils.HardwareInfoXMLReader.ScaleSelector ss = visitElement_scale(nodeElement);
					info.addScales(ss.getScale());
					if (ss.isSelected()) {
						info.setActualSelectedScale(ss.getScale());
					}
				}
				break;
			case Node.PROCESSING_INSTRUCTION_NODE:
				// ((org.w3c.dom.ProcessingInstruction)node).getTarget();
				// ((org.w3c.dom.ProcessingInstruction)node).getData();
				break;
			}
		}
		return info;
	}

	/** Scan through org.w3c.dom.Element named Frequency. */
	Frequency visitElement_Frequency(final Element element) { // <Frequency>
		// element.getValue();
		final Frequency frequency = new Frequency();
		final NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			final Attr attr = (Attr) attrs.item(i);
			if (attr.getName().equals("type")) { // <Frequency type="???">
				final String def_type = attr.getValue();
				if (def_type.equalsIgnoreCase("FrequencyType")) {
					frequency.setFrequencyDefType(FrequencyDefType.FrequencyType);
				} else if (def_type.equalsIgnoreCase("SamplingIntervalType")) {
					frequency.setFrequencyDefType(FrequencyDefType.SamplingIntervalType);
				} else {
					throw new RuntimeException("frequency type should be FrequencyType or SamplingIntervalType");
				}
			}
			if (attr.getName().equals("multiplier")) { // <Frequency
				frequency.setMultiplier(readMultiplier(attr.getValue()));
			}
		}

		frequency.setFrequency(Double.parseDouble(((Text) element.getFirstChild()).getData()));
		return frequency;
	}

	private Multiplier readMultiplier(final String multiplier) {
		if (multiplier.equalsIgnoreCase("fento")) {
			return Multiplier.fento;
		}
		if (multiplier.equalsIgnoreCase("pico")) {
			return Multiplier.pico;
		}
		if (multiplier.equalsIgnoreCase("nano")) {
			return Multiplier.nano;
		}
		if (multiplier.equalsIgnoreCase("micro")) {
			return Multiplier.micro;
		}
		if (multiplier.equalsIgnoreCase("milli")) {
			return Multiplier.mili;
		}
		if (multiplier.equalsIgnoreCase("none")) {
			return Multiplier.none;
		}
		if (multiplier.equalsIgnoreCase("kilo")) {
			return Multiplier.kilo;
		}
		if (multiplier.equalsIgnoreCase("mega")) {
			return Multiplier.mega;
		}
		if (multiplier.equalsIgnoreCase("giga")) {
			return Multiplier.giga;
		}
		if (multiplier.equalsIgnoreCase("tera")) {
			return Multiplier.tera;
		}
		throw new RuntimeException("multiplier attribute should be fento,pico,nano,micro,milli,none,kilo,giga,tera");
	}

	/** Scan through org.w3c.dom.Element named HardwareInfo. */
	void visitElement_HardwareInfo(final Element element) { // <HardwareInfo>
		// element.getValue();
		hardwareInfo = new HardwareInfo();
		final NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			final Attr attr = (Attr) attrs.item(i);
			if (attr.getName().equals("manufacturer")) { // <HardwareInfo
				// manufacturer="???">
				// attr.getValue();
				hardwareInfo.setHardwareManufacturer(attr.getValue());
			}
			if (attr.getName().equals("familiarName")) { // <HardwareInfo
				// familiarName="???">
				// attr.getValue();
				hardwareInfo.setFamiliarName(attr.getValue());
			}
			if (attr.getName().equals("name")) { // <HardwareInfo name="???">
				// attr.getValue();
				hardwareInfo.setHardwareName(attr.getValue());
			}
			if (attr.getName().equals("driverVersion")) { // <HardwareInfo
				// driverVersion="???">
				// attr.getValue();
				hardwareInfo.setDriverVersion(attr.getValue());
			}
			if (attr.getName().equals("id")) { // <HardwareInfo id="???">
				// attr.getValue();
				hardwareInfo.setHardwareUniqueID(attr.getValue());
			}
			if (attr.getName().equals("version")) { // <HardwareInfo
				// version="???">
				// attr.getValue();
				hardwareInfo.setHardwareVersion(attr.getValue());
			}
		}
		final NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			final Node node = nodes.item(i);
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				final Element nodeElement = (Element) node;
				if (nodeElement.getTagName().equals("ChannelInfo")) {
					hardwareInfo.addChannelsInfo(visitElement_ChannelInfo(nodeElement));
				}
				if (nodeElement.getTagName().equals("Frequency")) {
					hardwareInfo.setSelectedFrequency(visitElement_Frequency(nodeElement));
				}
				if (nodeElement.getTagName().equals("description")) {
					hardwareInfo.setDescriptionText(visitElement_description(nodeElement));
				}
				if (nodeElement.getTagName().equals("frequency-scale")) {
					hardwareInfo.addHardwareFrequencies(visitElement_frequency_scale(nodeElement));
				}
				if (nodeElement.getTagName().equals("parameter")) {
					hardwareInfo.addHardwareParameters(visitElement_parameter(nodeElement));
				}
				if (nodeElement.getTagName().equals("sampling-scale")) {
					hardwareInfo.setSamplingScale(visitElement_sampling_scale(nodeElement));
				}
				break;
			}
		}
	}

	/** Scan through org.w3c.dom.Element named PhysicsVal. */
	PhysicsVal visitElement_PhysicsVal(final Element element) { // <PhysicsVal>
		// element.getValue();
		final PhysicsVal value = new PhysicsVal();
		final NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			final Attr attr = (Attr) attrs.item(i);
			/*
			 * if (attr.getName().equals("href")) { // <PhysicsVal href="???">
			 * // attr.getValue(); try { ByteArrayValBuffer buffer=new
			 * ByteArrayValBuffer(new URL(attr.getValue()));
			 * value.setByteArrayValue(buffer.getByteArrayValue()); return
			 * value; }catch(Exception e) { throw new
			 * RuntimeException(e.getMessage()); } }
			 */
			if (attr.getName().equals("type")) { // <PhysicsVal type="???">
				final String type = attr.getValue();
				if (type.equalsIgnoreCase("boolean")) {
					final String val = ((Text) element.getFirstChild()).getData();
					if (val.equalsIgnoreCase("true") || val.equalsIgnoreCase("yes") || val.equalsIgnoreCase("1")
							|| val.equalsIgnoreCase("on")) {
						value.setBooleanValue(true);
					} else if (val.equalsIgnoreCase("false") || val.equalsIgnoreCase("no") || val.equalsIgnoreCase("0")
							|| val.equalsIgnoreCase("off")) {
						value.setBooleanValue(false);
					} else {
						throw new RuntimeException("PhysicsVal of type boolean must be true/yes/on/1 or false/no/off/0");
					}

					return value;
				} else if (type.equalsIgnoreCase("byte")) {
					final String val = ((Text) element.getFirstChild()).getData();
					value.setByteValue(Byte.parseByte(val));
					return value;
				} else if (type.equalsIgnoreCase("short")) {
					final String val = ((Text) element.getFirstChild()).getData();
					value.setShortValue(Short.parseShort(val));
					return value;
				} else if (type.equalsIgnoreCase("int")) {
					final String val = ((Text) element.getFirstChild()).getData();
					value.setIntValue(Integer.parseInt(val));
					return value;
				} else if (type.equalsIgnoreCase("long")) {
					final String val = ((Text) element.getFirstChild()).getData();
					value.setLongValue(Long.parseLong(val));
					return value;
				} else if (type.equalsIgnoreCase("float")) {
					final String val = ((Text) element.getFirstChild()).getData();
					value.setFloatValue(Float.parseFloat(val));
					return value;
				} else if (type.equalsIgnoreCase("double")) {
					final String val = ((Text) element.getFirstChild()).getData();
					value.setDoubleValue(Double.parseDouble(val));
					return value;
				} else if (type.equalsIgnoreCase("byteArray")) {
					String mime = null;
					final Attr mimeType = (Attr) attrs.getNamedItem("mimetype");
					if (mimeType != null) {
						mime = mimeType.getValue();
					}

					final Attr attrurl = (Attr) attrs.getNamedItem("href");
					if (attrurl != null) {
						try {
							final String url = attrurl.getValue();
							final ByteArrayValBuffer buffer = new ByteArrayValBuffer(Protocols.getURL(url));
							if (mime != null && mime.length() > 0) {
								buffer.setMimeType(mime);
							}

							value.setByteArrayValue(buffer.getByteArrayValue());
							return value;
						} catch (final Exception e) {
							throw new RuntimeException(e.getMessage());
						}
					}
					final Attr attrfile = (Attr) attrs.getNamedItem("file");
					if (attrfile != null) {
						try {
							String file = attrfile.getValue();

							if (!file.startsWith("file:")) {
								file = (new java.io.File(file)).toURI().toURL().toExternalForm();
							}

							if (mime != null) {
								final ByteArrayValBuffer buffer = new ByteArrayValBuffer(mime, Protocols.getURL(file));
								value.setByteArrayValue(buffer.getByteArrayValue());
								return value;
							} else {
								final ByteArrayValBuffer buffer = new ByteArrayValBuffer(Protocols.getURL(file));
								value.setByteArrayValue(buffer.getByteArrayValue());
								return value;
							}
						} catch (final Exception e) {
							throw new RuntimeException(e.getMessage());
						}
					}

					if (mime == null) {
						mime = "application/octet-stream";
					}

					if (element.getFirstChild() != null && element.getFirstChild() instanceof Text) {
						final String val = ((Text) element.getFirstChild()).getData();
						value.setByteArrayValue(new ByteArrayValue(val.getBytes(), mime));
					} else {
						value.setByteArrayValue(new ByteArrayValue("".getBytes(), mime));
					}

					return value;
				} else {
					throw new RuntimeException(
							"PhysicsVal type attribute must be one of boolean,byte,short,int,long,float,double,bytearray");
				}
			}
			/*
			 * if (attr.getName().equals("file")) { // <PhysicsVal file="???">
			 * // attr.getValue(); try { String
			 * mime=((Attr)attrs.getNamedItem("mimetype")).getValue(); String
			 * filepath=attr.getValue(); ByteArrayValBuffer buffer=new
			 * ByteArrayValBuffer(mime,filepath);
			 * value.setByteArrayValue(buffer.getByteArrayValue()); return
			 * value; }catch(Exception e) { throw new
			 * RuntimeException(e.getMessage()); } }
			 */
		}

		return null;
	}

	/** Scan through org.w3c.dom.Element named description. */
	String visitElement_description(final Element element) { // <description>
		// element.getValue();
		String desc = "";
		final NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			final Node node = nodes.item(i);
			if (node.getNodeType() == Node.CDATA_SECTION_NODE) {
				desc = ((CDATASection) node).getData();
				break;
			} else if (node.getNodeType() == Node.TEXT_NODE) {
				desc = ((Text) node).getData();
			}
		}
		return desc;
	}

	/** Scan through org.w3c.dom.Element named frequency-scale. */
	FrequencyScale visitElement_frequency_scale(final Element element) { // <frequency-scale>
		// element.getValue();

		final FrequencyScale freqscale = new FrequencyScale();
		final NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			final Attr attr = (Attr) attrs.item(i);
			if (attr.getName().equals("label")) { // <frequency-scale
				// label="???">
				freqscale.setFrequencyScaleLabel(attr.getValue());
			}
		}
		final NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			final Node node = nodes.item(i);
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				final Element nodeElement = (Element) node;
				if (nodeElement.getTagName().equals("max-frequency")) {
					freqscale.setMaximumFrequency(visitElement_Frequency((Element) node.getChildNodes().item(0)));
				}
				if (nodeElement.getTagName().equals("min-frequency")) {
					freqscale.setMinimumFrequency(visitElement_Frequency((Element) node.getChildNodes().item(0)));
				}
				if (nodeElement.getTagName().equals("step-frequency")) {
					freqscale.setStepFrequency(visitElement_Frequency((Element) node.getChildNodes().item(0)));
				}
				break;
			}
		}

		return freqscale;
	}

	/** Scan through org.w3c.dom.Element named parameter. */
	ChannelParameter visitElement_parameter(final Element element) { // <parameter>
		// element.getValue();
		final ChannelParameter parameter = new ChannelParameter();

		final NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			final Attr attr = (Attr) attrs.item(i);
			if (attr.getName().equals("value")) { // <parameter value="???">
				// attr.getValue();
				parameter.setSelectedParameterValue(attr.getValue());
			}
			if (attr.getName().equals("type")) { // <parameter type="???">
				final String type = attr.getValue();
				if (type.equalsIgnoreCase("OnOffValue")) {
					parameter.setParameterType(ParameterType.OnOffValue);
				} else if (type.equalsIgnoreCase("ContinuousValue")) {
					parameter.setParameterType(ParameterType.ContinuousValue);
				} else if (type.equalsIgnoreCase("SelectionListValue")) {
					parameter.setParameterType(ParameterType.SelectionListValue);
				} else if (type.equalsIgnoreCase("BlackBoxValue")) {
					parameter.setParameterType(ParameterType.BlackBoxValue);
				} else {
					throw new RuntimeException(
							"Parameter type must be one of OnOffValue/ContinuousValue/SelectionListValue/BlackBoxValue");
				}
			}
			if (attr.getName().equals("name")) { // <parameter name="???">
				parameter.setParameterName(attr.getValue());
			}
		}
		final NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			final Node node = nodes.item(i);
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				final Element nodeElement = (Element) node;
				if (nodeElement.getTagName().equals("selection-list")) {
					parameter.setParameterSelectionList(visitElement_selection_list(nodeElement));
				}
				break;
			}
		}
		return parameter;
	}

	/** Scan through org.w3c.dom.Element named sampling-scale. */
	SamplesNumScale visitElement_sampling_scale(final Element element) { // <sampling-scale>
		// element.getValue();
		final SamplesNumScale scale = new SamplesNumScale();
		final NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			final Node node = nodes.item(i);
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				final Element nodeElement = (Element) node;
				if (nodeElement.getTagName().equals("max-samples")) {
					final String str_max_samples = ((Text) nodeElement.getFirstChild()).getData();
					scale.setMaxSamples(Integer.parseInt(str_max_samples));
				}
				if (nodeElement.getTagName().equals("min-samples")) {
					final String str_min_samples = ((Text) nodeElement.getFirstChild()).getData();
					scale.setMinSamples(Integer.parseInt(str_min_samples));
				}
				if (nodeElement.getTagName().equals("step-samples")) {
					final String str_step_samples = ((Text) nodeElement.getFirstChild()).getData();
					scale.setStep(Integer.parseInt(str_step_samples));
				}
				break;
			}
		}
		return scale;
	}

	/** Scan through org.w3c.dom.Element named scale. */
	com.linkare.rec.impl.utils.HardwareInfoXMLReader.ScaleSelector visitElement_scale(final Element element) { // <scale>
		// element.getValue();
		final Scale scale = new Scale();
		boolean isSelected = false;

		final NamedNodeMap attrs = element.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			final Attr attr = (Attr) attrs.item(i);
			if (attr.getName().equals("selected")) { // <scale selected="???">
				final String str_isSelected = attr.getValue();
				if (str_isSelected.equalsIgnoreCase("true") || str_isSelected.equalsIgnoreCase("yes")
						|| str_isSelected.equalsIgnoreCase("1")) {
					isSelected = true;
				} else if (str_isSelected.equalsIgnoreCase("false") || str_isSelected.equalsIgnoreCase("no")
						|| str_isSelected.equalsIgnoreCase("0")) {
					isSelected = false;
				} else {
					throw new RuntimeException("selected attribute should be true/yes/1 or false/no/0");
				}
			}
			if (attr.getName().equals("label")) { // <scale label="???">
				// attr.getValue();
				scale.setScaleLabel(attr.getValue());
			}
			if (attr.getName().equals("physicsunitname")) { // <scale
				// physicsunitname="???">
				// attr.getValue();
				scale.setPhysicsUnitName(attr.getValue());
			}
			if (attr.getName().equals("multiplier")) { // <scale
				// multiplier="???">
				// attr.getValue();
				scale.setMultiplier(readMultiplier(attr.getValue()));
			}
			if (attr.getName().equals("physicsunitsymbol")) { // <scale
				// physicsunitsymbol="???">
				// attr.getValue();
				scale.setPhysicsUnitSymbol(attr.getValue());
			}
		}
		final NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			final Node node = nodes.item(i);
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				final Element nodeElement = (Element) node;
				if (nodeElement.getTagName().equals("errordefault")) {
					scale.setDefaultErrorValue(visitElement_PhysicsVal((Element) nodeElement.getFirstChild()));
				}
				if (nodeElement.getTagName().equals("max")) {
					scale.setMaximumValue(visitElement_PhysicsVal((Element) nodeElement.getFirstChild()));
				}
				if (nodeElement.getTagName().equals("min")) {
					scale.setMinimumValue(visitElement_PhysicsVal((Element) nodeElement.getFirstChild()));
				}
				if (nodeElement.getTagName().equals("step")) {
					scale.setStepValue(visitElement_PhysicsVal((Element) nodeElement.getFirstChild()));
				}
				break;
			}
		}

		return new com.linkare.rec.impl.utils.HardwareInfoXMLReader.ScaleSelector(scale, isSelected);
	}

	/** Scan through org.w3c.dom.Element named selection-list. */
	String[] visitElement_selection_list(final Element element) { // <selection-list>
		// element.getValue();
		TreeMap<Integer, String> tableSelectionList = new TreeMap<Integer, String>();
		final NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			final Node node = nodes.item(i);
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				final Element nodeElement = (Element) node;
				if (nodeElement.getTagName().equals("value")) {
					final String strOrderNum = ((Attr) node.getAttributes().getNamedItem("order")).getValue();
					final String value = ((Text) node.getFirstChild()).getData();
					tableSelectionList.put(Integer.valueOf(strOrderNum), value);
				}
				break;
			}
		}

		final Collection<String> col = tableSelectionList.values();
		final String[] selectionList = new String[col.size()];
		Object[] selectionListObj = col.toArray();
		System.arraycopy(selectionListObj, 0, selectionList, 0, selectionList.length);
		tableSelectionList = null;
		selectionListObj = null;
		return selectionList;

	}

	/**
	 * Getter for property hardwareInfo.
	 * 
	 * @return Value of property hardwareInfo.
	 */
	public HardwareInfo getHardwareInfo() {
		return hardwareInfo;
	}

	private class ScaleSelector {

		public ScaleSelector(final Scale scale, final boolean selected) {
			this.scale = scale;
			this.selected = selected;
		}

		/** Holds value of property scale. */
		private final Scale scale;

		/** Holds value of property selected. */
		private final boolean selected;

		/**
		 * Getter for property scale.
		 * 
		 * @return Value of property scale.
		 */
		public Scale getScale() {
			return scale;
		}

		/**
		 * Getter for property selected.
		 * 
		 * @return Value of property selected.
		 */
		public boolean isSelected() {
			return selected;
		}

	}

}
